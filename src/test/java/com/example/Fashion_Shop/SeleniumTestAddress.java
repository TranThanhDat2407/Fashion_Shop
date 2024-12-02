package com.example.Fashion_Shop;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SeleniumTestAddress {
    WebDriver driver;
    private WebDriverWait wait;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    Map<String, Object[]> TestNGResults;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("TestNG Results");



        TestNGResults = new HashMap<>();
    }



    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test(description = "Opens the TestNG Demo Website for Login Test")
    public void launchWebsite() {
        try {
            driver.get("http://localhost:4200/addresses_list");
            TestNGResults.put("2", new Object[]{"Navigate to demo website", "Site gets opened successfully", "Pass"});
        } catch (Exception e) {
            TestNGResults.put("2", new Object[]{"Navigate to demo website", "Site failed to open", "Fail"});
            e.printStackTrace();
        } finally {
            writeExcel(); // Ghi kết quả vào Excel sau mỗi bài test
        }
    }

    @Test
    public void testAddNewAddress_Success() {
        driver.get("http://localhost:4200/addresses_list");

        // Nhấn nút Thêm Mới để mở form
        sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-primary.mb-3")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-body")));
        sleep(2000);
        // Nhập thông tin vào form sử dụng formControlName
        driver.findElement(By.cssSelector("input[formControlName='city']")).sendKeys("Sài Gòn");

        driver.findElement(By.cssSelector("input[formControlName='ward']")).sendKeys("Quận 10");
        sleep(2000);

        driver.findElement(By.cssSelector("input[formControlName='street']")).sendKeys("Nguyễn Văn Trỗi");
        sleep(2000);
        driver.findElement(By.cssSelector("input[formControlName='isDefault']")).click();
        // Nhấn nút Thêm mới

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Kiểm tra thông báo thành công hoặc dữ liệu hiển thị trên bảng
        wait.until(visibilityOfElementLocated(By.cssSelector(".table")));
        WebElement table = driver.findElement(By.cssSelector(".table"));

        Assertions.assertTrue(table.isDisplayed(), "Bảng địa chỉ phải được hiển thị");

        // Kiểm tra xem địa chỉ mới có xuất hiện trên bảng không
        WebElement newAddress = driver.findElement(By.xpath("//td[text()='Sài Gòn']"));
        sleep(2000);
        Assertions.assertNotNull(newAddress, "Địa chỉ mới phải xuất hiện trên bảng.");

        TestNGResults.put("3", new Object[]{"Add New Address", "Address added successfully", "Pass"});
        writeExcel(); // Ghi kết quả vào Excel sau mỗi test
    }


    @Test
    public void testUpdateAddress_Success() {
        driver.get("http://localhost:4200/addresses_list");

        // Giả sử đã có một địa chỉ và nhấn nút Sửa
        sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-warning")).click();

        // Kiểm tra modal hiện lên và chỉnh sửa thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-body")));
        sleep(2000);
        // Chỉnh sửa thông tin form
        driver.findElement(By.cssSelector("input[formControlName='city']")).clear();

        driver.findElement(By.cssSelector("input[formControlName='city']")).sendKeys("Sài Gòn");
        sleep(2000);
        driver.findElement(By.cssSelector("input[formControlName='ward']")).clear();

        driver.findElement(By.cssSelector("input[formControlName='ward']")).sendKeys("Quận 9");
        sleep(2000);
        driver.findElement(By.cssSelector("input[formControlName='street']")).clear();

        driver.findElement(By.cssSelector("input[formControlName='street']")).sendKeys("Nguyễn Văn Trỗi");
        sleep(2000);
        // Nhấn nút Cập nhật
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleep(2000);
        // Kiểm tra bảng đã được cập nhật
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table")));
        WebElement updatedAddress = driver.findElement(By.xpath("//td[text()='Sài Gòn']"));
        Assertions.assertNotNull(updatedAddress, "Địa chỉ đã được cập nhật.");

        TestNGResults.put("4", new Object[]{"Update Address", "Address updated successfully", "Pass"});
        writeExcel(); // Ghi kết quả vào Excel sau mỗi test
    }


    @Test
    public void testAddNewAddress_Failure_MissingCity() {
        driver.get("http://localhost:4200/addresses_list");

        // Nhấn nút Thêm Mới để mở form
        sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-primary.mb-3")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-body")));
        sleep(2000);
        // Bỏ qua trường "Thành phố" và nhập các trường khác
        driver.findElement(By.cssSelector("input[formControlName='ward']")).sendKeys("Ba Đình");

        driver.findElement(By.cssSelector("input[formControlName='street']")).sendKeys("123 Đường Láng");
        sleep(2000);
        // Nhấn nút Thêm mới
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Kiểm tra lỗi hiển thị cho trường "Thành phố"
        WebElement errorMessage = driver.findElement(By.cssSelector(".text-danger"));

        Assertions.assertTrue(errorMessage.isDisplayed(), "Thành phố không được để trống.");

        TestNGResults.put("5", new Object[]{"Add New Address - Failure", "Missing City field", "Fail"});
        writeExcel(); // Ghi kết quả vào Excel sau mỗi test
    }


    @Test
    public void testDeleteAddress_Success() {
        try {
            driver.get("http://localhost:4200/addresses_list");

            // Giả sử đã có một địa chỉ trong danh sách và nhấn Xóa
            WebElement deleteButton = driver.findElement(By.cssSelector("button.btn.btn-danger")); // Chọn nút Xóa đầu tiên
            sleep(2000);
            deleteButton.click();

            // Xác nhận xóa trong modal hoặc alert (nếu có)
            driver.switchTo().alert().accept(); // Hoặc tìm nút xác nhận trong modal và click

            // Kiểm tra xem địa chỉ đã bị xóa khỏi bảng
            sleep(2000);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Sài Gòn']")));

            // Xác nhận bảng không còn chứa địa chỉ đã xóa
            sleep(2000);
            Assertions.assertTrue(driver.findElements(By.xpath("//td[text()='Sài Gòn']")).isEmpty(), "Địa chỉ đã bị xóa khỏi bảng");

            // Ghi kết quả vào TestNGResults
            TestNGResults.put("6", new Object[]{"Delete Address", "Address deleted successfully", "Pass"});
        } catch (Exception e) {
            TestNGResults.put("6", new Object[]{"Delete Address", "Address deletion failed", "Fail"});
            e.printStackTrace();
        } finally {
            writeExcel(); // Ghi kết quả vào Excel sau mỗi test
        }
    }




    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    private void writeExcel() {
        if (sheet.getPhysicalNumberOfRows() == 0) {
            createHeaderRow();
        }

        int rowNum = sheet.getPhysicalNumberOfRows();
        for (Map.Entry<String, Object[]> entry : TestNGResults.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            writeDataRow(entry.getValue(), row);
        }


        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi file Excel
        try (FileOutputStream outFile = new FileOutputStream(new File("TestNGResults.xls"))) {
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tạo dòng tiêu đề với định dạng
    private void createHeaderRow() {
        Row header = sheet.createRow(0);
        String[] columns = {"Test Case", "Description", "Result"};

        // Định dạng cho tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    // Ghi dữ liệu vào một dòng với định dạng
    private void writeDataRow(Object[] data, Row row) {
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Căn giữa cột "Result"
        CellStyle resultStyle = workbook.createCellStyle();
        resultStyle.cloneStyleFrom(dataStyle);
        resultStyle.setAlignment(HorizontalAlignment.CENTER);

        // Định dạng màu cho kết quả Pass/Fail
        Font passFont = workbook.createFont();
        passFont.setColor(IndexedColors.GREEN.getIndex());

        Font failFont = workbook.createFont();
        failFont.setColor(IndexedColors.RED.getIndex());

        for (int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            if (data[i] instanceof String) {
                cell.setCellValue((String) data[i]);
                cell.setCellStyle(dataStyle);

                // Định dạng màu cho cột "Result"
                if (i == 2) { // Cột "Result"
                    if ("Pass".equalsIgnoreCase((String) data[i])) {
                        cell.getCellStyle().setFont(passFont);
                    } else if ("Fail".equalsIgnoreCase((String) data[i])) {
                        cell.getCellStyle().setFont(failFont);
                    }
                    cell.setCellStyle(resultStyle);
                }
            }
        }
    }

}
