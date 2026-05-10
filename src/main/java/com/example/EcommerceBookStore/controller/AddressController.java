package com.example.EcommerceBookStore.controller;

import com.example.EcommerceBookStore.model.Address;
import com.example.EcommerceBookStore.model.User;
import com.example.EcommerceBookStore.service.AddressService;
import com.example.EcommerceBookStore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // ---------------- ADD ----------------
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public List<Address> getMyAddresses() {
        return addressService.getMyAddresses();
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id,
                                 @RequestBody Address address) {
        return addressService.updateAddress(id, address);
    }

}
