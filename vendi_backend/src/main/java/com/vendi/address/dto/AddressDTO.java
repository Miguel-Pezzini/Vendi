package com.vendi.address.dto;

import com.vendi.address.model.Address;

import java.util.UUID;

public record AddressDTO(UUID id, String street, String city, String state, String zipCode, UUID userId) {
    public AddressDTO(Address address) {
        this(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getUser().getId()
        );
    }
}
