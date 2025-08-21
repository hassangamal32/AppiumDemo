package tests;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import utils.DriverUtils;
import utils.TestDataLoader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.*;
import utils.ExtentReportManager;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Epic("Cart Functionality")
@Feature("Remove Product from Cart")
public class ScrollTest {

    AndroidDriver driver;
    DriverUtils driverUtils;
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
        cap.setCapability("appium:deviceName", "RKCY4000D7T");
        cap.setCapability("appium:platformVersion", "15");
        cap.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");
        cap.setCapability("appium:app", "D:/APKs/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("appium:noReset", false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), cap);
        driverUtils = new DriverUtils(driver, 10);

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);

        test = ExtentReportManager.getReporter().createTest("Scroll Test");
    }

    @Test(description = "Verify price of Sauce Labs Bolt T-Shirt")
    public void testPriceVerification() {
        loginPage.login("standard_user", "secret_sauce");
        productsPage.scrollToProduct("Sauce Labs Bolt T-Shirt");
        driverUtils.waitForVisibility(By.xpath("//*[@text='Sauce Labs Bolt T-Shirt']"));
        productsPage.openProduct("Sauce Labs Bolt T-Shirt");
        driverUtils.waitForVisibility(By.xpath("//*[@text='Sauce Labs Bolt T-Shirt']"));

        productDetailPage.scrollToPrice();
        String price = productDetailPage.getPrice();

        test.log(Status.INFO, "Product price found: " + price);
        assertEquals(price, "$15.99");
        test.pass("Price verification passed");
    }

    @Test(description = "Add product to cart and verify name")
    public void testAddToCartAndVerify() {
        String username = TestDataLoader.get("username");
        String password = TestDataLoader.get("password");
        String productName = TestDataLoader.get("productName");

        loginPage.login(username, password);
        productsPage.scrollToProduct(productName);
        driverUtils.waitForVisibility(By.xpath("//*[@text='" + productName + "']"));
        productsPage.openProduct(productName);
        driverUtils.waitForVisibility(By.xpath("//*[@text='" + productName + "']"));
        productDetailPage.scrollToAddToCartButton();
        String productTitle = productDetailPage.getProductTitle(productName);
        productDetailPage.addToCart();
        driverUtils.waitForVisibility(By.className("android.widget.ImageView"));
        productDetailPage.openCart();
        driverUtils.waitForVisibility(By.xpath("//*[@text='" + productName + "']"));
        String cartTitle = cartPage.getProductTitle(productName);
        assertEquals(productTitle, cartTitle);
        test.pass("Product title matched between product page and cart");
    }

    @Test(description = "Add product to cart, remove it, and verify removal")
    @Story("User removes product from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to add a product to cart, remove it with swipe, and assert it's gone")
    public void testRemoveProductFromCart() {
        loginPage.login("standard_user", "secret_sauce");
        productsPage.scrollToProduct("Sauce Labs Onesie");
        driverUtils.waitForVisibility(By.xpath("//*[@text='Sauce Labs Onesie']"));
        productsPage.openProduct("Sauce Labs Onesie");
        driverUtils.waitForVisibility(By.xpath("//*[@text='Sauce Labs Onesie']"));
        productDetailPage.scrollToAddToCartButton();


        productDetailPage.addToCart();
        driverUtils.waitForVisibility(By.className("android.widget.ImageView"));
        productDetailPage.openCart();
        driverUtils.waitForVisibility(By.xpath("//*[@text='Sauce Labs Onesie']"));
        cartPage.swipeLeftOnProductFromCenter("Sauce Labs Onesie", 1000);
        cartPage.clickRemoveIcon();
        assertFalse(cartPage.isProductInCart("Sauce Labs Onesie"), "Product should be removed from cart");
        test.pass("Product successfully removed from cart");
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