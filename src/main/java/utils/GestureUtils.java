package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Collections;

public class GestureUtils {
    AppiumDriver driver;
    public static void swipeElementLeft(AppiumDriver driver, WebElement element, int durationMs) {
        String platform = driver.getCapabilities().getCapability("platformName").toString();

        if (platform.equalsIgnoreCase("android")) {
            // Android implementation
            int startX = element.getLocation().getX() + element.getSize().getWidth() - 10;
            int endX = element.getLocation().getX() + 10;
            int y = element.getLocation().getY() + (element.getSize().getHeight() / 2);

            performSwipe(driver, startX, y, endX, y, durationMs);
        } else {
            // iOS implementation
            int startX = (int) (element.getLocation().getX() + element.getSize().getWidth() * 0.9);
            int endX = (int) (element.getLocation().getX() + element.getSize().getWidth() * 0.1);
            int y = element.getLocation().getY() + (element.getSize().getHeight() / 2);

            performSwipe(driver, startX, y, endX, y, durationMs);
        }
    }
    private static void performSwipe(AppiumDriver driver, int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public static void swipeElementLeftFromCenter(AppiumDriver driver, WebElement element, int durationMs) {
        int centerX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int centerY = element.getLocation().getY() + (element.getSize().getHeight() / 2);
        int endX = centerX - (element.getSize().getWidth() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, centerY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"))"
        ));
    }

}