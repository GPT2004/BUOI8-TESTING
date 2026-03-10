package bai22.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CartParallelTest extends BaseParallelTest {

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By INVENTORY_ITEM_ADD = By.cssSelector(".inventory_item button.btn_inventory");
    private static final By CART_LINK = By.id("shopping_cart_container");
    private static final By CART_ITEM = By.cssSelector(".cart_item");

    private void login() {
        WebElement u = driver().findElement(USERNAME);
        WebElement p = driver().findElement(PASSWORD);
        WebElement b = driver().findElement(LOGIN_BUTTON);
        u.clear();
        u.sendKeys(VALID_USER);
        p.clear();
        p.sendKeys(VALID_PASSWORD);
        b.click();
    }

    @Test(description = "Chạy song song - Login + add 1 sản phẩm, vào cart thấy item")
    @Parameters({"baseUrl"})
    public void testAddToCart_Parallel(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();

        driver().findElement(INVENTORY_ITEM_ADD).click();
        driver().findElement(CART_LINK).click();

        WebElement item = driver().findElement(CART_ITEM);
        Assert.assertTrue(item.isDisplayed(), "Trong giỏ hàng phải có ít nhất 1 item sau khi add.");
    }

    @Test(description = "Chạy song song - Login xong vào cart khi chưa add, không có item")
    @Parameters({"baseUrl"})
    public void testEmptyCart_Parallel(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        login();

        driver().findElement(CART_LINK).click();
        boolean hasItem = !driver().findElements(CART_ITEM).isEmpty();
        Assert.assertFalse(hasItem, "Chưa add sản phẩm thì cart không nên có item.");
    }
}

