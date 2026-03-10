package com.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

/**
 * Bài tập 1.2 - Kiểm thử form đăng nhập saucedemo.com
 * Dùng @BeforeMethod / @AfterMethod, WebDriverWait, Assert có message.
 * Sau mỗi test dừng 5s để chụp màn hình. Report: test-output (TestNG HTML).
 */
public class LoginTest {

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String LOCKED_USER = "locked_out_user";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);
    /** Dừng 5 giây sau mỗi test để chụp màn hình */
    private static final int SCREENSHOT_DELAY_MS = 5_000;
    /** Đường dẫn EdgeDriver local */
    private static final String DEFAULT_EDGE_DRIVER_PATH = "D:\\Download\\edgedriver_win64\\msedgedriver.exe";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void openBrowser() {
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
        wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(SCREENSHOT_DELAY_MS);
            driver.quit();
        }
    }

    @Test
    public void testLoginSuccess() {
        driver.get(BASE_URL);
        login(VALID_USER, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/inventory.html"),
                "Sau khi đăng nhập thành công phải chuyển đến /inventory.html, nhưng URL hiện tại là: " + currentUrl);
    }

    @Test
    public void testLoginWrongPassword() {
        driver.get(BASE_URL);
        login(VALID_USER, "wrong_password");
        WebElement errorEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String errorText = errorEl.getText();
        assertTrue(errorText != null && !errorText.isEmpty(),
                "Phải hiển thị thông báo lỗi trên trang đăng nhập khi nhập sai mật khẩu. Nội dung lỗi: " + errorText);
    }

    @Test
    public void testLoginEmptyUsername() {
        driver.get(BASE_URL);
        login("", VALID_PASSWORD);
        WebElement errorEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String errorText = errorEl.getText();
        assertTrue(errorText != null && errorText.contains("Username is required"),
                "Thông báo lỗi phải chứa 'Username is required'. Thực tế: " + errorText);
    }

    @Test
    public void testLoginEmptyPassword() {
        driver.get(BASE_URL);
        login(VALID_USER, "");
        WebElement errorEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String errorText = errorEl.getText();
        assertTrue(errorText != null && errorText.contains("Password is required"),
                "Thông báo lỗi phải chứa 'Password is required'. Thực tế: " + errorText);
    }

    @Test
    public void testLoginLockedUser() {
        driver.get(BASE_URL);
        login(LOCKED_USER, VALID_PASSWORD);
        WebElement errorEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String errorText = errorEl.getText();
        assertTrue(errorText != null && errorText.contains("Sorry, this user has been locked out"),
                "Thông báo lỗi phải chứa 'Sorry, this user has been locked out'. Thực tế: " + errorText);
    }

    private void login(String username, String password) {
        WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passInput = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        userInput.clear();
        userInput.sendKeys(username);
        passInput.clear();
        passInput.sendKeys(password);
        loginBtn.click();
    }
}
