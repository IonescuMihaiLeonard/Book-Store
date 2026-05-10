package com.example.EcommerceBookStore.service;

import com.example.EcommerceBookStore.model.Address;
import com.example.EcommerceBookStore.model.User;
import com.example.EcommerceBookStore.model.repositoriy.AddressRepository;
import com.example.EcommerceBookStore.model.repositoriy.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ---------------- ADD ADDRESS ----------------
    public Address addAddress(Address address) {
        User user = getCurrentUser();
        address.setUser(user);
        return addressRepository.save(address);
    }

    // ---------------- GET ALL ADDRESSES ----------------
    public List<Address> getMyAddresses() {
        User user = getCurrentUser();
        return addressRepository.findByUserId(user.getId());
    }

    // ---------------- DELETE ADDRESS ----------------
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        User user = getCurrentUser();

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        addressRepository.delete(address);
    }

    // ---------------- UPDATE ADDRESS ----------------
    public Address updateAddress(Long id, Address updated) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        User user = getCurrentUser();

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        address.setStreet(updated.getStreet());
        address.setCity(updated.getCity());
        address.setCountry(updated.getCountry());
        address.setZipCode(updated.getZipCode());

        return addressRepository.save(address);
    }
}
