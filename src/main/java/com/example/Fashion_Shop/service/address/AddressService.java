package com.example.Fashion_Shop.service.address;

import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;


    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }


    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }


    public Optional<Address> getAddressById(Integer id) {
        return addressRepository.findById(id);
    }


    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }


    public List<Address> getAddressesByUserId(Integer userId) {
        return addressRepository.findAllByUser_Id(userId);
    }

    public void setDefaultAddress(Integer addressId) {
        addressRepository.findById(addressId).ifPresent(address -> {
            address.setIsDefault(true);
            addressRepository.save(address);
        });
    }

}
