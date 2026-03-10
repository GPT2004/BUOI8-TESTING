package bai62.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TextBoxPage {

    private final WebDriver driver;

    @FindBy(id = "userName")
    private WebElement nameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(id = "submit")
    private WebElement submitBtn;

    @FindBy(id = "output")
    private WebElement outputSection;

    @FindBy(css = "#userEmail.field-error, #userEmail[aria-invalid='true']")
    private WebElement emailError; // tùy DOM, ta dùng locator rộng để bắt lỗi email

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmit(String name, String email, String address) {
        nameField.clear();
        nameField.sendKeys(name);
        emailField.clear();
        emailField.sendKeys(email);
        currentAddressField.clear();
        currentAddressField.sendKeys(address);
        submitBtn.click();
    }

    public boolean isOutputDisplayed() {
        try {
            return outputSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getOutputText() {
        return outputSection.getText();
    }

    public boolean isEmailErrorDisplayed() {
        try {
            return emailField.getAttribute("class").contains("field-error");
        } catch (Exception e) {
            return false;
        }
    }
}

