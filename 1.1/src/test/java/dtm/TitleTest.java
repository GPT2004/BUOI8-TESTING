package dtm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class TitleTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);
    private static final int SCREENSHOT_DELAY_MS = 5_000;
    private static final String DEFAULT_EDGE_DRIVER_PATH = "D:\\Download\\edgedriver_win64\\msedgedriver.exe";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        String driverPath = System.getProperty("webdriver.edge.driver", DEFAULT_EDGE_DRIVER_PATH);
        File driverFile = new File(driverPath);
        if (driverFile.exists()) {
            EdgeDriverService service = new EdgeDriverService.Builder()
                    .usingDriverExecutable(driverFile)
                    .build();
            driver = new EdgeDriver(service);
        } else {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(SCREENSHOT_DELAY_MS);
            driver.quit();
        }
    }

    @Test(description = "Kiem thu tieu de trang chu")
    public void testTitle() {
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Tieu de trang khong dung!");
    }

    @Test(description = "Kiem thu URL trang chu")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("saucedemo"), "URL khong hop le!");
    }

    @Test(description = "Kiem thu nguon trang co chua Swag Labs")
    public void testPageSourceContainsSwagLabs() {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Swag Labs"), "Page source phai chua 'Swag Labs'!");
    }

    @Test(description = "Kiem thu form dang nhap co hien thi")
    public void testLoginFormDisplayed() {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        Assert.assertTrue(username.isDisplayed(), "Username input phai hien thi!");
        Assert.assertTrue(password.isDisplayed(), "Password input phai hien thi!");
        Assert.assertTrue(loginButton.isDisplayed(), "Login button phai hien thi!");
    }
}

