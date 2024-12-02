package com.example.Fashion_Shop.service.address;

import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.AddressRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

//    public Address saveAddress(Address address) {
//        return addressRepository.save(address);
//    }
public Address saveAddress(Address address) {
    if (address.getId() != null) {
        Optional<Address> existingAddress = addressRepository.findById(address.getId());
        if (existingAddress.isPresent()) {
            Address updatedAddress = existingAddress.get();
            updatedAddress.setCity(address.getCity());
            updatedAddress.setWard(address.getWard());
            updatedAddress.setStreet(address.getStreet());
            updatedAddress.setIsDefault(address.getIsDefault());

            if (address.getUser() == null && address.getUser().getId() != null) {
                User user = userRepository.findById(address.getUser().getId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                updatedAddress.setUser(user);
            }

            return addressRepository.save(updatedAddress);
        } else {
            throw new IllegalArgumentException("Address with the given ID does not exist.");
        }
    } else {
        if (address.getUser() == null && address.getUser().getId() != null) {
            User user = userRepository.findById(address.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            address.setUser(user);
        }

        return addressRepository.save(address);
    }
}







    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }


    public Optional<Address> getAddressById(Integer id) {
        return addressRepository.findById(id);
    }


    public boolean deleteAddress(Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            addressRepository.deleteById(id); // Xóa địa chỉ nếu tồn tại
            return true; // Địa chỉ đã được xóa thành công
        }
        return false; // Không tìm thấy địa chỉ để xóa
    }


    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findAllByUser_Id(userId);
    }


//
//    public boolean setDefaultAddress(Integer addressId) {
//        Optional<Address> addressOptional = addressRepository.findById(addressId);
//        if (addressOptional.isPresent()) {
//            Address address = addressOptional.get();
//
//            address.setIsDefault(true);
//            addressRepository.save(address);
//            return true; // Địa chỉ đã được cập nhật thành công
//        }
//        return false; // Không tìm thấy địa chỉ
//    }


    @Transactional
    public boolean setDefaultAddress(Integer addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isPresent()) {
            Address addressToSetDefault = addressOptional.get();
            List<Address> userAddresses = addressRepository.findAllByUser_Id(addressToSetDefault.getUser().getId());

            // Đặt tất cả các địa chỉ thành không mặc định, trừ địa chỉ được chọn
            for (Address addr : userAddresses) {
                addr.setIsDefault(addr.getId().equals(addressId));
            }

            // Lưu tất cả địa chỉ đã cập nhật
            addressRepository.saveAll(userAddresses);
            return true;
        }
        return false; // Không tìm thấy địa chỉ
    }







}
