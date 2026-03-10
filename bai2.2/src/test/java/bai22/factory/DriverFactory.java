package bai22.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public final class DriverFactory {
    private DriverFactory() {}

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static void initDriver(String browser, String driverPath) {
        WebDriver driver;
        String b = browser == null ? "" : browser.trim().toLowerCase();

        switch (b) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
            default:
                driver = createEdge(driverPath);
                break;
        }

        driver.manage().window().maximize();
        tlDriver.set(driver);
    }

    private static WebDriver createEdge(String driverPath) {
        if (driverPath != null && !driverPath.isBlank()) {
            File f = new File(driverPath);
            if (f.exists()) {
                EdgeDriverService service = new EdgeDriverService.Builder()
                        .usingDriverExecutable(f)
                        .build();
                return new EdgeDriver(service);
            }
        }
        return new EdgeDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
        }
        tlDriver.remove(); // rất quan trọng: tránh memory leak khi chạy parallel
    }
}

