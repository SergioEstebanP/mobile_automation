package step_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import serenity_steps.CommonSteps;

public class HomepageDefinitions {

    @Steps(shared = true)
    private CommonSteps commonSteps;

    @Given("user opens the app")
    public void openApp() {
        commonSteps.opensTheApp();
    }

    @And("^taps on register button and see the register window$")
    public void tapsOnRegisterButton() {
        commonSteps.tapsOnRegisterSuccessfully();
    }

    @And("^types the name \"([^\"]*)\"$")
    public void typesTheName(String name) {
        commonSteps.typesName(name);
    }

    @And("^types the surname \"([^\"]*)\"$")
    public void typesTheSurname(String surname) {
        commonSteps.typesSurname(surname);
    }

    @And("^types the email \"([^\"]*)\"$")
    public void typesTheEmail(String email) {
        commonSteps.typesEmail(email);
    }

    @And("^types the password \"([^\"]*)\"$")
    public void typesThePassword(String pass) {
        commonSteps.typesPass(pass);
    }

    @And("^types the repeated password \"([^\"]*)\"$")
    public void typesTheRepeatedPassword(String pass) {
        commonSteps.typeRepeatedPass(pass);
    }

    @And("^checks the terms and conditions checkbox$")
    public void checksTheTermsAndConditionsCheckbox() {
        commonSteps.checksTermsAndConditions();
    }

    @And("^tap on accepts button$")
    public void tapOnAcceptsButton() {
        commonSteps.tapsOnAcceptButton();
    }
}
