package pages;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.Pause;
import utils.ConfigManager;

import java.time.Duration;
import java.util.Collections;

public class BasePage {
    protected AppiumDriver driver;
    protected String platform;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.platform = ConfigManager.getPlatform();
    }

    public void longPress(WebElement element, int durationInSeconds) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        int centerX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int centerY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        Sequence longPress = new Sequence(finger, 1);
        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofSeconds(durationInSeconds)));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(longPress));
    }

    // Platform-specific element interaction methods
    protected String getPlatformSpecificText(String androidText, String iosText) {
        return platform.equalsIgnoreCase("android") ? androidText : iosText;
    }

    protected boolean isAndroid() {
        return platform.equalsIgnoreCase("android");
    }

    protected boolean isIOS() {
        return platform.equalsIgnoreCase("ios");
    }
}