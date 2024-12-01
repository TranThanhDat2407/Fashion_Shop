package com.example.Fashion_Shop;

import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.AddressRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;



    @Autowired
    private UserRepository userRepository;

    @BeforeMethod
    public void setUp() {

        User user = new User();
        user.setId(4L);

        userRepository.save(user);
    }

    @Test
    public void testCreateAddressIntegration() throws Exception {
        // Tạo đối tượng Address
        ObjectMapper objectMapper = new ObjectMapper();
        Address address = new Address();
        address.setCity("TP Hồ Chí Minh");
        address.setWard("Phú Nhuận");
        address.setStreet("456 Nguyễn Đình Chiểu");
        address.setIsDefault(false);

        User user = new User();
        user.setId(4L);
        address.setUser(user);

        // Chuyển Address thành JSON
        String addressJson = objectMapper.writeValueAsString(address);

        // Gửi yêu cầu POST và kiểm tra phản hồi
        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("TP Hồ Chí Minh"))
                .andExpect(jsonPath("$.ward").value("Phú Nhuận"))
                .andExpect(jsonPath("$.street").value("456 Nguyễn Đình Chiểu"))
                .andExpect(jsonPath("$.isDefault").value(false));

        // Kiểm tra dữ liệu đã được lưu trong database
        List<Address> addresses = addressRepository.findAll();
        assert !addresses.isEmpty();

        Address savedAddress = addresses.get(0);
        assert savedAddress.getCity().equals("TP Hồ Chí Minh");
        assert savedAddress.getWard().equals("Phú Nhuận");
        assert savedAddress.getStreet().equals("456 Nguyễn Đình Chiểu");
        assert !savedAddress.getIsDefault();
        assert savedAddress.getUser().getId() == 4;
    }




    @Test
    @DisplayName("Test get all addresses")
    public void testGetAllAddresses() throws Exception {
        // Tình huống 1: Không có địa chỉ nào
        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isNoContent())  // Kiểm tra mã trạng thái 204
                .andExpect(jsonPath("$").isEmpty());  // Kiểm tra rằng mảng trả về là rỗng

        User user = userRepository.findById(4L).orElseThrow(() -> new RuntimeException("User not found"));
        // Tình huống 2: Có địa chỉ trong cơ sở dữ liệu
        Address address1 = new Address(6,"Hanoi","Dong Da","123 Pho Hue", false, user );
        Address address2 = new Address(7,"Hồ Chí Minh","Ward 1","123 Hoàng Hoa Thám", false,user);
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
        // Tình huống 1: Địa chỉ tồn tại
        User user = userRepository.findById(4L).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = new Address(6,"Hanoi","Dong Da","123 Pho Hue", false, user);
        Address savedAddress = addressRepository.save(address);

        mockMvc.perform(get("/api/v1/addresses/{id}", savedAddress.getId()))
                .andExpect(status().isOk())  // Kiểm tra mã trạng thái 200
                .andExpect(jsonPath("$.city").value("TP Hồ Chí Minh"))  // Kiểm tra thông tin địa chỉ
                .andExpect(jsonPath("$.ward").value("Phú Nhuận"))
                .andExpect(jsonPath("$.street").value("456 Nguyễn Đình Chiểu"))
                .andExpect(jsonPath("$.isDefault").value(false));

        // Tình huống 2: Địa chỉ không tồn tại
        mockMvc.perform(get("/api/v1/addresses/{id}", 999))  // Sử dụng một ID không tồn tại
                .andExpect(status().isNotFound());  // Kiểm tra mã trạng thái 404
    }



}
