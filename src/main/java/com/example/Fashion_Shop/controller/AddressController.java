package com.example.Fashion_Shop.controller;


import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.service.address.AddressService;
import com.example.Fashion_Shop.service.user.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<Address> createOrUpdateAddress(@Valid @RequestBody Address address) {

        if (address.getUser() == null) {
            Address errorResponse = new Address();
            errorResponse.setCity("User must be provided.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);

    }


        @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        if (addresses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(addresses);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable @Min(1) Integer id) {
        Optional<Address> address = addressService.getAddressById(id);
        if (address.isPresent()) {
            return ResponseEntity.ok(address.get()); // Trả về địa chỉ nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Optional<Address> existingAddress = addressService.getAddressById(id);
        if (existingAddress.isPresent()) {
            address.setId(id);
            Address updatedAddress = addressService.saveAddress(address);
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable @Min(1) Integer id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // Xóa thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Không tìm thấy địa chỉ để xóa
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable @Min(1) Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        if (addresses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(addresses); // Trả về danh sách trống
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PatchMapping("/set-default/{addressId}")
    public ResponseEntity<String> setDefaultAddress(@PathVariable @Min(1) Integer addressId) {
        boolean isSetDefault = addressService.setDefaultAddress(addressId);
        if (isSetDefault) {
            return ResponseEntity.ok("Address set as default successfully."); // Trả về thông báo thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found.");
        }
    }


}
