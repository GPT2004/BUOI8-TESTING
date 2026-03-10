package bai21.pages;

import org.openqa.selenium.By;

public final class SdPages {
    private SdPages() {}

    public static final By USERNAME = By.id("user-name");
    public static final By PASSWORD = By.id("password");
    public static final By LOGIN_BUTTON = By.id("login-button");
    public static final By ERROR = By.cssSelector("[data-test='error']");

    public static final By INVENTORY_CONTAINER = By.id("inventory_container");
    public static final By FIRST_ADD_TO_CART = By.cssSelector("[data-test^='add-to-cart']");
    public static final By SHOPPING_CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");
    public static final By CART_ITEM = By.cssSelector("[data-test='inventory-item']");
    public static final By CHECKOUT = By.id("checkout");

    public static final By FIRST_NAME = By.id("first-name");
    public static final By LAST_NAME = By.id("last-name");
    public static final By POSTAL_CODE = By.id("postal-code");
    public static final By CONTINUE = By.id("continue");
    public static final By FINISH = By.id("finish");
    public static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");
}

