package com.incomemanager.api.entity.address;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AddressDAOImp implements AddressDAO {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Optional<Address> getByUuid(String uuid) {
        return addressRepository.findByUuid(uuid);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.saveAndFlush(address);
    }
}
