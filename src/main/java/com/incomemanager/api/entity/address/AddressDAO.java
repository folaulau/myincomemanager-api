package com.incomemanager.api.entity.address;

import java.util.Optional;

public interface AddressDAO {

    Address save(Address address);

    Optional<Address> getByUuid(String uuid);

}
