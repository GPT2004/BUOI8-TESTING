package bai21.tests;

import bai21.base.BaseTest;
import bai21.pages.SdPages;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    @Test(groups = {"smoke", "regression"}, description = "Add to cart 1 sản phẩm -> vào cart thấy item")
    @Parameters({"baseUrl"})
    public void testAddToCart(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();
        waitDriver().until(d -> d.findElement(SdPages.INVENTORY_CONTAINER));

        driver().findElement(SdPages.FIRST_ADD_TO_CART).click();
        driver().findElement(SdPages.SHOPPING_CART_LINK).click();

        WebElement item = waitDriver().until(d -> d.findElement(SdPages.CART_ITEM));
        Assert.assertTrue(item.isDisplayed(), "Trong giỏ hàng phải có ít nhất 1 item sau khi add.");
    }

    @Test(groups = {"regression"}, description = "Vào cart khi chưa add -> không có item")
    @Parameters({"baseUrl"})
    public void testEmptyCartHasNoItems(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();
        waitDriver().until(d -> d.findElement(SdPages.INVENTORY_CONTAINER));

        driver().findElement(SdPages.SHOPPING_CART_LINK).click();
        boolean hasItem = !driver().findElements(SdPages.CART_ITEM).isEmpty();
        Assert.assertFalse(hasItem, "Chưa add sản phẩm thì cart không nên có item.");
    }

    private void login() {
        waitDriver().until(d -> d.findElement(SdPages.USERNAME)).clear();
        driver().findElement(SdPages.USERNAME).sendKeys(VALID_USER);
        driver().findElement(SdPages.PASSWORD).clear();
        driver().findElement(SdPages.PASSWORD).sendKeys(VALID_PASSWORD);
        driver().findElement(SdPages.LOGIN_BUTTON).click();
    }
}

