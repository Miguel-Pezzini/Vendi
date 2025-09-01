package com.vendi.user.service;

import com.vendi.address.dto.AddressDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeService {

    @Autowired
    UserAuthenticatedService userAuthenticatedService;

    public UserDTO getMe() {
        return new UserDTO(userAuthenticatedService.getAuthenticatedUser());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getMyProducts() {
        return userAuthenticatedService.getAuthenticatedUser().getProducts().stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> getMyAddresses() {
        return userAuthenticatedService.getAuthenticatedUser().getAddresses().stream().map(AddressDTO::new).toList();
    }
}
