package test_runner;


import cucumber.api.CucumberOptions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.restassured.response.Response;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Configuration;
import utils.Constant;
import utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {
                "pretty",
                "html:target/cucumber-html-report",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-report.xml"},
        glue = "step_definitions"
)

public class TestRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);
    private static final AppiumDriverLocalService APPIUM_SERVER = AppiumDriverLocalService.buildDefaultService();

    public TestRunner() {
        // no need to initialize anything here
    }

    /**
     * ISP Principle: code against interfaces. In this case we decide to download the application from
     * APKCombo replicating the requests you can do with the browser as its a simple GET with some params.
     */
    @BeforeClass
    public static void downloadApp() {
        LOGGER.info("> Downloading application ...");
        String appName = Configuration.getConfiguration().getAppName();
        if (!Utils.checkIfAlreadyExistsApk(Constant.OUTPUT_DIURECTORY)) {
            Response response = Utils.downloadAndSaveFromApi(appName);
            Assert.assertNotNull(response);
            Assert.assertEquals(response.statusCode(), 200);
            LOGGER.info("> Application: " + appName + " downloaded Successfully!");
            Utils.saveAsFile(Constant.OUTPUT_DIURECTORY, response);
            LOGGER.info("> Application: " + appName + "saved in: " + Constant.OUTPUT_DIURECTORY);
        } else {
            LOGGER.info(Constant.OUTPUT_DIURECTORY + " already exists. Not downloading new apk.");
        }

    }

    /**
     * Start Appium server if its not running
     */
    @BeforeClass
    public static void startServer() {
        if (!APPIUM_SERVER.isRunning()) {
            APPIUM_SERVER.start();
            LOGGER.info("> Appium server started successfully!");
        } else {
            LOGGER.info("> Appium server already running");
        }
    }

    /**
     * Set up driver for Android or iOS and the capabilities
     */
    @BeforeClass
    public static void setUpDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities = Utils.setCapabilities(capabilities, "android");
        try {
            Utils.driver = new AndroidDriver(new URL(Constant.APPIUM_SERVER), capabilities);
        } catch (MalformedURLException e) {
            LOGGER.error(String.valueOf(e));
        }
    }

    /**
     * Stop the server at the end of the tests
     */
    @AfterClass
    public static void stopServer() {
        APPIUM_SERVER.stop();
        LOGGER.info("> Appium server stopped successfully!");
    }

    /**
     * Not implemented but we could implement some methods to centralize the reports in S3 with some simple scripts and
     * as the index.html of the S3 as static webpage to see all the reports.
     */
    @AfterClass
    public static void uploadSerenityReports() {
        // Utils.uploadReportsToS3();
        // LOGGER.info("> Serenity reports uploaded successfully!");
    }
}
