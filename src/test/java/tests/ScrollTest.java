package tests;

import base.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.*;
import utils.TestDataLoader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Epic("Cart Functionality")
@Feature("Remove Product from Cart")
public class ScrollTest extends BaseTest {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;

    @Override
    protected void initializePageObjects() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
    }



    @Test(description = "Verify price of Sauce Labs Bolt T-Shirt")
    @Story("User verifies product price")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify the price of Sauce Labs Bolt T-Shirt")
    public void testPriceVerification() {
        String username = TestDataLoader.get("username");
        String password = TestDataLoader.get("password");

        loginPage.login(username, password);
        productsPage.scrollToProduct("Sauce Labs Bolt T-Shirt");
        productsPage.openProduct("Sauce Labs Bolt T-Shirt");
        productDetailPage.scrollToPrice();
        String price = productDetailPage.getPrice();

        test.info("Product price found: " + price);
        assertEquals(price, "$15.99");
    }

    @Test(description = "Add product to cart and verify name")
    @Story("User adds product to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to add a product to cart and verify the name matches")
    public void testAddToCartAndVerify() {
        String username = TestDataLoader.get("username");
        String password = TestDataLoader.get("password");
        String productName = TestDataLoader.get("productName");

        loginPage.login(username, password);
        productsPage.scrollToProduct(productName);
        productsPage.openProduct(productName);
        String productTitle = productDetailPage.getProductTitle(productName);
        productDetailPage.scrollToAddToCartButton();
        productDetailPage.addToCart();
        productDetailPage.openCart();
        String cartTitle = cartPage.getProductTitle(productName);

        assertEquals(productTitle, cartTitle, "Product title should match between product page and cart");
        test.pass("Product title matched between product page and cart");
    }

    @Test(description = "Add product to cart, remove it, and verify removal")
    @Story("User removes product from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to add a product to cart, remove it with swipe, and assert it's gone")
    public void testRemoveProductFromCart() {
        String username = TestDataLoader.get("username");
        String password = TestDataLoader.get("password");
        String productName = TestDataLoader.get("productName");

        loginPage.login(username, password);
        productsPage.scrollToProduct(productName);
        productsPage.openProduct(productName);
        productDetailPage.scrollToAddToCartButton();
        productDetailPage.addToCart();
        productDetailPage.openCart();
        cartPage.swipeToRemoveProduct(productName);
        cartPage.clickRemoveIcon();

        assertFalse(cartPage.isProductInCart(productName), "Product should be removed from cart");
        test.pass("Product successfully removed from cart");
    }
}