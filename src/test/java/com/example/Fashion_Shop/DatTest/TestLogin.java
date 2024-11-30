package com.example.Fashion_Shop.DatTest;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.web.servlet.MockMvc;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class TestLogin {
    WebDriver driver;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    private MockMvc mockMvc;

    private LinkedHashMap<String, Object[]> TestNGResults;

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
        saveExcelFile("DatTestLogin.xls");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test(description = "Open login", priority = 1)
    public void LaunchWebsite() {
        try {
            driver.get("http://localhost:4200/login");
            driver.manage().window().maximize();

            TestNGResults.put("2", new Object[]{
                    1d, "", "Mở trang đăng nhập", "\nTrang đăng nhập được mở\n", "pass", "Đạt", new Date()
            });
        } catch (Exception e) {
            TestNGResults.put("2", new Object[]{
                    1d, "", "Mở trang đăng nhập", "\nTrang đăng nhập được mở\n", "fail", "Đạt", new Date()
            });
            Assert.fail("Test gặp lỗi");
        }
    }

    @Test(description = "Required Fields", priority = 2)
    public void requiredFields(){
        try{
            driver.findElement(By.id("email")).sendKeys("");
            driver.findElement(By.id("password")).sendKeys("");
            driver.findElement(By.tagName("body")).click();

            boolean isEmailErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Vui lòng nhập email.')]")).isDisplayed();

            boolean isPasswordErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Vui lòng nhập email.')]")).isDisplayed();
            if(isEmailErrorDisplayed && isPasswordErrorDisplayed){
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nEmail: '',\nPassword: ''\n",
                        "Nhập trống trường Email và trường Password",
                        "\nHiển thị lỗi:\n'Vui lòng nhập email.'\nVui lòng nhập mật khẩu.\n",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nEmail: '',\nPassword: ''\n",
                        "Nhập trống trường Email và trường Password",
                        "\nHiển thị lỗi:\n'Vui lòng nhập email.'\nVui lòng nhập mật khẩu.\n",
                        "fail",
                        "Đạt",
                        new Date()
                });
            }
        }catch(Exception e){
                TestNGResults.put("3", new Object[]{
                        2d,
                        "\nEmail: '',\nPassword: ''\n",
                        "Nhập trống trường Email và trường Password",
                        "\nHiển thị lỗi:\n'Vui lòng nhập email.'\nVui lòng nhập mật khẩu.\n",
                        "fail",
                        "Đạt",
                        new Date()
                });
        }
    }

    @Test(description = "Email Format", priority = 3)
    public void emailFormat() {
        driver.navigate().refresh();
        try {
        driver.findElement(By.id("email")).sendKeys("datps37451");
        driver.findElement(By.id("password")).sendKeys("Abc123");
        driver.findElement(By.tagName("body")).click();

        boolean isEmailFormatErrorDisplay = driver.findElement(
                By.xpath("//div[contains(text(),'Vui lòng nhập email đúng định dạng.')]"))
                .isDisplayed();

        if(isEmailFormatErrorDisplay){
            TestNGResults.put("4", new Object[]{
                    3d,
                    "\nEmail: 'datps37451',\nPassword: ''\n",
                    "Nhập Email sai định dạng",
                    "Hiển thị lỗi:\n'Vui lòng nhập email đúng định dạng.'",
                    "pass",
                    "Đạt",
                    new Date()
            });
        }else{
            TestNGResults.put("4", new Object[]{
                    3d,
                    "\nEmail: 'datps37451',\nPassword: ''\n",
                    "Nhập Email sai định dạng",
                    "Hiển thị lỗi:\n'Vui lòng nhập email đúng định dạng.'",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail();
        }
        }catch(Exception e){
            TestNGResults.put("4", new Object[]{
                    3d,
                    "\nEmail: 'datps37451',\nPassword: ''\n",
                    "Nhập Email sai định dạng",
                    "Hiển thị lỗi:\n'Vui lòng nhập email đúng định dạng.'",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail(e.getMessage());
        }
    }

    @Test(description = "PasswordFormat",priority = 4)
    public void passwordFormat() {
        driver.navigate().refresh();
        try{
            driver.findElement(By.id("email")).sendKeys("datttps37451@fpt.edu.vn");
            driver.findElement(By.id("password")).sendKeys("abc");
            driver.findElement(By.tagName("body")).click();

            boolean isPasswordFormatErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.')]")
            ).isDisplayed();

            if(isPasswordFormatErrorDisplayed){
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'abc'\n",
                        "Nhập mật khẩu sai định dạng",
                        "\nHiển thị thông báo lỗi:\n'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.'",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("5", new Object[]{
                        4d,
                        "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'abc'\n",
                        "Nhập mật khẩu sai định dạng",
                        "\nHiển thị thông báo lỗi:\n'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.'",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail();
            }

        }catch (Exception e){
            TestNGResults.put("5", new Object[]{
                    4d,
                    "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'abc'\n",
                    "Nhập mật khẩu sai định dạng",
                    "\nHiển thị thông báo lỗi:\n'Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.'",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail(e.getMessage());
        }
    }

    @Test(description = "login failed", priority = 5)
    public void loginFailed() {
        driver.navigate().refresh();
        try{
        driver.findElement(By.id("email")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.tagName("body")).click();
        driver.findElement(By.className("btnLogin")).click();

        boolean isEmailErrorDisplayed = driver.findElement(
                By.xpath("//div[contains(text(),'Vui lòng nhập email.')]")).isDisplayed();

        boolean isPasswordErrorDisplayed = driver.findElement(
                By.xpath("//div[contains(text(),'Vui lòng nhập mật khẩu.')]")).isDisplayed();

        boolean isErrorDisplayed = driver.findElement(
                By.xpath("//div[contains(text(),'Email hoặc mật khẩu không đúng')]")).isDisplayed();

        if(isEmailErrorDisplayed && isPasswordErrorDisplayed && isErrorDisplayed){
            TestNGResults.put("6", new Object[]{
                    5d,
                    "\nEmail: '',\nPassword: ''\n",
                    "Nhập trống trường Email và trường Password nhấn nút đăng nhập",
                    "\nHiển thị lỗi:\n'Vui lòng nhập email.'\n'Vui lòng nhập mật khẩu.'" +
                            "\n'Email hoặc mật khẩu không đúng'",
                    "pass",
                    "Đạt",
                    new Date()
            });
        }else{
            TestNGResults.put("6", new Object[]{
                    5d,
                    "\nEmail: '',\nPassword: ''\n",
                    "Nhập trống trường Email và trường Password nhấn nút đăng nhập",
                    "\nHiển thị lỗi:\n'Vui lòng nhập email.'\n'Vui lòng nhập mật khẩu.'" +
                            "\n'Email hoặc mật khẩu không đúng'",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail();
        }
    }catch(Exception e){
            TestNGResults.put("6", new Object[]{
                    5d,
                    "\nEmail: '',\nPassword: ''\n",
                    "Nhập trống trường Email và trường Password nhấn nút đăng nhập",
                    "\nHiển thị lỗi:\n'Vui lòng nhập email.'\n'Vui lòng nhập mật khẩu.'" +
                            "\n'Email hoặc mật khẩu không đúng'",
                "fail",
                "Đạt",
                new Date()
        });
            Assert.fail(e.getMessage());
    }
    }

    @Test(description = "login failed", priority = 6)
    public void loginFailed2() {
        driver.navigate().refresh();
        try{
            driver.findElement(By.id("email")).sendKeys("datttps37451@fpt.edu.vn");
            driver.findElement(By.id("password")).sendKeys("Abc1234");
            driver.findElement(By.tagName("body")).click();
            driver.findElement(By.className("btnLogin")).click();

            boolean isErrorDisplayed = driver.findElement(
                    By.xpath("//div[contains(text(),'Email hoặc mật khẩu không đúng')]")).isDisplayed();

            if(isErrorDisplayed){
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'Abc1234'\n",
                        "Nhập sai thông tin đăng nhập của tài khoản datttps37451@fpt.edu.vn ",
                        "\nHiển thị lỗi:" +
                                "\n'Email hoặc mật khẩu không đúng'\n",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("7", new Object[]{
                        6d,
                        "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'Abc1234'\n",
                        "Nhập sai thông tin đăng nhập của tài khoản datttps37451@fpt.edu.vn ",
                        "\nHiển thị lỗi:" +
                                "\n'Email hoặc mật khẩu không đúng'\n",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail();
            }
        }catch(Exception e){
            TestNGResults.put("7", new Object[]{
                    6d,
                    "\nEmail: 'datttps37451@fpt.edu.vn',\nPassword: 'Abc1234'\n",
                    "Nhập sai thông tin đăng nhập của tài khoản datttps37451@fpt.edu.vn ",
                    "\nHiển thị lỗi:" +
                            "\n'Email hoặc mật khẩu không đúng'\n",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail(e.getMessage());
        }
    }

    @Test(description = "Client login success",priority = 7)
    public void loginSuccess() {
        driver.navigate().refresh();
        try{
            driver.findElement(By.id("email")).sendKeys("datttps37451@fpt.edu.vn");
            driver.findElement(By.id("password")).sendKeys("Abc123");
            driver.findElement(By.className("btnLogin")).click();

            boolean isHomepageDisplayed = driver.findElement(
                    By.xpath(
                            "//p[contains(text()," +
                                    "'Bản thân công ty đã là một công ty rất thành công. " +
                                    "Thường thì niềm vui đạt được kết quả')]"))
                    .isDisplayed();

            if(isHomepageDisplayed){
                TestNGResults.put("8", new Object[]{
                        7d,
                        "Email: 'datttps37451@fpt.edu.vn'\n'Password: 'Abc123''",
                        "Nhập Email và mật khẩu của tài khoản client datttps37451@fpt.edu.vn\n Chuyển trang sang homepage",
                        "Đăng nhập thành công và chuyển trang sang homepage",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("8", new Object[]{
                        7d,
                        "Email: 'datttps37451@fpt.edu.vn'\n'Password: 'Abc123''",
                        "Nhập Email và mật khẩu của tài khoản client datttps37451@fpt.edu.vn\n Chuyển trang sang homepage",
                        "Đăng nhập thành công và chuyển trang sang homepage",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail();
            }
        } catch (Exception e) {
            TestNGResults.put("8", new Object[]{
                    7d,
                    "Email: 'datttps37451@fpt.edu.vn'\n'Password: 'Abc123''",
                    "Nhập Email và mật khẩu của tài khoản client datttps37451@fpt.edu.vn\n Chuyển trang sang homepage",
                    "Đăng nhập thành công và chuyển trang sang homepage",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail(e.getMessage());
        }
    }

    @Test(description = "Admin login",priority = 8)
    public void adminLogin() {
        driver.navigate().to("http://localhost:4200/login");
        try{
            driver.findElement(By.id("email")).sendKeys("admin@admin.com");
            driver.findElement(By.id("password")).sendKeys("admin");
            driver.findElement(By.className("btnLogin")).click();

            boolean isHomepageDisplayed = driver.findElement(
                            By.xpath(
                                    "//div[contains(text()," +
                                            "'Admin')]"))
                    .isDisplayed();

            if(isHomepageDisplayed){
                TestNGResults.put("9", new Object[]{
                        8d,
                        "Email: 'admin@admin.com'\n'Password: 'admin''",
                        "Nhập Email và mật khẩu của tài khoản admin admin@admin.com\n Chuyển trang sang adminpage",
                        "Đăng nhập thành công và chuyển trang sang adminpage",
                        "pass",
                        "Đạt",
                        new Date()
                });
            }else{
                TestNGResults.put("9", new Object[]{
                        8d,
                        "Email: 'admin@admin.com'\n'Password: 'admin''",
                        "Nhập Email và mật khẩu của tài khoản admin admin@admin.com\n Chuyển trang sang adminpage",
                        "Đăng nhập thành công và chuyển trang sang adminpage",
                        "fail",
                        "Đạt",
                        new Date()
                });
                Assert.fail();
            }
        } catch (Exception e) {
            TestNGResults.put("9", new Object[]{
                    8d,
                    "Email: 'admin@admin.com'\n'Password: 'admin''",
                    "Nhập Email và mật khẩu của tài khoản admin admin@admin.com\n Chuyển trang sang adminpage",
                    "Đăng nhập thành công và chuyển trang sang adminpage",
                    "fail",
                    "Đạt",
                    new Date()
            });
            Assert.fail(e.getMessage());
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




}
