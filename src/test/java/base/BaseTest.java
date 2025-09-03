package base;

import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.*;
import com.aventstack.extentreports.ExtentTest;
import java.lang.reflect.Method;

public class BaseTest {
    protected AppiumDriver driver;
    protected ExtentTest test;

    @BeforeSuite
    public void globalSetup() {
        // ConfigManager loads properties automatically in its static block
        AllureUtils.cleanAllureResults();
    }

    @BeforeMethod
    public void setup(Method method) throws Exception {
        String platform = ConfigManager.getPlatform();
        driver = DriverFactory.createDriver(platform);

        // Initialize page objects
        initializePageObjects();

        // Create ExtentTest instance
        test = ExtentReportManager.createTest(method.getName());
    }

    protected void initializePageObjects() {
        // This method will be overridden by test classes to initialize their page objects
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if (driver != null) {
            driver.quit();
        }

        // Log test status to ExtentReport
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        ExtentReportManager.flushReports();
    }

    @AfterSuite
    public void generateReports() {
        // Generate Allure report after all tests complete
        AllureReportGenerator.generateAllureReport();
    }
}