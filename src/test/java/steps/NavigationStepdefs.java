package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.simple.parser.ParseException;
import pageObjects.*;
import io.cucumber.java.Scenario;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

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
        sleep(3000);
    }

    @And("User accepts privacy settings")
    public void userAcceptsPrivacySettings() {
        if (!System.getProperty("environment").equalsIgnoreCase("stage")) {
            try {
                Thread.sleep(6000);
            } catch (Exception ex) {

            }
            BasePage.waitForPage(BasePage.ACCOUNT_SELECTOR);
            BasePage.acceptPrivacySettings();
        } else {
            BasePage.waitForPage(BasePage.ACCOUNT_SELECTOR);
            BasePage.acceptPrivacySettings();
        }
    }


}
