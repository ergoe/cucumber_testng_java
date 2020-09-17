package framework.web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static java.lang.String.format;

/**
 * Created by Eric on 7/8/18.
 */
public class CrossBrowserRemoteWebDriver {
    /** When running local  */
//    private String hubUrl = "http://0.0.0.0:4444/wd/hub";
    /** When running from Jenkins */
    private String hubUrl = "http://10.0.1.55:4444/wd/hub";
    private RemoteWebDriver innerDriver;

    private URL seleniumGridUrl() throws MalformedURLException {
        String environmentUrl = StringUtils.defaultIfBlank(System.getProperty("seleniumUrl"), hubUrl);
        System.out.println("SeleniumGrid1: " + environmentUrl);
        URL url = new URL(hubUrl);
        return url;
    }

    public RemoteWebDriver createInstance(String browserName) throws MalformedURLException {
        RemoteWebDriver remoteWebDriver = null;
        if (browserName.toLowerCase().contains("firefox")) {
            remoteWebDriver = new RemoteWebDriver(seleniumGridUrl(), firefoxCapabilities());
            return remoteWebDriver;
        }

        if (browserName.toLowerCase().contains("iphone")) {
            remoteWebDriver = new RemoteWebDriver(seleniumGridUrl(), iphoneEmulatorCapabilities());
            return remoteWebDriver;
        }

        if (browserName.toLowerCase().contains("chrome")) {
            try {
                remoteWebDriver = new RemoteWebDriver(seleniumGridUrl(), chromeCapabilities());
            } catch (Exception ex) {
                System.out.println("");
            }
            innerDriver = remoteWebDriver;
            return innerDriver;
        }
        innerDriver = remoteWebDriver;
        return innerDriver;
    }

    private static DesiredCapabilities firefoxCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");

        return capabilities;
    }

    private static DesiredCapabilities chromeCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");

        return capabilities;
    }

    private static DesiredCapabilities iphoneEmulatorCapabilities() {
        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        Dictionary<String, List<String>> browserOptionsDictionary = new Hashtable<>();
        browserOptionsDictionary.put("args", Arrays.asList(
                "--ignore-certificate-errors",
                "--window-size=340,768",
                "--user-agent=" +
                        "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) " +
                        "AppleWebKit/420+ (KHTML, like Gecko) " +
                        "Version/3.0 Mobile/1A543a Safari/419.3 (TestAutomation) (BBCOMTestAutomation)"
        ));

        capabilities.setCapability(ChromeOptions.CAPABILITY, browserOptionsDictionary);

        Proxy noProxy = new Proxy();
        noProxy.setProxyType(Proxy.ProxyType.DIRECT);
        capabilities.setCapability(CapabilityType.PROXY, noProxy);

        return capabilities;
    }
}