package com.vendi.address.service;

import com.vendi.address.dto.AddressDTO;
import com.vendi.address.repository.AddressRepository;
import com.vendi.product.dto.ProductResponseDTO;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserAuthenticatedService userAuthenticatedService;
}
