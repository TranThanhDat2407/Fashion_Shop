package com.example.Fashion_Shop;

import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.AddressRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application.yml")
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAddress() throws Exception {
        // Tạo User
        User user = new User();
        user.setId(4L); // Đảm bảo user có ID hợp lệ

        // Tạo Address
        Address address = new Address();
        address.setCity("TP Hồ Chí Minh");
        address.setWard("Phú Nhuận");
        address.setStreet("456 Nguyễn Đình Chiểu");
        address.setIsDefault(false);
        address.setUser(user); // Gán user vào địa chỉ

        // Chuyển đối tượng Address thành JSON
        String addressJson = new ObjectMapper().writeValueAsString(address);
        System.out.println("addressJson: " + addressJson);

        // Gửi yêu cầu POST để tạo địa chỉ mới
        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("TP Hồ Chí Minh"))
                .andExpect(jsonPath("$.ward").value("Phú Nhuận"))
                .andExpect(jsonPath("$.street").value("456 Nguyễn Đình Chiểu"))
                .andExpect(jsonPath("$.isDefault").value(false));

        // Kiểm tra dữ liệu đã được lưu trong cơ sở dữ liệu
        Address savedAddress = addressRepository.findAll().get(0); // Hoặc tìm theo ID nếu cần thiết
        assert savedAddress != null;
        assert savedAddress.getId() != null;  // Kiểm tra rằng ID đã được gán
        assert savedAddress.getCity().equals("TP Hồ Chí Minh");
        assert savedAddress.getWard().equals("Phú Nhuận");
        assert savedAddress.getStreet().equals("456 Nguyễn Đình Chiểu");
        assert savedAddress.getIsDefault().equals(false);  // Kiểm tra giá trị 'isDefault'
    }


    @Test
    @DisplayName("Test get all addresses")
    public void testGetAllAddresses() throws Exception {
        // Tình huống 1: Không có địa chỉ nào
        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isNoContent())  // Kiểm tra mã trạng thái 204
                .andExpect(jsonPath("$").isEmpty());  // Kiểm tra rằng mảng trả về là rỗng

        // Tình huống 2: Có địa chỉ trong cơ sở dữ liệu
        User user = new User();
        user.setId(4L);
        userRepository.save(user); // Lưu user vào cơ sở dữ liệu

        Address address1 = new Address(6, "Hanoi", "Dong Da", "123 Pho Hue", false, user);
        Address address2 = new Address(7, "Hồ Chí Minh", "Ward 1", "123 Hoàng Hoa Thám", false, user);
        addressRepository.save(address1);
        addressRepository.save(address2);

        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isOk())  // Kiểm tra mã trạng thái 200
                .andExpect(jsonPath("$").isArray())  // Kiểm tra rằng kết quả là một mảng
                .andExpect(jsonPath("$.length()").value(2))  // Kiểm tra số lượng địa chỉ
                .andExpect(jsonPath("$[0].city").value("Hanoi"))  // Kiểm tra thông tin địa chỉ đầu tiên
                .andExpect(jsonPath("$[1].city").value("Hồ Chí Minh"));  // Kiểm tra thông tin địa chỉ thứ hai
    }


    @Test
    @DisplayName("Test get address by ID")
    public void testGetAddressById() throws Exception {
        User user = new User();
        user.setId(4L);
        userRepository.save(user); // Lưu user vào cơ sở dữ liệu

        Address address = new Address(6, "Hanoi", "Dong Da", "123 Pho Hue", false, user);
        Address savedAddress = addressRepository.save(address);

        // Tình huống 1: Địa chỉ tồn tại
        mockMvc.perform(get("/api/v1/addresses/{id}", savedAddress.getId()))
                .andExpect(status().isOk())  // Kiểm tra mã trạng thái 200
                .andExpect(jsonPath("$.city").value("Hanoi"))  // Kiểm tra thông tin địa chỉ
                .andExpect(jsonPath("$.ward").value("Dong Da"))
                .andExpect(jsonPath("$.street").value("123 Pho Hue"))
                .andExpect(jsonPath("$.isDefault").value(false));

        // Tình huống 2: Địa chỉ không tồn tại
        mockMvc.perform(get("/api/v1/addresses/{id}", 999))  // Sử dụng một ID không tồn tại
                .andExpect(status().isNotFound());  // Kiểm tra mã trạng thái 404
    }

}
