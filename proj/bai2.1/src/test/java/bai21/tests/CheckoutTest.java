package bai21.tests;

import bai21.base.BaseTest;
import bai21.pages.SdPages;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    @Test(groups = {"smoke", "regression"}, description = "Checkout happy path -> Complete!")
    @Parameters({"baseUrl"})
    public void testCheckoutSuccess(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();
        waitDriver().until(d -> d.findElement(SdPages.INVENTORY_CONTAINER));

        driver().findElement(SdPages.FIRST_ADD_TO_CART).click();
        driver().findElement(SdPages.SHOPPING_CART_LINK).click();
        waitDriver().until(d -> d.findElement(SdPages.CART_ITEM));

        driver().findElement(SdPages.CHECKOUT).click();
        waitDriver().until(d -> d.findElement(SdPages.FIRST_NAME));

        driver().findElement(SdPages.FIRST_NAME).sendKeys("Thanh");
        driver().findElement(SdPages.LAST_NAME).sendKeys("Test");
        driver().findElement(SdPages.POSTAL_CODE).sendKeys("700000");
        driver().findElement(SdPages.CONTINUE).click();

        waitDriver().until(d -> d.findElement(SdPages.FINISH)).click();

        WebElement header = waitDriver().until(d -> d.findElement(SdPages.COMPLETE_HEADER));
        Assert.assertTrue(header.getText() != null && header.getText().toUpperCase().contains("THANK YOU"),
                "Checkout thành công phải có 'THANK YOU'. Thực tế: " + header.getText());
    }

    @Test(groups = {"regression"}, description = "Checkout thiếu first name -> báo lỗi")
    @Parameters({"baseUrl"})
    public void testCheckoutMissingFirstNameShowsError(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();
        waitDriver().until(d -> d.findElement(SdPages.INVENTORY_CONTAINER));

        driver().findElement(SdPages.FIRST_ADD_TO_CART).click();
        driver().findElement(SdPages.SHOPPING_CART_LINK).click();
        waitDriver().until(d -> d.findElement(SdPages.CART_ITEM));

        driver().findElement(SdPages.CHECKOUT).click();
        waitDriver().until(d -> d.findElement(SdPages.FIRST_NAME));

        driver().findElement(SdPages.LAST_NAME).sendKeys("Test");
        driver().findElement(SdPages.POSTAL_CODE).sendKeys("700000");
        driver().findElement(SdPages.CONTINUE).click();

        WebElement errorEl = waitDriver().until(d -> d.findElement(SdPages.ERROR));
        Assert.assertTrue(errorEl.getText() != null && errorEl.getText().toLowerCase().contains("first name"),
                "Phải báo lỗi thiếu First Name. Thực tế: " + errorEl.getText());
    }

    private void login() {
        waitDriver().until(d -> d.findElement(SdPages.USERNAME)).clear();
        driver().findElement(SdPages.USERNAME).sendKeys(VALID_USER);
        driver().findElement(SdPages.PASSWORD).clear();
        driver().findElement(SdPages.PASSWORD).sendKeys(VALID_PASSWORD);
        driver().findElement(SdPages.LOGIN_BUTTON).click();
    }
}

