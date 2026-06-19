package com.bookstore.order.controller;

import com.bookstore.order.model.Address;
import com.bookstore.order.service.AddressService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> addAddress(
            @RequestParam Long userId,
            @Valid @RequestBody Address address
    ) {
        return ResponseEntity.ok(addressService.addAddress(userId, address));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(@RequestParam Long userId) {
        return ResponseEntity.ok(addressService.getAddresses(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @RequestParam Long userId,
            @PathVariable Long id,
            @Valid @RequestBody Address address
    ) {
        return ResponseEntity.ok(addressService.updateAddress(userId, id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@RequestParam Long userId, @PathVariable Long id) {
        addressService.deleteAddress(userId, id);
        return ResponseEntity.noContent().build();
    }
}
