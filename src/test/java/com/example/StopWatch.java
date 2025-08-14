package com.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import static org.testng.Assert.assertEquals;

public class StopWatch {

    @Test
    public void Setup() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("platformName","Android");
        cap.setCapability("appium:automationName","UiAutomator2");
        cap.setCapability("appium:deviceName","AVD1");
        cap.setCapability("appium:platformVersion","15");
        cap.setCapability("appium:appPackage","com.google.android.deskclock");
        cap.setCapability("appium:appActivity","com.android.deskclock.DeskClock");

        URL url = new URL("http://127.0.0.1:4723/");
        AndroidDriver driver = new AndroidDriver(url,cap);
        driver.findElement(AppiumBy.accessibilityId("Stopwatch")).click();
        String getText = driver.findElement(AppiumBy.id("com.google.android.deskclock:id/action_bar_title")).getText();

        assertEquals(getText,"Stopwatch");

        driver.terminateApp("com.google.android.deskclock");
    }
}
