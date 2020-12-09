package page_object;

import org.openqa.selenium.WebElement;
import utils.Constant;
import utils.Utils;

public class RegisterPage {

    // BUILDER
    public RegisterPage() {
        // Nothing to initialize here
    }

    // ITEMS
    private WebElement title = Utils.driver.findElementById(Constant.WB_PKG + "txtTitle");
    private WebElement name = Utils.driver.findElementById(Constant.WB_PKG + "edtName");
    private WebElement surname = Utils.driver.findElementById(Constant.WB_PKG + "edtSurname");
    private WebElement email = Utils.driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/android.widget.ScrollView/android.view.ViewGroup/android.widget.LinearLayout[3]/android.widget.FrameLayout/android.widget.EditText\n");
    private WebElement password1 = Utils.driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/android.widget.ScrollView/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.FrameLayout/android.widget.EditText\n");
    private WebElement password2 = Utils.driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + "Repeat password" + "\").instance(0))");
    private WebElement privacyTerms = Utils.driver.findElementById(Constant.WB_PKG + "chkPrivacy");
    private WebElement accept = Utils.driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + "ACCEPT" + "\").instance(0))");

    // INTERACTIONS
    public boolean titleIsShown() {
        return title.isDisplayed();
    }

    public void typesName(String name) {
        this.name.click();
        this.name.sendKeys(name);
    }

    public void typesSurname(String surname) {
        this.surname.click();
        this.surname.sendKeys(surname);
    }

    public void typesEmail(String email) {
        this.email.click();
        this.email.sendKeys(email);
    }

    public void typesPassword(String pass) {
        this.password1.click();
        this.password1.sendKeys(pass);
        Utils.driver.hideKeyboard();
    }

    public void typesRepeatedPassword(String pass) {
        this.password2.click();
        this.password2.sendKeys(pass);
        Utils.driver.hideKeyboard();
    }

    public void checkTermsAndConditions() {
        this.privacyTerms.click();
    }

    public void tapsOnAcceptButton() {
        this.accept.click();
    }
}
