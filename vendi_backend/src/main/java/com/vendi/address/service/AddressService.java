package com.vendi.address.service;

import com.vendi.address.repository.AddressRepository;
import com.vendi.user.service.UserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserAuthenticatedService userAuthenticatedService;
}
