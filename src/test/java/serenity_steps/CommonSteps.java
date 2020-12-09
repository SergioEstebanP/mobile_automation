package serenity_steps;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import page_object.HomePage;
import page_object.RegisterPage;

import java.util.Random;

public class CommonSteps {

    public HomePage homePage;
    public RegisterPage registerPage;

    @Step
    public void opensTheApp() {
        homePage = new HomePage();
        Assert.assertTrue(homePage.opensTheApp());
    }

    @Step
    public void tapsOnRegisterSuccessfully() {
        homePage.tapsOnRegister();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerPage = new RegisterPage();
        Assert.assertTrue(registerPage.titleIsShown());
    }

    @Step
    public void typesEmail(String email) {
        if (!"valid".equals(email)) {
            registerPage.typesEmail(email);
        } else {
            registerPage.typesEmail(new Random().nextInt() + "@mail.com");
        }
    }

    @Step
    public void typesName(String name) {
        registerPage.typesName(name);
    }

    @Step
    public void typesSurname(String surname) {
        registerPage.typesSurname(surname);
    }

    @Step
    public void typesPass(String pass) {
        registerPage.typesPassword(pass);
    }

    @Step
    public void typeRepeatedPass(String pass) {
        registerPage.typesRepeatedPassword(pass);
    }

    @Step
    public void checksTermsAndConditions() {
        registerPage.checkTermsAndConditions();
    }

    public void tapsOnAcceptButton() {
        registerPage.tapsOnAcceptButton();
    }
}
