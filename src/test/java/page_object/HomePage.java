package page_object;

import org.openqa.selenium.WebElement;
import utils.Utils;

public class HomePage {

    // BUILDER
    public HomePage() {
        // Nothing to initialize here
    }

    // ITEMS
    WebElement loginLogo = Utils.driver.findElementById("com.wallbox:id/imgLoginLogo");
    WebElement registerBtn = Utils.driver.findElementById("com.wallbox:id/btnRegister");

    // INTERACTIONS
    public boolean opensTheApp() {
        return loginLogo.isDisplayed();
    }

    public void tapsOnRegister() {
        registerBtn.isDisplayed();
        registerBtn.click();
    }
}
