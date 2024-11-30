package com.example.Fashion_Shop;

import com.example.Fashion_Shop.controller.AddressController;
import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.service.address.AddressService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private int rowCount;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Test Address Results");
        rowCount = 1;

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Test ID");
        headerRow.createCell(1).setCellValue("Test Name");
        headerRow.createCell(2).setCellValue("Result");
        headerRow.createCell(3).setCellValue("Message");
    }

    @AfterEach
    public void exportTestResultsToExcel() {
        try (FileOutputStream out = new FileOutputStream(new File("test_results.xls"))) {
            workbook.write(out);
            System.out.println("Test results have been successfully saved to Excel file: test_results.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logResult(String testName, String result, String message) {
        Row row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue(rowCount - 1);
        row.createCell(1).setCellValue(testName);
        row.createCell(2).setCellValue(result);
        row.createCell(3).setCellValue(message);
    }

    @Test
    public void testCreateOrUpdateAddress() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            address.setCity("Ho Chi Minh");
            address.setWard("Ward 1");
            address.setStreet("123 Hoàng Hoa Thám");
            address.setIsDefault(true);
            User user = new User();
            user.setId(1L);
            address.setUser(user);

            when(addressService.saveAddress(any(Address.class))).thenReturn(address);

            mockMvc.perform(post("/api/v1/addresses")
                            .contentType("application/json")
                            .content("{\"city\":\"Ho Chi Minh\", \"ward\":\"Ward 1\", " +
                                    "\"street\":\"123 Hoàng Hoa Thám\"," +
                                    " \"isDefault\":true, \"user\":{\"id\":1}}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.street").value("123 Hoàng Hoa Thám"))
                    .andExpect(jsonPath("$.city").value("Ho Chi Minh"))
                    .andExpect(jsonPath("$.ward").value("Ward 1"))
                    .andExpect(jsonPath("$.isDefault").value(true))
                    .andExpect(jsonPath("$.user.id").value(1L));

            logResult("testCreateOrUpdateAddress", "Pass", "Create or Update Address");
        } catch (Exception e) {
            logResult("testCreateOrUpdateAddress", "Fail", e.getMessage());
        }
    }

    @Test
    public void testCreateOrUpdateAddressWithMissingFields() throws Exception {
        try {
            Address address = new Address();
            address.setStreet("123 Hoàng Hoa Thám");

            when(addressService.saveAddress(any(Address.class))).thenReturn(address);

            mockMvc.perform(post("/api/v1/addresses")
                            .contentType("application/json")
                            .content("{\"street\":\"123 Hoàng Hoa Thám\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("City and Ward are required"));

            logResult("testCreateOrUpdateAddressWithMissingFields", "Pass", "City and Ward are required");
        } catch (Exception e) {
            logResult("testCreateOrUpdateAddressWithMissingFields", "Fail", e.getMessage());
        }
    }

    @Test
    public void testGetAllAddresses() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            address.setStreet("123 Hoàng Hoa Thám");

            when(addressService.getAllAddresses()).thenReturn(Arrays.asList(address));

            mockMvc.perform(get("/api/v1/addresses"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].street").value("123 Hoàng Hoa Thám"));

            logResult("testGetAllAddresses", "Pass", "Get All Address");
        } catch (Exception e) {
            logResult("testGetAllAddresses", "Fail", e.getMessage());
        }
    }

    @Test
    public void testGetAddressById() throws Exception {
        try {
            Address address = new Address();
            address.setId(100);
//            address.setStreet("123 Hoàng Hoa Thám");

            when(addressService.getAddressById(100)).thenReturn( Optional.of(address));
            mockMvc.perform(get("/api/v1/addresses/100"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(100));
//                    .andExpect(jsonPath("$.street").value("123 Hoàng Hoa Thám"));

            logResult("testGetAddressById", "Pass", "Get Address By Id");
        } catch (Exception e) {
            logResult("testGetAddressById", "Fail", e.getMessage());
        }
    }

    @Test
    public void testGetAddressById_NotFound() throws Exception {
        try {
            when(addressService.getAddressById(1)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/v1/addresses/1"))
                    .andExpect(status().isNotFound());

            logResult("testGetAddressById_NotFound", "Pass", "Address not found");
        } catch (Exception e) {
            logResult("testGetAddressById_NotFound", "Fail", e.getMessage());
        }
    }

    @Test
    public void testDeleteAddress() throws Exception {
        try {
            doNothing().when(addressService).deleteAddress(1);

            mockMvc.perform(delete("/api/v1/addresses/1"))
                    .andExpect(status().isNoContent());

            logResult("testDeleteAddress", "Pass", "Address deleted successfully");
        } catch (Exception e) {
            logResult("testDeleteAddress", "Fail", e.getMessage());
        }
    }

    @Test
    public void testSetDefaultAddress() throws Exception {
        try {
            doNothing().when(addressService).setDefaultAddress(1);

            mockMvc.perform(patch("/api/v1/addresses/set-default/1"))
                    .andExpect(status().isNoContent());

            logResult("testSetDefaultAddress", "Pass", "Set Default Address");
        } catch (Exception e) {
            logResult("testSetDefaultAddress", "Fail", e.getMessage());
        }
    }
}
