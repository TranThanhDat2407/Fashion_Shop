package com.example.Fashion_Shop.controller;


import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.service.address.AddressService;
import com.example.Fashion_Shop.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity<Address> createOrUpdateAddress(@RequestBody Address address) {
        Address savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> address = addressService.getAddressById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Integer userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


    @PatchMapping("/set-default/{addressId}")
    public ResponseEntity<Void> setDefaultAddress(@PathVariable Integer addressId) {
        addressService.setDefaultAddress(addressId);
        return ResponseEntity.noContent().build();
    }


}
