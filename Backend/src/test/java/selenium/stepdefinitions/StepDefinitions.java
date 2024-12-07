package selenium.stepdefinitions;

import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import selenium.functions.CreateDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StepDefinitions {

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../../test.properties");

    Logger log = Logger.getLogger(StepDefinitions.class);
    WebDriver driver;

    public StepDefinitions() {
        driver = Hooks.driver;
    }

    @Given("^El cliente se encuentra en la pantalla principal$")
    public void elclienteSeEncuentraEnLaPantallaPrincipal() throws IOException {
        prop.load(in);
        String url = prop.getProperty("MainAppUrlBase");
        log.info(("Navegar a: " + url));
        driver.get(url);
    }
}
