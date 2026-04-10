package com.vendi.address.exception;

import com.vendi.shared.exception.ResourceNotFoundException;

public class AddressNotFoundException extends ResourceNotFoundException {
    public AddressNotFoundException() {
        super("Address not found.");
    }
}
