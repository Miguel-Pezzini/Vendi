package com.vendi.address.service;

import com.vendi.address.dto.AddressDTO;
import com.vendi.address.dto.UpsertAddressDTO;
import com.vendi.address.exception.AddressNotFoundException;
import com.vendi.address.model.Address;
import com.vendi.address.repository.AddressRepository;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserAuthenticatedService userAuthenticatedService;

    @Transactional(readOnly = true)
    public List<AddressDTO> getMyAddresses() {
        User user = userAuthenticatedService.getAuthenticatedUser();

        return addressRepository.findAllByUserIdOrderByCreatedAtAsc(user.getId())
                .stream()
                .map(AddressDTO::new)
                .toList();
    }

    @Transactional
    public AddressDTO create(UpsertAddressDTO body) {
        User user = userAuthenticatedService.getAuthenticatedUser();

        Address address = new Address();
        applyChanges(address, body);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        user.getAddresses().add(savedAddress);

        if (user.getCurrentAddress() == null) {
            user.setCurrentAddress(savedAddress);
        }

        return new AddressDTO(savedAddress);
    }

    @Transactional
    public AddressDTO update(UUID addressId, UpsertAddressDTO body) throws AddressNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Address address = getUserAddressOrThrow(addressId, user);

        applyChanges(address, body);
        return new AddressDTO(address);
    }

    @Transactional
    public void delete(UUID addressId) throws AddressNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Address address = getUserAddressOrThrow(addressId, user);

        if (isCurrentAddress(user, addressId)) {
            user.setCurrentAddress(
                    addressRepository.findFirstByUserIdAndIdNotOrderByCreatedAtAsc(user.getId(), addressId).orElse(null)
            );
        }

        user.getAddresses().removeIf(existingAddress -> existingAddress.getId().equals(addressId));
    }

    @Transactional
    public AddressDTO setActive(UUID addressId) throws AddressNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Address address = getUserAddressOrThrow(addressId, user);

        user.setCurrentAddress(address);
        return new AddressDTO(address);
    }

    private Address getUserAddressOrThrow(UUID addressId, User user) throws AddressNotFoundException {
        return addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(AddressNotFoundException::new);
    }

    private void applyChanges(Address address, UpsertAddressDTO body) {
        address.setStreet(body.street().trim());
        address.setCity(body.city().trim());
        address.setState(body.state().trim());
        address.setZipCode(body.zipCode().trim());
    }

    private boolean isCurrentAddress(User user, UUID addressId) {
        return user.getCurrentAddress() != null && user.getCurrentAddress().getId().equals(addressId);
    }
}
