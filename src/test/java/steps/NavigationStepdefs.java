package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pageObjects.*;
import io.cucumber.java.Scenario;

import static com.codeborne.selenide.Selenide.open;

public class NavigationStepdefs {

    private Scenario scenario;
    private boolean validateShippingMethodCount = false;

    @Before
    public void beforeStep(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("Anonymous user navigates to store")
    public void navigateToStorePage() {
        open(MainStorePage.URL);
    }

    @And("User accepts privacy settings")
    public void userAcceptsPrivacySettings() {
        BasePage.waitForPage(BasePage.ACCOUNT_SELECTOR);
        BasePage.acceptPrivacySettings();

    }


}
