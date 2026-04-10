package com.vendi.user.service;

import com.vendi.address.dto.AddressDTO;
import com.vendi.address.service.AddressService;
import com.vendi.product.dto.ProductDTO;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.UpdateMeDTO;
import com.vendi.user.dto.UpdatePasswordDTO;
import com.vendi.user.model.User;
import com.vendi.user.repository.UserRepository;
import com.vendi.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeService {

    @Autowired
    UserAuthenticatedService userAuthenticatedService;

    @Autowired
    AddressService addressService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDTO getMe() {
        return new UserDTO(userAuthenticatedService.getAuthenticatedUser());
    }

    @Transactional
    public UserDTO updateMe(UpdateMeDTO body) throws ResourceAlreadyExistsException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        validateEmailAvailability(body.email(), user);

        user.setName(body.name().trim());
        user.setEmail(body.email().trim());

        return new UserDTO(userRepository.save(user));
    }

    @Transactional
    public void updatePassword(UpdatePasswordDTO body) {
        User user = userAuthenticatedService.getAuthenticatedUser();

        if (!passwordEncoder.matches(body.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        if (passwordEncoder.matches(body.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the current password.");
        }

        user.setPassword(passwordEncoder.encode(body.newPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getMyProducts() {
        return userAuthenticatedService.getAuthenticatedUser().getProducts().stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> getMyAddresses() {
        return addressService.getMyAddresses();
    }

    private void validateEmailAvailability(String email, User authenticatedUser) throws ResourceAlreadyExistsException {
        Object existingUser = userRepository.findByEmail(email.trim());

        if (existingUser == null) {
            return;
        }

        User userWithEmail = (User) existingUser;
        if (!userWithEmail.getId().equals(authenticatedUser.getId())) {
            throw new ResourceAlreadyExistsException("This email is already registered");
        }
    }
}
