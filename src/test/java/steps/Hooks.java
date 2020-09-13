package steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import framework.web.CrossBrowserRemoteWebDriver;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class Hooks {
    private String browser;
    private String env;
    private RemoteWebDriver webDriver;
    CrossBrowserRemoteWebDriver crossBrowserRemoteWebDriver;

    private static Logger log = LoggerFactory.getLogger(Hooks.class);


    @Before
    public void InitializeTest() {
        System.setProperty("environment", "stage");
        crossBrowserRemoteWebDriver = new CrossBrowserRemoteWebDriver();
        browser = StringUtils.defaultIfBlank(System.getProperty("browser"), "chrome");
        env = StringUtils.defaultIfBlank(System.getProperty("environment"), "stage");
        System.out.println("Browser: " + browser);
        System.out.println("Environment: " + env);

        try {
            webDriver = crossBrowserRemoteWebDriver.createInstance(browser);
            WebDriverRunner.setWebDriver(webDriver);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Configuration.startMaximized = false;
        Configuration.reportsFolder = "target/failsafe-reports/Surefire suite/";
        System.out.println("Opening the browser: " + Configuration.browser);
    }


    @After
    public void TearDownTest(Scenario scenario) {

        String test = scenario.getName();
        if (scenario.isFailed()) {
            String filePath = Selenide.screenshot(test);
            File file = new File(filePath);
            try {
//                scenario.attach(Files.readAllBytes(file.toPath()),  test + ".png");
                //embed(Files.readAllBytes(file.toPath()),  test + ".png");
            } catch (Exception ex) {

            }
        } else {

        }
        if (!scenario.isFailed() ) {

        }
        clearBrowserCookies();
        WebDriverRunner.getWebDriver().quit();
        System.out.println("Closing the browser");
    }


}
