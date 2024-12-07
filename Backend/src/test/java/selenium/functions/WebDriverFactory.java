package selenium.functions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebDriverFactory {

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../../test.properties");
    private static String resourceFolder;
    private static Logger log = Logger.getLogger(WebDriverFactory.class);

    private static WebDriverFactory instance = null;

    private WebDriverFactory() {}

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            instance = new WebDriverFactory();
        }
        return instance;
    }

    public static WebDriver createNewWebDriver(String browser, String os) throws IOException {
        WebDriver driver;
        prop.load(in);
        resourceFolder = prop.getProperty("resourceFolder");

        if ("CHROME".equalsIgnoreCase(browser)) {
            if ("WINDOWS".equalsIgnoreCase(os)) {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver.exe");
            } else {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver");
            }
            driver = new ChromeDriver();

        } else {
            log.error("El Driver no ha sido seleccionado apropiadamente, nombre inválido: " + browser + ", " + os);
            return null;
        }

        driver.manage().window().maximize();
        return driver;
    }
}
