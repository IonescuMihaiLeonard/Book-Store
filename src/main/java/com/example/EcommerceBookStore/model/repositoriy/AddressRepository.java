package com.example.EcommerceBookStore.model.repositoriy;

import com.example.EcommerceBookStore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);
}
