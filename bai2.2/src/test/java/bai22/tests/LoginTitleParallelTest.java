package bai22.tests;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTitleParallelTest extends BaseParallelTest {

    @Test(description = "Chạy song song - kiểm thử title trang login")
    @Parameters({"baseUrl"})
    public void testTitle(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        String actualTitle = driver().getTitle();
        Assert.assertEquals(actualTitle, "Swag Labs", "Tiêu đề trang không đúng!");
    }

    @Test(description = "Chạy song song - kiểm thử URL chứa saucedemo")
    @Parameters({"baseUrl"})
    public void testUrl(@Optional("https://www.saucedemo.com/") String baseUrl) {
        driver().get(baseUrl);
        String url = driver().getCurrentUrl();
        Assert.assertTrue(url.contains("saucedemo"), "URL không hợp lệ!");
    }
}

