package bai62.tests;

import bai62.pages.TextBoxPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

public class TextBoxWhiteBoxTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Parameters({"baseUrl", "edgeDriverPath"})
    @BeforeMethod
    public void setUp(String baseUrl, @Optional("") String edgeDriverPath) {
        if (edgeDriverPath != null && !edgeDriverPath.isBlank() && new File(edgeDriverPath).exists()) {
            EdgeDriverService service = new EdgeDriverService.Builder()
                    .usingDriverExecutable(new File(edgeDriverPath))
                    .build();
            driver = new EdgeDriver(service);
        } else {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(3000); // thời gian chờ chụp màn hình
            driver.quit();
        }
    }

    @Test(description = "Happy path: name/email/address hợp lệ -> output hiển thị đầy đủ")
    public void testValidInput_ShowsOutput() {
        TextBoxPage page = new TextBoxPage(driver);
        page.fillAndSubmit("Nguyen Van A", "test@example.com", "HCM City");

        wait.until(d -> page.isOutputDisplayed());
        String output = page.getOutputText();

        Assert.assertTrue(page.isOutputDisplayed(), "Output section phải hiển thị sau khi submit hợp lệ.");
        Assert.assertTrue(output.contains("Nguyen Van A"), "Output phải chứa tên.");
        Assert.assertTrue(output.contains("test@example.com"), "Output phải chứa email.");
        Assert.assertTrue(output.contains("HCM City"), "Output phải chứa địa chỉ.");
    }

    @Test(description = "Tên rỗng nhưng email + address hợp lệ -> vẫn cho submit (white-box xác nhận)")
    public void testEmptyName_StillAccept() {
        TextBoxPage page = new TextBoxPage(driver);
        page.fillAndSubmit("", "test@example.com", "Address 1");

        wait.until(d -> page.isOutputDisplayed());
        String output = page.getOutputText();

        Assert.assertTrue(output.contains("Name:"), "Output vẫn phải render phần Name (có thể rỗng).");
    }

    @Test(description = "Email sai định dạng -> không có output, highlight lỗi email")
    public void testInvalidEmail_ShowsClientValidation() {
        TextBoxPage page = new TextBoxPage(driver);
        page.fillAndSubmit("User A", "invalid-email", "Address X");

        // Chờ một chút để client validation chạy
        wait.until(ExpectedConditions.attributeContains(
                driver.findElement(org.openqa.selenium.By.id("userEmail")),
                "class", "field-error"));

        Assert.assertFalse(page.isOutputDisplayed(), "Khi email sai định dạng, phần output không nên hiển thị.");
        Assert.assertTrue(page.isEmailErrorDisplayed(), "Trường email phải bị đánh dấu lỗi (class field-error).");
    }

    @Test(description = "Địa chỉ rỗng nhưng name + email hợp lệ -> vẫn hiển thị output (boundary)")
    public void testEmptyAddress_StillOutput() {
        TextBoxPage page = new TextBoxPage(driver);
        page.fillAndSubmit("User B", "userb@example.com", "");

        wait.until(d -> page.isOutputDisplayed());
        String output = page.getOutputText();

        Assert.assertTrue(output.contains("User B"), "Output phải hiển thị tên.");
        Assert.assertTrue(output.contains("userb@example.com"), "Output phải hiển thị email.");
    }

    @Test(description = "Các ký tự đặc biệt trong địa chỉ -> vẫn lưu và hiển thị đúng")
    public void testAddressWithSpecialChars() {
        TextBoxPage page = new TextBoxPage(driver);
        String address = "12/5 Đường A, P.B, Q.C";
        page.fillAndSubmit("User C", "userc@example.com", address);

        wait.until(d -> page.isOutputDisplayed());
        String output = page.getOutputText();

        Assert.assertTrue(output.contains(address), "Output phải hiển thị đúng địa chỉ có ký tự đặc biệt.");
    }
}

