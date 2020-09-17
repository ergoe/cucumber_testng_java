package pageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {

    public static String URL = "bodybuilding.com";
    public final static By CHANGE_COUNTRY_TRIGGER = By.cssSelector(".CountrySelectTrigger-icon");
    public final static By CHANGE_COUNTRY_FLASH_MESSAGE = By.id("BbWrapperFlashMessage");
    public final static By ACCOUNT_SELECTOR = byText("Account");
    public final static By PRIVACY_SELECTOR = byText("Sounds good, thanks");
    public final static int TIMEOUT = 30000;

    public static void waitForPage(By selector) {
        $(selector).waitUntil(Condition.visible, TIMEOUT);
    }

    public static void selectCountry(String country) {
        $(CHANGE_COUNTRY_TRIGGER).shouldBe(visible).click();
        $(byText(country))
                .waitUntil(visible, TIMEOUT)
                .click();
        $(CHANGE_COUNTRY_FLASH_MESSAGE).waitUntil(disappear, TIMEOUT);
    }

    public static void acceptPrivacySettings() {
        SelenideElement element;
        element = $(PRIVACY_SELECTOR);
        if (element.exists()) {
            element.shouldBe(visible);
            if (element.isDisplayed()) {
                $(element).shouldBe(visible).click();
            } else {
                System.out.println("Privacy settings are not displayed");
            }
        }
    }

}