package utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static java.nio.file.Files.newInputStream;

public class AllureUtils {
    public static void cleanAllureResults(){
        FileUtils.deleteQuietly(new File("test-output/allure-results"));
    }

    public static void attacheScreenshotsToAllure(String screenName, String screenPath){
        try {
            File screenshotFile = new File(screenPath);
            Allure.addAttachment(screenName, newInputStream(Path.of(screenPath)));
            FileUtils.deleteQuietly(screenshotFile);
        }catch (Exception e){
            System.out.println("Error"+ e.getMessage());
        }
    }
}
