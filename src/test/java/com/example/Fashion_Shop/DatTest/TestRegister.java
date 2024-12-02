package com.example.Fashion_Shop.DatTest;

import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application.yml")
public class TestRegister extends AbstractTestNGSpringContextTests{
    WebDriver driver;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    private LinkedHashMap<String, Object[]> TestNGResults;

    @Autowired
    private UserRepository userRepository;

    @BeforeClass
    public void setUp() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Test Result Summary");
        TestNGResults = new LinkedHashMap<>();
        TestNGResults.put("1", new Object[]{
                "Test Step No.", "Test Data", "Action", "Expected Output",
                "Actual Output", "Test By", "Date"
        });
        try {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new IllegalStateException("Can't start the Edge driver", e);
        }
    }

    @AfterClass
    public void teardown() {
        applyFormattingAndFill();
        saveExcelFile("DatTestRegister.xls");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test(description = "Open Register", priority = 1)
    public void LaunchWebsite() {
        try {
            driver.get("http://localhost:4200/signin");
            driver.manage().window().maximize();

            TestNGResults.put("2", new Object[]{
                    1d, "", "Mở trang đăng ký", "\nTrang đăng ký được mở\n", "pass", "Đạt", new Date()
            });
        } catch (Exception e) {
            TestNGResults.put("2", new Object[]{
                    1d, "", "Mở trang đăng ký", "\nTrang đăng ký được mở\n", "fail", "Đạt", new Date()
            });
            Assert.fail("Test gặp lỗi");
        }
    }

    @Test(description = "Required Fields", priority = 2)
    public void requiredFields() {
        try {

            driver.findElement(By.id("name")).sendKeys("");
            driver.findElement(By.id("email")).sendKeys("");
            driver.findElement(By.id("phone")).sendKeys("");
            driver.findElement(By.id("password")).sendKeys("");
            driver.findElement(By.id("retypePassword")).sendKeys("");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Nhấn nút đăng ký

            Thread.sleep(2000);
            // Kiểm tra các thông báo lỗi hiển thị
            boolean isNameErrorDisplayed =
                    driver.findElement(By.xpath(
                            "//div[contains(text(),'Vui lòng nhập tên')]")).isDisplayed();
            boolean isEmailErrorDisplayed =
                    driver.findElement(By.xpath(
                            "//div[contains(text(),'Vui lòng nhập email')]")).isDisplayed();
            boolean isPhoneErrorDisplayed =
                    driver.findElement(By.xpath(
                            "//div[contains(text(),'Vui lòng nhập số điện thoại')]")).isDisplayed();
            boolean isPasswordErrorDisplayed =
                    driver.findElement(By.xpath(
                            "//div[contains(text(),'Vui lòng nhập mật khẩu')]")).isDisplayed();

            boolean isRetypePasswordErrorDisplayed =
                    driver.findElement(By.xpath(
                            "//div[contains(text(),'Vui lòng xác nhận mật khẩu.')]")).isDisplayed();

            // Kiểm tra xem các thông báo lỗi có được hiển thị không
            if (isNameErrorDisplayed && isEmailErrorDisplayed && isPhoneErrorDisplayed &&
                    isPasswordErrorDisplayed && isRetypePasswordErrorDisplayed) {
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nname = ' ',\n" +
                                "email = ' ',\n" +
                                "phone = ' ',\n" +
                                "password = ' ',\n" +
                                "retypePassword = ' ',",
                        "Nhập các trường thông tin trống vào trang đăng ký",
                        "\nCác trường hiển thị lỗi: " +
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
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nname = ' ',\n" +
                                "email = ' ',\n" +
                                "phone = ' ',\n" +
                                "password = ' ',\n" +
                                "retypePassword = ' ',",
                        "Nhập các trường thông tin trống vào trang đăng ký",
                        "\nCác trường hiển thị lỗi: " +
                                "'Vui lòng nhập tên.' \n" +
                                "'Vui lòng nhập email.' \n" +
                                "'Vui lòng nhập số điện thoại.' \n" +
                                "'Vui lòng nhập mật khẩu.' \n" +
                                "'Vui lòng xác nhận mật khẩu.' \n"
                        ,
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi");
            }

        } catch (Exception e) {
            TestNGResults.put("3", new Object[]{
                    2d,
                    "\nname = ' ',\n" +
                            "email = ' ',\n" +
                            "phone = ' ',\n" +
                            "password = ' ',\n" +
                            "retypePassword = ' ',",
                    "Nhập các trường thông tin trống vào trang đăng ký",
                    "\nCác trường hiển thị lỗi: " +
                            "'Vui lòng nhập tên.' \n" +
                            "'Vui lòng nhập email.' \n" +
                            "'Vui lòng nhập số điện thoại.' \n" +
                            "'Vui lòng nhập mật khẩu.' \n" +
                            "'Vui lòng xác nhận mật khẩu.' \n"
                    ,
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Email Format", priority = 3)
    public void invalidEmailFormat() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps");
            driver.findElement(By.id("phone")).sendKeys("0934421234");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("retypePassword")).sendKeys("Password123");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isEmailFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Vui lòng nhập email đúng định dạng.')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isEmailFormatErrorDisplayed) {
                TestNGResults.put("4", new Object[]{
                        3d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập email sai định dạng",
                        "Thông báo lỗi 'Vui lòng nhập email đúng định dạng.' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("4", new Object[]{
                        3d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập email sai định dạng",
                        "Thông báo lỗi 'Vui lòng nhập email đúng định dạng.' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("4", new Object[]{
                    3d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps',\n" +
                            "phone = '0934421234',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập email sai định dạng",
                    "Thông báo lỗi 'Vui lòng nhập email đúng định dạng.' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Email Format", priority = 4)
    public void existingEmail() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@fpt.edu.vn");
            driver.findElement(By.id("phone")).sendKeys("0934421234");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("retypePassword")).sendKeys("Password123");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isEmailFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Email này đã được đăng ký')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isEmailFormatErrorDisplayed) {
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@fpt.edu.vn',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập email đã được đăng ký",
                        "Thông báo lỗi 'Email này đã được đăng ký.' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@fpt.edu.vn',\n" +
                                "phone = '0934421234',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập email đã được đăng ký",
                        "Thông báo lỗi 'Email này đã được đăng ký.' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("5", new Object[]{
                    4d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@fpt.edu.vn',\n" +
                            "phone = '0934421234',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập email đã được đăng ký",
                    "Thông báo lỗi 'Email này đã được đăng ký.' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Phone Format", priority = 5)
    public void invalidPhoneFormat() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@gmail.com");
            driver.findElement(By.id("phone")).sendKeys("012311");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("retypePassword")).sendKeys("Password123");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isPhoneFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Vui lòng nhập đúng định dạng số điện thoại.')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isPhoneFormatErrorDisplayed) {
                TestNGResults.put("6", new Object[]{
                        5d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '012311',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại sai định dạng",
                        "Thông báo lỗi 'Vui lòng nhập đúng định dạng số điện thoại.' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("6", new Object[]{
                        5d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '012311',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại sai định dạng",
                        "Thông báo lỗi 'Vui lòng nhập đúng định dạng số điện thoại.' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("6", new Object[]{
                    5d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '012311',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập số điện thoại sai định dạng",
                    "Thông báo lỗi 'Vui lòng nhập đúng định dạng số điện thoại.' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Phone Format", priority = 6)
    public void existingPhone() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@gmail.com");
            driver.findElement(By.id("phone")).sendKeys("0931234567");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("retypePassword")).sendKeys("Password123");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isEmailFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Số điện thoại này đã được đăng ký')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isEmailFormatErrorDisplayed) {
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại đã được đăng ký",
                        "Thông báo lỗi 'Số điện thoại này đã được đăng ký' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0931234567',\n" +
                                "password = 'Password123',\n" +
                                "retypePassword = 'Password123'\n",
                        "Nhập số điện thoại đã được đăng ký",
                        "Thông báo lỗi 'Số điện thoại này đã được đăng ký' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("7", new Object[]{
                    6d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0931234567',\n" +
                            "password = 'Password123',\n" +
                            "retypePassword = 'Password123'\n",
                    "Nhập số điện thoại đã được đăng ký",
                    "Thông báo lỗi 'Số điện thoại này đã được đăng ký' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Phone Format", priority = 7)
    public void invalidPasswordFormat() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@gmail.com");
            driver.findElement(By.id("phone")).sendKeys("0934417924");
            driver.findElement(By.id("password")).sendKeys("abc");
            driver.findElement(By.id("retypePassword")).sendKeys("abc");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isPhoneFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isPhoneFormatErrorDisplayed) {
                TestNGResults.put("8", new Object[]{
                        7d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'abc',\n" +
                                "retypePassword = 'abc'\n",
                        "Nhập mật khẩu sai định dạng",
                        "Thông báo lỗi 'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("8", new Object[]{
                        7d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'abc',\n" +
                                "retypePassword = 'abc'\n",
                        "Nhập mật khẩu sai định dạng",
                        "Thông báo lỗi 'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("8", new Object[]{
                    7d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0934417924',\n" +
                            "password = 'abc',\n" +
                            "retypePassword = 'abc'\n",
                    "Nhập mật khẩu sai định dạng",
                    "Thông báo lỗi 'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Invalid Phone Format", priority = 8)
    public void invalidConfirmPassword() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@gmail.com");
            driver.findElement(By.id("phone")).sendKeys("0934417924");
            driver.findElement(By.id("password")).sendKeys("Abc123");
            driver.findElement(By.id("retypePassword")).sendKeys("Abc21231321");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();
            // Kiểm tra thông báo lỗi email
            boolean isPhoneFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Mật khẩu không trùng khớp.')]")
            ).isDisplayed();
            Thread.sleep(2000);
            // Ghi lại kết quả kiểm tra
            if (isPhoneFormatErrorDisplayed) {
                TestNGResults.put("9", new Object[]{
                        8d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc21231321'\n",
                        "Nhập xác nhận mật khẩu không khớp",
                        "Thông báo lỗi 'Mật khẩu không trùng khớp.' hiển thị",
                        "pass",
                        "Đạt",
                        new Date()
                });
            } else {
                TestNGResults.put("9", new Object[]{
                        8d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc21231321'\n",
                        "Nhập xác nhận mật khẩu không khớp",
                        "Thông báo lỗi 'Mật khẩu không trùng khớp.' hiển thị",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail("Test gặp lỗi: Thông báo lỗi không hiển thị");
            }

        } catch (Exception e) {
            TestNGResults.put("9", new Object[]{
                    8d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0934417924',\n" +
                            "password = 'Abc123',\n" +
                            "retypePassword = 'Abc21231321'\n",
                    "Nhập xác nhận mật khẩu không khớp",
                    "Thông báo lỗi 'Mật khẩu không trùng khớp.' hiển thị",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail("Test gặp lỗi: " + e.getMessage());
        }
    }

    @Test(description = "Register Succecfully", priority = 9)
    public void registerSuccessfully() {
        try {
            // Reload lại trang để reset form
            driver.navigate().refresh();

            // Điền thông tin vào form với email sai định dạng
            driver.findElement(By.id("name")).sendKeys("Trần Thành Đạt");
            driver.findElement(By.id("email")).sendKeys("datttps37451@gmail.com");
            driver.findElement(By.id("phone")).sendKeys("0934417924");
            driver.findElement(By.id("password")).sendKeys("Abc123");
            driver.findElement(By.id("retypePassword")).sendKeys("Abc123");
            driver.findElement(By.tagName("body")).click();
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("form-check-label"))).perform();

            driver.findElement(By.className("btnSignin")).click();
            Thread.sleep(2000);

            // Chờ và xử lý alert
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept(); // Bấm OK

            String loginTitle = driver.findElement(By.tagName("h2")).getText();
            Thread.sleep(2000);

            Optional<User> savedUser = userRepository.findByEmail("datttps37451@gmail.com");

            if (loginTitle.equals("ĐĂNG NHẬP") && savedUser.isPresent()) {
                TestNGResults.put("10", new Object[]{
                        9d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc123'\n",
                        "Đăng ký một tài khoản",
                        "Đăng ký tài khoản thành công\n" +
                                "chuyển hướng để trang đăng nhập\n",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("10", new Object[]{
                        9d,
                        "\nname = 'Trần Thành Đạt',\n" +
                                "email = 'datttps37451@gmail.com',\n" +
                                "phone = '0934417924',\n" +
                                "password = 'Abc123',\n" +
                                "retypePassword = 'Abc123'\n",
                        "Đăng ký một tài khoản",
                        "Đăng ký tài khoản thành công\n" +
                                "chuyển hướng để trang đăng nhập\n",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail();
            }
            } catch(Exception e){
            TestNGResults.put("10", new Object[]{
                    9d,
                    "\nname = 'Trần Thành Đạt',\n" +
                            "email = 'datttps37451@gmail.com',\n" +
                            "phone = '0934417924',\n" +
                            "password = 'Abc123',\n" +
                            "retypePassword = 'Abc123'\n",
                    "Đăng ký một tài khoản",
                    "Đăng ký tài khoản thành công\n" +
                            "chuyển hướng để trang đăng nhập\n",
                    "fail",
                    "Đạt",
                    new Date()
            });
                Assert.fail("Test gặp lỗi: " + e.getMessage());
            }
        }

        private void applyFormattingAndFill () {
            Set<String> keyset = TestNGResults.keySet();
            int rownum = 0;

            // định nghĩa style cho header
            CellStyle headerStyle = workbook.createCellStyle();
            HSSFFont headerFont = workbook.createFont();
            //in đậm
            headerFont.setBold(true);
            // font size 12
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            // lề giữa
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // Đặt màu nền của tiêu đề là màu xanh sáng.
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //Thiết lập viền cho các ô (biên mỏng ở các cạnh).
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setWrapText(true); //Bật chế độ tự động xuống dòng cho văn bản trong ô.

            // định nghĩa style cho data
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setWrapText(true); // Enable text wrapping for data

            // style cho ngày tháng
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            // định dạng ngày tháng
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
    }
