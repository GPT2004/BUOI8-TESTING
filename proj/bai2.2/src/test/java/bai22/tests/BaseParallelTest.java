package bai22.tests;

import bai22.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseParallelTest {
    protected static final int SCREENSHOT_DELAY_MS = 5_000;

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "driverPath"})
    public void setUp(@Optional("edge") String browser,
                      @Optional("") String driverPath) {
        DriverFactory.initDriver(browser, driverPath);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        // dừng 5s để chụp màn hình 2 cửa sổ chạy song song
        Thread.sleep(SCREENSHOT_DELAY_MS);
        DriverFactory.quitDriver();
    }
}

