package bai22.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginFormParallelTest extends BaseParallelTest {
    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");

    @Test(description = "Chạy song song - kiểm thử form đăng nhập hiển thị")
    @Parameters({"baseUrl"})
    public void testLoginFormDisplayed(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        WebElement u = driver().findElement(USERNAME);
        WebElement p = driver().findElement(PASSWORD);
        WebElement b = driver().findElement(LOGIN_BUTTON);

        Assert.assertTrue(u.isDisplayed(), "Ô Username phải hiển thị!");
        Assert.assertTrue(p.isDisplayed(), "Ô Password phải hiển thị!");
        Assert.assertTrue(b.isDisplayed(), "Nút Login phải hiển thị!");
    }

    @Test(description = "Chạy song song - kiểm thử page source có chứa Swag Labs")
    @Parameters({"baseUrl"})
    public void testPageSource(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        Assert.assertTrue(driver().getPageSource().contains("Swag Labs"),
                "Page source phải chứa 'Swag Labs'!");
    }
}

