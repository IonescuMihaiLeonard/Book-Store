package com.bookstore.order.service;

import com.bookstore.order.model.Address;
import com.bookstore.order.repository.AddressRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Long userId, Address address) {
        address.setUserId(userId);
        return addressRepository.save(address);
    }

    public List<Address> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address updateAddress(Long userId, Long id, Address updated) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        assertOwner(userId, address);

        address.setStreet(updated.getStreet());
        address.setCity(updated.getCity());
        address.setCountry(updated.getCountry());
        address.setZipCode(updated.getZipCode());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long userId, Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        assertOwner(userId, address);
        addressRepository.delete(address);
    }

    private void assertOwner(Long userId, Address address) {
        if (!address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized address access");
        }
    }
}
