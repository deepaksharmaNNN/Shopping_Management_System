package com.deepaksharma.shoppingmanagementsystem.mapper;


import com.deepaksharma.shoppingmanagementsystem.dtos.AddressDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressMapper {
    public Address mapToAddress(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .country(addressDTO.getCountry())
                .zipCode(addressDTO.getZipCode())
                .build();

    }
}
