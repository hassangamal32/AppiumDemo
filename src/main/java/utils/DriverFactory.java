package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public static AppiumDriver createDriver(String platform) throws MalformedURLException {
        System.out.println("Creating driver for platform: " + platform);

        DesiredCapabilities caps = new DesiredCapabilities();
        URL appiumServerURL = new URL("http://127.0.0.1:4723/");

        if (platform.equalsIgnoreCase("android")) {
            // Android capabilities from config
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", ConfigManager.getProperty("automation.name.android"));
            caps.setCapability("appium:deviceName", ConfigManager.getProperty("device.name.android"));

            // Resolve app path
            String appPath = ConfigManager.getProperty("app.path.android");
            System.out.println("Config app path: " + appPath);

            File appFile = new File(appPath);
            if (!appFile.exists()) {
                // Try relative path from project root
                appFile = new File(System.getProperty("user.dir"), appPath);
                System.out.println("Trying project relative path: " + appFile.getAbsolutePath());
            }

            if (!appFile.exists()) {
                throw new RuntimeException("APK file not found: " + appFile.getAbsolutePath());
            }

            System.out.println("Using APK file: " + appFile.getAbsolutePath());
            caps.setCapability("appium:app", appFile.getAbsolutePath());
            caps.setCapability("appium:appActivity", ConfigManager.getProperty("app.activity.android"));
            caps.setCapability("appium:autoGrantPermissions", true);
            caps.setCapability("appium:noReset", false);

            System.out.println("Capabilities: " + caps);
            AndroidDriver driver = new AndroidDriver(appiumServerURL, caps);
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
            return driver;

        } else if (platform.equalsIgnoreCase("ios")) {
            // iOS capabilities from config
            caps.setCapability("platformName", "iOS");
            caps.setCapability("appium:automationName", ConfigManager.getProperty("automation.name.ios"));
            caps.setCapability("appium:deviceName", ConfigManager.getProperty("device.name.ios"));
            caps.setCapability("appium:app", ConfigManager.getProperty("app.path.ios"));
            caps.setCapability("appium:bundleId", ConfigManager.getProperty("app.bundle.id.ios"));

            return new IOSDriver(appiumServerURL, caps);
        }

        throw new IllegalArgumentException("Unsupported platform: " + platform);
    }
}