package bai21.tests;

import bai21.base.BaseTest;
import bai21.pages.SdPages;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String LOCKED_USER = "locked_out_user";

    @Test(groups = {"smoke", "regression"}, description = "Đăng nhập đúng -> vào inventory")
    @Parameters({"baseUrl"})
    public void testLoginSuccess(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login(VALID_USER, VALID_PASSWORD);
        waitDriver().until(d -> d.getCurrentUrl().contains("/inventory.html"));
        Assert.assertTrue(driver().getCurrentUrl().contains("/inventory.html"),
                "Đăng nhập thành công phải chuyển sang /inventory.html nhưng URL = " + driver().getCurrentUrl());
    }

    @Test(groups = {"regression"}, description = "Sai mật khẩu -> hiển thị lỗi")
    @Parameters({"baseUrl"})
    public void testLoginWrongPasswordShowsError(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login(VALID_USER, "wrong_password");
        WebElement errorEl = waitDriver().until(d -> d.findElement(SdPages.ERROR));
        Assert.assertTrue(errorEl.isDisplayed(), "Phải hiển thị error khi nhập sai mật khẩu.");
        Assert.assertTrue(errorEl.getText() != null && !errorEl.getText().isBlank(),
                "Nội dung error không được rỗng.");
    }

    @Test(groups = {"regression"}, description = "Locked user -> hiển thị locked out")
    @Parameters({"baseUrl"})
    public void testLoginLockedUser(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login(LOCKED_USER, VALID_PASSWORD);
        WebElement errorEl = waitDriver().until(d -> d.findElement(SdPages.ERROR));
        String msg = errorEl.getText();
        Assert.assertTrue(msg != null && msg.contains("locked out"),
                "Thông báo phải chứa 'locked out'. Thực tế: " + msg);
    }

    private void login(String username, String password) {
        waitDriver().until(d -> d.findElement(SdPages.USERNAME)).clear();
        driver().findElement(SdPages.USERNAME).sendKeys(username);
        driver().findElement(SdPages.PASSWORD).clear();
        driver().findElement(SdPages.PASSWORD).sendKeys(password);
        driver().findElement(SdPages.LOGIN_BUTTON).click();
    }
}

