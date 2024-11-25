package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(Integer id);
    List<Address> findByCity(String city);

}
