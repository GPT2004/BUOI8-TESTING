package bai21.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);
    private static final int SCREENSHOT_DELAY_MS = 5_000;

    private final ThreadLocal<WebDriver> driverTL = new ThreadLocal<>();
    private final ThreadLocal<WebDriverWait> waitTL = new ThreadLocal<>();

    protected WebDriver driver() {
        return driverTL.get();
    }

    protected WebDriverWait waitDriver() {
        return waitTL.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"driverPath"})
    public void setUp(@Optional("") String driverPath) {
        WebDriver driver;
        if (driverPath != null && !driverPath.isBlank() && new File(driverPath).exists()) {
            EdgeDriverService service = new EdgeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath))
                    .build();
            driver = new EdgeDriver(service);
        } else {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driverTL.set(driver);
        waitTL.set(new WebDriverWait(driver, WAIT_TIMEOUT));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        WebDriver driver = driverTL.get();
        if (driver != null) {
            Thread.sleep(SCREENSHOT_DELAY_MS);
            driver.quit();
        }
        waitTL.remove();
        driverTL.remove();
    }
}

