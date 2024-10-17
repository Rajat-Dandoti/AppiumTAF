package org.example.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppiumServer {
    public WebDriver driver;
    private AppiumDriverLocalService service;
    private final Logger logger = Logger.getLogger(AppiumServer.class.getName());

    @BeforeClass
    public void setUp() {
        // Start Appium server programmatically
        try {
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File("node_modules/appium/build/lib/main.js"))
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .withArgument(() -> "--allow-insecure", "chromedriver_autodownload")
                    .build();
            service.start();
            logger.log(Level.INFO,"Appium server started");
        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            throw new RuntimeException(e);
        }

        // Set up desired capabilities for Android Virtual Device
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidEmulator");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("browserName", "Chrome");

        // Create AndroidDriver instance
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), capabilities);
            logger.log(Level.INFO, "Appium driver created");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create Appium driver", e);
        }
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
