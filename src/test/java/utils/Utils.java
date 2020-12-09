package utils;

import io.appium.java_client.android.AndroidDriver;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.LoggerFactory;
import test_runner.TestRunner;

import java.io.*;
import java.util.HashMap;

import static net.serenitybdd.rest.SerenityRest.rest;

public class Utils {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);
    public static AndroidDriver driver;

    public Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Download application from the page {code}https://apkcombo.com/es-es/{code}. Function has to different steps. As the
     * token, cpn and cra change in the request passed some time I have to make a workaround to get those values. Inspecting
     * the HTML of the previous request I see the <code>"href"<code/> of the download button has formed the correct URL
     * and the values set up properly. So
     * 1. Get the download HTML link.
     * 2. Parse the results where the parameters are located.
     * 3. Send the request to download the application.
     *
     * @param appName Exact name of the app we want to download, as it is in the browser or stored in
     *                Play Store from Google.
     * @return Response object with the file, in this case the APK
     */
    public static Response downloadAndSaveFromApi(String appName) {
        RequestSpecification getUrl = rest();
        // Step 1 - Get the download HTML link.
        getUrl.baseUri("https://apkcombo.com/es-es/wallbox/com.wallbox/download/apk");
        Response urlResponse = executeRequest(getUrl, "GET", "");
        // Step 2 - Parse the results where the parameters are located
        String urlResponseBody = urlResponse.getBody().asString();
        int index = urlResponse.getBody().asString().indexOf("https://gpudf.down-apk.com/download");
        String request = urlResponseBody.substring(index, index + 689);
        int endIndex = request.indexOf("\"");
        request = request.substring(0, endIndex);
        String[] params = request.split("\\?")[1].split("&");
        HashMap<String, String> parsedParams = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            if (i != 0) {
                params[i] = params[i].substring(4);
            }
            String[] aux = params[i].split("=");
            parsedParams.put(aux[0], aux[1]);
        }
        RequestSpecification requestSpecification = rest();
        requestSpecification.baseUri(Constant.APK_COMBO_URL);
        // Step 3 - Send the request to download the application
        return executeRequest(requestSpecification, "GET", Constant.DOWNLOAD_ENDPOINT +
                "token=" + parsedParams.get("token") + "&" +
                "cpn=" + parsedParams.get("cpn") + "&" +
                "name=" + appName + "&" +
                "package_name=" + parsedParams.get("package_name") + "&" +
                "cra=" + parsedParams.get("cra"));
    }

    /**
     * Check if the given directory/file exists already in the system in order to avoid Downloading the application
     *
     * @param outputDirectory
     * @return Boolean: True if the application Exists. False otherwise.
     */
    public static boolean checkIfAlreadyExistsApk(String outputDirectory) {
        return new File(outputDirectory).exists();
    }

    /**
     * Save the APK downloaded as a file in the computer
     *
     * @param outputDirectory Path where we want to save the APK file
     * @param response        Response with the APK object. Just a RAW data stream in this case.
     */
    public static void saveAsFile(String outputDirectory, Response response) {
        byte[] apk = response.asByteArray();
        try {
            File apkFile = new File(outputDirectory);
            OutputStream outStream = new FileOutputStream(apkFile);
            outStream.write(apk);
            outStream.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(String.valueOf(e));
            LOGGER.error("Output directory does not exists!");
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
            LOGGER.error("Can't write the APK in the directory!");
        }
    }

    /**
     * Wrapper to get a better handler for HTTP requests.
     *
     * @param request   Request object from Rest Assured library with the properly formed ready to execute.
     * @param operation Operation we want to execute. here we have a full control of the allowed operations and its handler.
     * @param endpoint  Endpoint we want to attack and add to the base URL
     * @return Response object with the response data of the request executed.
     */
    public static Response executeRequest(RequestSpecification request, String operation, String endpoint) {
        Response response;
        switch (operation.toUpperCase()) {
            case ("POST"):
                response = request.post(endpoint);
                break;
            case ("GET"):
                response = request.get(endpoint);
                break;
            case ("PUT"):
                response = request.put(endpoint);
                break;
            case ("PATCH"):
                response = request.patch(endpoint);
                break;
            case ("DELETE"):
                response = request.delete(endpoint);
                break;
            default:
                LOGGER.error("Operations allowed: [POST, GET, PUT, PATCH, DELETE]");
                response = null;
                break;
        }
        return response;
    }

    /**
     * Here we can decide and have a handler (like an interface) to decide which capabilities to load in any case. We can get
     * the the OS, or properties or devices from some config files, or env variables, parse the main decision here and then
     * load some capabilities or others for executing the tests.
     *
     * @param capabilities Capabilities object where we want to set all the functionalities.
     * @param os           Operative System in order to load some capabilities or others.
     * @return As I don't remember if the arguments in Java goes for value or reference I ensure the value is set properly returning
     * the object and assign in it also.
     */
    public static DesiredCapabilities setCapabilities(DesiredCapabilities capabilities, String os) {
        if (!os.equals("android")) {
            return loadIosCapabilites(capabilities);
        } else {
            return loadAndroidCapabilities(capabilities);
        }
    }

    private static DesiredCapabilities loadAndroidCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10.0");
        capabilities.setCapability("deviceName", "device-testing");
        capabilities.setCapability("app", "src/test/resources/app/testing.apk");
        capabilities.setCapability("appActivity", "com.wallbox.splash.presentation.SplashActivity");
        capabilities.setCapability("appPackage", "com.wallbox");
        return capabilities;
    }

    private static DesiredCapabilities loadIosCapabilites(DesiredCapabilities capabilities) {
        // still to implement
        return null;
    }

}