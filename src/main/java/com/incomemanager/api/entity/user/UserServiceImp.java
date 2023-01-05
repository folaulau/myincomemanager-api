package com.incomemanager.api.entity.user;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserRecord;
import com.incomemanager.api.dto.AddressDTO;
import com.incomemanager.api.dto.AuthenticationResponseDTO;
import com.incomemanager.api.dto.AuthenticatorDTO;
import com.incomemanager.api.dto.EntityDTOMapper;
import com.incomemanager.api.dto.UserDTO;
import com.incomemanager.api.dto.UserProfileUpdateDTO;
import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountDAO;
import com.incomemanager.api.entity.account.AccountStatus;
import com.incomemanager.api.entity.account.SignUpStatus;
import com.incomemanager.api.entity.address.Address;
import com.incomemanager.api.entity.address.AddressDAO;
import com.incomemanager.api.entity.user.role.Role;
import com.incomemanager.api.exception.ApiException;
import com.incomemanager.api.firebase.FirebaseAuthService;
import com.incomemanager.api.jwt.JwtTokenService;
import com.incomemanager.api.security.AuthenticationService;
import com.incomemanager.api.utils.ObjectUtils;
import com.incomemanager.api.utils.RandomGeneratorUtils;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private FirebaseAuthService   firebaseAuthService;

    @Autowired
    private UserDAO               userDAO;

    @Autowired
    private AddressDAO            addressDAO;

    @Autowired
    private AccountDAO            accountDAO;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserValidatorService  userValidatorService;

    @Autowired
    private EntityDTOMapper       entityDTOMapper;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticatorDTO authenticatorDTO) {
        UserRecord userRecord = firebaseAuthService.verifyAndGetUser(authenticatorDTO.getToken());

        log.info("userRecord: uuid={}, email={}", userRecord.getUid(), userRecord.getEmail());

        Optional<User> optUser = userDAO.findByUuid(userRecord.getUid());

        User user = null;

        if (optUser.isPresent()) {
            /**
             * sign in
             */
            user = optUser.get();

            if (!user.isActive()) {
                throw new ApiException("Your account is not active. Please contact our support team.", "status=" + user.getStatus());
            }

        } else {
            /**
             * sign up
             */

            Account account = new Account();
            account.setAddress(new Address());
            account.setSignUpStatus(SignUpStatus.SIGN_UP);
            account.setStatus(AccountStatus.ACTIVE);
            account = accountDAO.save(account);

            user = new User();
            user.setUuid(userRecord.getUid());
            user.setRole(new Role(UserType.user));
            user.setType(UserType.user);
            user.setStatus(UserStatus.ACTIVE);
            user.setAccount(account);

            String email = userRecord.getEmail();

            if (email == null || email.isEmpty()) {
                UserInfo[] userInfos = userRecord.getProviderData();

                Optional<String> optEmail = Arrays.asList(userInfos)
                        .stream()
                        .filter(userInfo -> (userInfo.getEmail() != null && !userInfo.getEmail().isEmpty()))
                        .map(userInfo -> userInfo.getEmail())
                        .findFirst();

                if (optEmail.isPresent()) {
                    email = optEmail.get();

                    optUser = userDAO.findByEmail(email);
                    if (optUser.isPresent()) {
                        throw new ApiException("Email taken", "an account has this email already", "Please use one email per account");
                    }
                } else {
                    // temp email as placeholder
                    email = "temp-user" + RandomGeneratorUtils.getIntegerWithin(10000, Integer.MAX_VALUE) + "@myincomemanager.com";

                    user.setEmailTemp(true);
                }
            }

            user.setThirdPartyName(userRecord.getDisplayName());

            user.setEmail(email);

            if (userRecord.getPhoneNumber() != null) {
                user.setPhoneNumber(userRecord.getPhoneNumber());
            }

            // com.stripe.model.Customer customer = stripeCustomerService.createParentDetails(petParent);
            //
            // user.setStripeCustomerId(customer.getId());

            user = userDAO.save(user);

            // Customer stripeCustomer = stripeCustomerService.create(user);
            //
            // account.setStripeCustomerId(stripeCustomer.getId());
            //
            // accountDAO.save(account);

            // notificationService.sendWelcomeNotificationToParent(petParent);
        }

        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(user);

        log.info("authenticationResponseDTO={}", ObjectUtils.toJson(authenticationResponseDTO));

        return authenticationResponseDTO;
    }

    @Transactional
    @Override
    public UserDTO updateProfile(UserProfileUpdateDTO userProfileUpdateDTO) {
        User user = userValidatorService.validateProfileUpdate(userProfileUpdateDTO);

        AddressDTO addressDTO = userProfileUpdateDTO.getAccount().getAddress();

        /**
         * update account
         */
        Account account = user.getAccount();

        account.updateSignUpStatus(SignUpStatus.PROFILE);

        account = accountDAO.save(account);

        /**
         * update address
         */
        Address address = account.getAddress();

        address = entityDTOMapper.patchAddressWithAddressDTO(addressDTO, address);

        address = this.addressDAO.save(address);

        /**
         * update user
         */
        user = entityDTOMapper.patchUserWithUserProfileUpdateDTO(userProfileUpdateDTO, user);

        user = userDAO.save(user);

        UserDTO userDTO = entityDTOMapper.mapUserToUserDTO(user);
        return userDTO;
    }

}
