package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.*;

import utils.ExtentReportManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import static org.testng.Assert.assertEquals;

public class ScrollTest {

    AndroidDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;
    ProductsPage productsPage;
    ProductDetailPage productDetailPage;
    CartPage cartPage;

    ExtentTest test;

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

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), cap);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);

        test = ExtentReportManager.getReporter().createTest("Scroll Test");
    }


    @Test(description = "Verify price of Sauce Labs Bolt T-Shirt")
    public void testPriceVerification() throws InterruptedException {
        loginPage.login("standard_user", "secret_sauce");
        productsPage.scrollToProduct("Sauce Labs Bolt T-Shirt");
        Thread.sleep(3000);
        productsPage.openProduct("Sauce Labs Bolt T-Shirt");
        Thread.sleep(3000);

        productDetailPage.scrollToPrice();
        String price = productDetailPage.getPrice();

        test.log(Status.INFO, "Product price found: " + price);
        assertEquals(price, "$15.99");
        test.pass("Price verification passed");
    }

    @Test(description = "Add product to cart and verify name")
    public void testAddToCartAndVerify() throws InterruptedException {
        loginPage.login("standard_user", "secret_sauce");
        productsPage.scrollToProduct("Sauce Labs Onesie");
        productsPage.openProduct("Sauce Labs Onesie");

        String productTitle = productDetailPage.getProductTitle("Sauce Labs Onesie");
        productDetailPage.scrollToAddToCartButton();
        productDetailPage.addToCart();
        Thread.sleep(3000);
        productDetailPage.openCart();
        Thread.sleep(3000);
        String cartTitle = cartPage.getProductTitle("Sauce Labs Onesie");
        assertEquals(productTitle, cartTitle);
        test.pass("Product title matched between product page and cart");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
        ExtentReportManager.flushReports();
    }
    @AfterSuite(alwaysRun = true)
    public void tearDownReports() {
        ExtentReportManager.flushReports();
    }


}
