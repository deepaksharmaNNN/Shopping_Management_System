package com.deepaksharma.shoppingmanagementsystem.mapper;


import com.deepaksharma.shoppingmanagementsystem.model.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressMapper {
    public Address mapToAddress(Address address) {
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .build();

    }
}
