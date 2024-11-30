package com.example.Fashion_Shop.DatTest;

import com.example.Fashion_Shop.controller.UserController;
import com.example.Fashion_Shop.dto.UserDTO;
import com.example.Fashion_Shop.dto.UserLoginDTO;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.response.user.RegisterResponse;
import com.example.Fashion_Shop.response.user.UserResponse;
import com.example.Fashion_Shop.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application.yml")
public class TestUserController extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    String token;
    HSSFWorkbook workbook;
    HSSFSheet sheet;

    private LinkedHashMap<String, Object[]> TestNGResults;

    @BeforeClass
    public void setUp() {
        token = "Bearer " + generateTestToken();
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Test Result Summary");
        TestNGResults = new LinkedHashMap<>();
        TestNGResults.put("1", new Object[]{
                "Test Step No.", "Test Data", "Action", "Expected Output",
                "Actual Output", "Test By", "Date"
        });
    }

    @AfterClass
    public void teardown() {
        applyFormattingAndFill();
        saveExcelFile("DatTestUserController.xls");
    }

    @Test(description = "Required Fields", priority = 2)
    public void requiredFields() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("");
            userDTO.setPhone("");
            userDTO.setEmail("");
            userDTO.setPassword("");
            userDTO.setRetypePassword("");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Vui lòng nhập tên") &&
                    content.contains("Vui lòng nhập số điện thoại.") &&
                    content.contains("Vui lòng nhập email.") &&
                    content.contains("Vui lòng nhập mật khẩu.") &&
                    content.contains("Vui lòng xác nhận mật khẩu.");
            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("2", new Object[]{
                        1d,
                        "\nname = ' ',\n" +
                                "email = ' ',\n" +
                                "phone = ' ',\n" +
                                "password = ' ',\n" +
                                "retypePassword = ' ',",
                        "Nhập các trường thông tin trống vào trang đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                "'Vui lòng nhập tên.' \n" +
                                "'Vui lòng nhập email.' \n" +
                                "'Vui lòng nhập số điện thoại.' \n" +
                                "'Vui lòng nhập mật khẩu.' \n" +
                                "'Vui lòng xác nhận mật khẩu.' \n"
                        ,
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("2", new Object[]{
                        1d,
                        "\nname = ' ',\n" +
                                "email = ' ',\n" +
                                "phone = ' ',\n" +
                                "password = ' ',\n" +
                                "retypePassword = ' ',",
                        "Nhập các trường thông tin trống vào trang đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                "'Vui lòng nhập tên.' \n" +
                                "'Vui lòng nhập email.' \n" +
                                "'Vui lòng nhập số điện thoại.' \n" +
                                "'Vui lòng nhập mật khẩu.' \n" +
                                "'Vui lòng xác nhận mật khẩu.' \n"
                        ,
                        "fail: \n"+errorMessage
                        ,

                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("2", new Object[]{
                    1d,
                    "\nname = ' ',\n" +
                            "email = ' ',\n" +
                            "phone = ' ',\n" +
                            "password = ' ',\n" +
                            "retypePassword = ' ',",
                    "Nhập các trường thông tin trống vào trang đăng ký",
                    "\nCác trường hiển thị lỗi: \n" +
                            "'Vui lòng nhập tên.' \n" +
                            "'Vui lòng nhập email.' \n" +
                            "'Vui lòng nhập số điện thoại.' \n" +
                            "'Vui lòng nhập mật khẩu.' \n" +
                            "'Vui lòng xác nhận mật khẩu.' \n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required email Format", priority = 3)
    public void requiredEmailFormat() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0934421234");
            userDTO.setEmail("datttps");
            userDTO.setPassword("Password123");
            userDTO.setRetypePassword("Password123");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Vui lòng nhập email đúng định dạng.");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập email sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                "'Vui lòng nhập email đúng định dạng.' \n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                                "Nhập email sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                "'Vui lòng nhập email đúng định dạng.' \n"
                        ,
                        "fail: \n"+errorMessage
                        ,

                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("3", new Object[]{
                    2d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps',\n" +
                            "phone = '0934421234',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập email sai định dạng",
                    "\nCác trường hiển thị lỗi: \n" +
                            "'Vui lòng nhập email đúng định dạng.' \n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required email Format", priority = 4)
    public void existingEmailFormat() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0934417928");
            userDTO.setEmail("datttps37451@fpt.edu.vn");
            userDTO.setPassword("Password123");
            userDTO.setRetypePassword("Password123");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Email đã tồn tại");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("4", new Object[]{
                        3d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@fpt.edu.vn',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n" ,
                        "Nhập email đã được đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                " 'Email này đã được đăng ký.'  \n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("4", new Object[]{
                        3d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@fpt.edu.vn',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n" ,
                        "Nhập email đã được đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                " 'Email này đã được đăng ký.'  \n"
                        ,
                        "fail: \n"+errorMessage
                        ,

                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("4", new Object[]{
                    3d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@fpt.edu.vn',\n" +
                            "phone = '0934421234',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n" ,
                    "Nhập email đã được đăng ký",
                    "\nCác trường hiển thị lỗi: \n" +
                            " 'Email này đã được đăng ký.'  \n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required phone Format", priority = 5)
    public void requiredPhoneFormat() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("012311");
            userDTO.setEmail("datttps37451@gmail.com");
            userDTO.setPassword("Password123");
            userDTO.setRetypePassword("Password123");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Vui lòng nhập đúng định dạng số điện thoại.");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '012311',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                "  'Vui lòng nhập đúng định dạng số điện thoại.'   \n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '012311',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                "  'Vui lòng nhập đúng định dạng số điện thoại.'   \n"
                        ,
                        "fail: \n"+errorMessage
                        ,
                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("5", new Object[]{
                    4d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '012311',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập số điện thoại sai định dạng",
                    "\nCác trường hiển thị lỗi: \n" +
                            "  'Vui lòng nhập đúng định dạng số điện thoại.'   \n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required phone Format", priority = 6)
    public void existingPhone() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0931234567");
            userDTO.setEmail("datttps37451@gmail.com");
            userDTO.setPassword("Password123");
            userDTO.setRetypePassword("Password123");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Số điện thoại đã tồn tại");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("6", new Object[]{
                        5d,
                        "\n\n" +
                                "name = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n"
                               ,
                        "Nhập số điện thoại đã được đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                "  Số điện thoại đã tồn tại   \n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("6", new Object[]{
                        5d,
                        "\n\n" +
                                "name = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n"
                        ,
                        "Nhập số điện thoại đã được đăng ký",
                        "\nCác trường hiển thị lỗi: \n" +
                                " Số điện thoại đã tồn tại   \n"
                        ,
                        "fail: \n"+errorMessage
                        ,
                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("6", new Object[]{
                    5d,
                    "\n\n" +
                            "name = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0931234567',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n"
                    ,
                    "Nhập số điện thoại đã được đăng ký",
                    "\nCác trường hiển thị lỗi: \n" +
                            " Số điện thoại đã tồn tại   \n",
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required password Format", priority = 7)
    public void passwordFormat() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0931234567");
            userDTO.setEmail("datttps37451@gmail.com");
            userDTO.setPassword("abc");
            userDTO.setRetypePassword("abc");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\n\n" +
                                "name = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'abc',\n" +
                                "retypePassword = 'abc'\n"
                        ,
                        "Nhập mật khẩu sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                " Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.  \n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\n\n" +
                                "name = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'abc',\n" +
                                "retypePassword = 'abc'\n"
                        ,
                        "Nhập mật khẩu sai định dạng",
                        "\nCác trường hiển thị lỗi: \n" +
                                " Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.  \n"
                        ,
                        "fail: \n"+errorMessage
                        ,
                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("7", new Object[]{
                    6d,
                    "\n\n" +
                            "name = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0931234567',\n" +
                            "password = 'abc',\n" +
                            "retypePassword = 'abc'\n"
                    ,
                    "Nhập mật khẩu sai định dạng",
                    "\nCác trường hiển thị lỗi: \n" +
                            " Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.  \n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Required password Format", priority = 8)
    public void matchPassword() {
        try {
            // Tạo dữ liệu người dùng không hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0931234567");
            userDTO.setEmail("datttps37451@gmail.com");
            userDTO.setPassword("Abc123");
            userDTO.setRetypePassword("Abc21231321");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .header("Accept-Language","vi")
//                            .header("Authorization", token)
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusBadRequest = status == 400;
            boolean containsAllErrors = content.contains("Mật khẩu không trùng khớp.");

            if (isStatusBadRequest && containsAllErrors) {
                TestNGResults.put("8", new Object[]{
                        7d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc21231321'\n"
                        ,
                        "Nhập xác nhận mật khẩu không khớp với mật khẩu",
                        "\nCác trường hiển thị lỗi: \n" +
                                "Mật khẩu không trùng khớp.\n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("8", new Object[]{
                        7d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc212A31321'\n"
                        ,
                        "Nhập xác nhận mật khẩu không khớp với mật khẩu",
                        "\nCác trường hiển thị lỗi: \n" +
                                "Mật khẩu không trùng khớp.\n"
                        ,
                        "fail: \n"+errorMessage
                        ,
                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("8", new Object[]{
                    7d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0934417924',\n" +
                            "password = 'Abc123',\n" +
                            "retypePassword = 'Abc21231321'\n"
                    ,
                    "Nhập xác nhận mật khẩu không khớp với mật khẩu",
                    "\nCác trường hiển thị lỗi: \n" +
                            "Mật khẩu không trùng khớp.\n"
                    ,
                    "fail: \n"+ e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Successful Registration", priority = 9)
    public void successfulRegistration() {
        try {
            // Tạo dữ liệu người dùng hợp lệ
            UserDTO userDTO = new UserDTO();
            userDTO.setName("Trần Thành Đạt");
            userDTO.setPhone("0931234564");
            userDTO.setEmail("datttps37452@gmail.com");
            userDTO.setPassword("Abc123");
            userDTO.setRetypePassword("Abc123");
            userDTO.setRoleId(2L);

            String userJson = new ObjectMapper().writeValueAsString(userDTO);

            // Gửi yêu cầu đăng ký
            var result = mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .header("Accept-Language", "vi")
                            .content(userJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            String errorMessage = "Status = " + status + "\nContent = " + content;

            boolean isStatusOK = status == 200;
            boolean containsSuccessMessage = content.contains("Đăng ký tài khoản thành công");

            Optional<User> savedUser = userRepository.findByEmail("datttps37452@gmail.com");

            if (isStatusOK && containsSuccessMessage && savedUser.isPresent()) {
                TestNGResults.put("9", new Object[]{
                        8d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc123'\n"
                        ,
                        "Nhập thông tin người dùng hợp lệ và đăng ký thành công",
                        "\nKết quả trả về: \n" +
                                "'Đăng ký thành công.' \n" +
                                "Người dùng được lưu trong cơ sở dữ liệu với email: 'datttps37451@gmail.com'\n"
                        ,
                        "pass\n",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("9", new Object[]{
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc123'\n"
                        ,
                        "Nhập thông tin người dùng hợp lệ và đăng ký thành công",
                        "\nKết quả trả về: \n" +
                                "'Đăng ký thành công.' \n" +
                                "Người dùng được lưu trong cơ sở dữ liệu với email: 'datttps37451@gmail.com'\n"
                        ,
                        "fail\n",
                        "Đạt",
                        new Date()
                });
                Assert.fail(errorMessage);
            }

        } catch (Exception e) {
            TestNGResults.put("9", new Object[]{
                    8d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0931234567',\n" +
                            "password = 'Abc123',\n" +
                            "retypePassword = 'Abc123'\n"
                    ,
                    "Nhập thông tin người dùng hợp lệ và đăng ký thành công",
                    "\nKết quả trả về: \n" +
                            "'Đăng ký thành công.' \n" +
                            "Người dùng được lưu trong cơ sở dữ liệu với email: 'datttps37451@gmail.com'\n"
                    ,
                    "fail: \n" + e.getMessage(),
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Login with Empty Email and Password", priority = 10)
    public void testLoginWithEmptyEmailAndPassword() {
        try {
            // Tạo dữ liệu người dùng với email và password trống
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setEmail("");  // Email trống
            userLoginDTO.setPassword("");  // Password trống
            userLoginDTO.setRoleId(2L);

            String loginJson = new ObjectMapper().writeValueAsString(userLoginDTO);

            // Gửi yêu cầu đăng nhập
            var result = mockMvc.perform(post("/api/v1/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(loginJson))
                    .andReturn();

            int status = result.getResponse().getStatus();
            String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            // Kiểm tra phản hồi
            boolean isStatusBadRequest = status == 400;
            boolean containsEmailError = content.contains("Vui lòng nhập email.");
            boolean containsPasswordError = content.contains("Vui lòng nhập mật khẩu.");

            if (isStatusBadRequest && containsEmailError && containsPasswordError) {
                TestNGResults.put("10", new Object[]{
                        9d,
                        "\nname: ''\n" + "password: ''\n"
                        ,
                        "Đăng nhập với email và password trống",
                        "Trả về lỗi yêu cầu nhập email và mật khẩu",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("10", new Object[]{
                        9d,
                        "\nname: ''\n" + "password: ''\n"
                        ,
                        "Đăng nhập với email và password trống",
                        "Trả về lỗi yêu cầu nhập email và mật khẩu",
                        "fail: Status = " + status + ", Content = " + content,
                        "Không đạt",
                        new Date()
                });
                Assert.fail("Test thất bại: Status = " + status + ", Content = " + content);
            }

        } catch (Exception e) {
            TestNGResults.put("10", new Object[]{
                    9d,
                    "\nname: ''\n" + "password: ''\n"
                    ,
                    "Đăng nhập với email và password trống",
                    "Trả về lỗi yêu cầu nhập email và mật khẩu",
                    "fail: " + e.getMessage(),
                    "Không đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }




    private void applyFormattingAndFill () {
        Set<String> keyset = TestNGResults.keySet();
        int rownum = 0;

        // Create a header style
        CellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setWrapText(true); // Enable text wrapping

        // Create a general data style
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setWrapText(true); // Enable text wrapping for data

        // Create a date style
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm:ss"));
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setWrapText(true);

        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = TestNGResults.get(key);
            int cellnum = 0;

            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                    cell.setCellStyle(dataStyle); // Apply general style
                } else if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                    cell.setCellStyle(dateStyle); // Apply date style
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                    cell.setCellStyle(dataStyle); // Apply general style
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                    cell.setCellStyle(dataStyle); // Apply general style
                }

                // Apply header style to the first row
                if (rownum == 1) {
                    cell.setCellStyle(headerStyle);
                }
            }

            // Auto-adjust row height for text wrapping
            row.setHeight((short) -1); // Automatically adjusts the row height to fit wrapped text
        }

        // Auto-size all columns
        for (int i = 0; i < TestNGResults.get("1").length; i++) {
            sheet.autoSizeColumn(i);

            // Optional: Increase column width for readability
            int currentWidth = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, Math.min(currentWidth + 2000, 65280)); // Ensure width doesn't exceed Excel's max
        }
    }


    private void saveExcelFile (String fileName){
        File file = new File(fileName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            if (file.exists()) {
                System.out.println("File already exists. Overwriting: " + fileName);
            } else {
                System.out.println("Creating new file: " + fileName);
            }
            workbook.write(out);
            System.out.println("File written successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateTestToken() {
        // Sử dụng thư viện JWT để tạo token giả hợp lệ
        return Jwts.builder()
                .setSubject("admin@admin.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, "TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI=")
                .compact();
    }



}
