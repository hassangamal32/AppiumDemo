package AppiumSauceLabsApp;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class Login {

    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:deviceName", "AVD1");
        cap.setCapability("appium:platformVersion", "15");
        cap.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");
        cap.setCapability("appium:app", "D:/APKs/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("appium:noReset", false);

        URL url = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver(url, cap);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void loginWithValidCredentials() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")")
        ));

        String getText = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")")).getText();
        assertEquals(getText, "PRODUCTS");
    }

    @Test
    public void loginWithInvalidUsername() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("wrong_name");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();


         String errorText = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Username and password do not match any user in this service.\")")).getText();
         assertEquals(errorText, "Username and password do not match any user in this service.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
