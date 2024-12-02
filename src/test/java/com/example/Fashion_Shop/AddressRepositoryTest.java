package com.example.Fashion_Shop;
import com.example.Fashion_Shop.controller.AddressController;
import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.AddressRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@SpringBootTest
@Transactional
@Rollback(true)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressController addressController;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAddress() {

        User userId = new User();
        userId.setId(4L);

        Address address = new Address();
        address.setCity("");
        address.setWard("Đà Lạt");
        address.setStreet("456 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(userId);

        ResponseEntity<?> response = addressController.createOrUpdateAddress(4L, address);

        assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    public void testSaveAddress_UserNotFound() {

        User userId = new User();
        userId.setId(100L);

        Address address = new Address();
        address.setCity("TP Hồ Chí Minh");
        address.setWard("Quậnn 2");
        address.setStreet("456 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(userId);

        ResponseEntity<?> response = addressController.createOrUpdateAddress(100L, address);

        assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testSaveAddress_MissingComponent() {

        User userId = new User();
        userId.setId(1L);

        Address address = new Address();
        address.setCity("TP Hồ Chí Minh");
//        address.setWard("Quậnn 2");
//        address.setStreet("456 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(userId);

        ResponseEntity<?> response = addressController.createOrUpdateAddress(1L, address);

        assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateAddress() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu Address ban đầu
        Address address = new Address();
        address.setCity("Ha Noi");
        address.setWard("Dong Da");
        address.setStreet("123 Phố Huế");
        address.setIsDefault(true);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        // Cập nhật địa chỉ
        savedAddress.setCity("Hồ Chí Minh");
        savedAddress.setWard("Quận 1");
        savedAddress.setStreet("456 Nguyễn Đình Chiểu");
        savedAddress.setIsDefault(false);

        // Gọi phương thức updateAddress
        ResponseEntity<?> response = addressController.createOrUpdateAddress(4L, savedAddress);


        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        // Kiểm tra dữ liệu đã được cập nhật trong cơ sở dữ liệu
        Address updatedAddress = addressRepository.findById(savedAddress.getId()).orElse(null);
        assertNotNull(updatedAddress);
        assertEquals(updatedAddress.getCity(), "Hồ Chí Minh");
        assertEquals(updatedAddress.getWard(), "Quận 1");
        assertEquals(updatedAddress.getStreet(), "456 Nguyễn Đình Chiểu");
        assertFalse(updatedAddress.getIsDefault());
    }


    @Test
    public void testUpdateAddress_UserNotFound() {
        // Tạo Address với userId không tồn tại trong cơ sở dữ liệu
        User user = new User();
        user.setId(100L); // ID người dùng không tồn tại
        Address address = new Address();
        address.setId(1);  // ID của Address muốn cập nhật
        address.setCity("TP Hồ Chí Minh");
        address.setWard("Phú Nhuận");
        address.setStreet("123 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(user);

        // Gọi phương thức updateAddress
        ResponseEntity<?> response = addressController.createOrUpdateAddress(100L, address);

        // Kiểm tra mã trạng thái trả về là 400 BAD_REQUEST
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateAddress_MissingComponent() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu Address ban đầu
        Address address = new Address();
        address.setCity("TP Hồ Chí Minh");
        address.setWard("Quận 2");
        address.setStreet("123 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);


        savedAddress.setWard(null);

        // Gọi phương thức updateAddress
        ResponseEntity<?> response = addressController.createOrUpdateAddress(4L, savedAddress);

        // Kiểm tra mã trạng thái trả về là 400 BAD_REQUEST
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void testGetAllAddresses_EmptyDatabase() {
        // Giả sử không có địa chỉ nào trong cơ sở dữ liệu
        addressRepository.deleteAll();  // Xóa hết tất cả các địa chỉ

        // Gọi phương thức getAllAddresses
        ResponseEntity<List<Address>> response = addressController.getAllAddresses();

        // Kiểm tra mã trạng thái trả về là 204 NO_CONTENT
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

        // Kiểm tra danh sách trả về là rỗng
        assertTrue(response.getBody().isEmpty());
    }


    @Test
    public void testGetAllAddresses_WithData() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu các địa chỉ
        Address address1 = new Address();
        address1.setCity("Hà Nội");
        address1.setWard("Ba Đình");
        address1.setStreet("123 Phố Huế");
        address1.setIsDefault(true);
        address1.setUser(user);
        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setCity("Hồ Chí Minh");
        address2.setWard("Quận 1");
        address2.setStreet("456 Nguyễn Đình Chiểu");
        address2.setIsDefault(false);
        address2.setUser(user);
        addressRepository.save(address2);

        // Gọi phương thức getAllAddresses
        ResponseEntity<List<Address>> response = addressController.getAllAddresses();

        // Kiểm tra mã trạng thái trả về là 200 OK
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Kiểm tra danh sách trả về không rỗng và có đúng 2 địa chỉ
        assertFalse(response.getBody().isEmpty());
        assertEquals(response.getBody().size(), 2);
        assertEquals(response.getBody().get(0).getCity(), "Hà Nội");
        assertEquals(response.getBody().get(1).getCity(), "Hồ Chí Minh");
    }


    @Test
    public void testGetAllAddresses_ForSpecificUser() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu các địa chỉ
        Address address1 = new Address();
        address1.setCity("Hà Nội");
        address1.setWard("Ba Đình");
        address1.setStreet("123 Phố Huế");
        address1.setIsDefault(true);
        address1.setUser(user);
        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setCity("Hồ Chí Minh");
        address2.setWard("Quận 1");
        address2.setStreet("456 Nguyễn Đình Chiểu");
        address2.setIsDefault(false);
        address2.setUser(user);
        addressRepository.save(address2);

        // Gọi phương thức getAllAddresses cho user có ID 4
        ResponseEntity<List<Address>> response = addressController.getAllAddresses();

        // Kiểm tra mã trạng thái trả về là 200 OK
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Kiểm tra rằng có đúng 2 địa chỉ của user này
        assertFalse(response.getBody().isEmpty());
        assertEquals(response.getBody().size(), 2);
    }


    @Test
    public void testDeleteAddress_Success() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu địa chỉ
        Address address = new Address();
        address.setCity("Hà Nội");
        address.setWard("Ba Đình");
        address.setStreet("123 Phố Huế");
        address.setIsDefault(true);
        address.setUser(user);
        addressRepository.save(address);

        // Kiểm tra trước khi xóa
        assertNotNull(addressRepository.findById(address.getId()).orElse(null));

        // Gọi phương thức deleteAddress
        ResponseEntity<Void> response = addressController.deleteAddress(address.getId());

        // Kiểm tra mã trạng thái trả về là 204 NO_CONTENT
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

        // Kiểm tra địa chỉ đã bị xóa
        assertFalse(addressRepository.findById(address.getId()).isPresent());
    }


    @Test
    public void testDeleteAddress_NotFound() {
        // ID không tồn tại trong cơ sở dữ liệu
        Integer nonExistentId = 999;

        // Gọi phương thức deleteAddress
        ResponseEntity<Void> response = addressController.deleteAddress(nonExistentId);

        // Kiểm tra mã trạng thái trả về là 404 NOT_FOUND
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void testDeleteAddress_InvalidId() {
        // ID không hợp lệ (<= 0)
        Integer invalidId = -1;

        // Gọi phương thức deleteAddress
        ResponseEntity<Void> response = addressController.deleteAddress(invalidId);

        // Kiểm tra mã trạng thái trả về là 400 BAD_REQUEST
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void testDeleteAddress_AlreadyDeleted() {
        // Tạo và lưu User
        User user = new User();
        user.setId(4L);
        userRepository.save(user);

        // Tạo và lưu địa chỉ
        Address address = new Address();
        address.setCity("Hà Nội");
        address.setWard("Ba Đình");
        address.setStreet("123 Phố Huế");
        address.setIsDefault(true);
        address.setUser(user);
        addressRepository.save(address);

        // Xóa địa chỉ
        addressRepository.delete(address);

        // Gọi phương thức deleteAddress với địa chỉ đã bị xóa
        ResponseEntity<Void> response = addressController.deleteAddress(address.getId());

        // Kiểm tra mã trạng thái trả về là 404 NOT_FOUND
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


}
