package selenium.stepdefinitions.autenticacion;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.functions.SeleniumFunctions;
import selenium.stepdefinitions.Hooks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RegistroSteps {

    private static Properties prop = new Properties();
    private static InputStream in = RegistroSteps.class.getResourceAsStream("/test.properties");

    Logger log = Logger.getLogger(RegistroSteps.class);
    WebDriver driver;

    public RegistroSteps() {
        driver = Hooks.driver;
    }

    @Given("^El usuario está en la página de registro$")
    public void elUsuarioEstaEnLaPaginaDeRegistro() throws IOException {
        prop.load(in);
        String url = prop.getProperty("MainAppUrlBase");
        log.info("Navegar a: " + url + "/signup.html");
        driver.get(url + "/signup.html");
    }

    @When("^El usuario introduce un nombre de usuario válido, un correo electrónico válido y una contraseña válida$")
    public void elUsuarioIntroduceDatosValidos() throws IOException {
        SeleniumFunctions.FileName = "datos_registro_usuario.json";
        JsonNode registrationData = SeleniumFunctions.readEntity("validUser");

        String username = registrationData.get("username").asText();
        String email = registrationData.get("email").asText();
        String password = registrationData.get("password").asText();

        log.info("Rellenando formulario con datos: Usuario=" + username + ", Email=" + email);

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys(username);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);

        WebElement privacyCheckbox = driver.findElement(By.id("privacy"));
        if (!privacyCheckbox.isSelected()) {
            privacyCheckbox.click();
        }
    }

    @When("^El usuario introduce un nombre de usuario vacío, un correo electrónico vacío y una contraseña inválida$")
    public void elUsuarioIntroduceDatosInvalidos() throws IOException {
        SeleniumFunctions.FileName = "datos_registro_usuario.json";
        JsonNode registrationData = SeleniumFunctions.readEntity("invalidUser");

        String username = registrationData.get("username").asText();
        String email = registrationData.get("email").asText();
        String password = registrationData.get("password").asText();

        log.info("Rellenando formulario con datos: Usuario=" + username + ", Email=" + email);

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys(username);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);

        WebElement privacyCheckbox = driver.findElement(By.id("privacy"));
        if (!privacyCheckbox.isSelected()) {
            privacyCheckbox.click();
        }
    }

    @When("^Hace clic en el botón \"Registrar\"$")
    public void haceClicEnRegistrar() {
        WebElement registerButton = driver.findElement(By.cssSelector(".card__submit-btn"));
        log.info("Haciendo clic en el botón de registrar");
        registerButton.click();
    }

    @Then("^El registro se completa exitosamente$")
    public void elRegistroSeCompletaExitosamente() {
        WebElement notificationContainer = driver.findElement(By.id("notification-container"));
        log.info("Verificando que el registro fue exitoso");
        assert notificationContainer.isDisplayed() : "El mensaje de éxito no se muestra";

        String notificationText = notificationContainer.getText();
        assert notificationText.contains("Registro exitoso") : "El mensaje no indica éxito en el registro";
    }

    @Then("^El registro no se completa$")
    public void elRegistroNoSeCompleta() {
        WebElement notificationContainer = driver.findElement(By.id("notification-container"));
        log.info("Verificando que no se haya completado el registro");

        assert !notificationContainer.isDisplayed() : "El mensaje de éxito no debe mostrarse";
    }

    @Then("^El usuario permanece en la página de registro$")
    public void elUsuarioPermaneceEnLaPaginaDeRegistro() {
        String currentUrl = driver.getCurrentUrl();
        log.info("Verificando que el usuario sigue en la página de registro");

        assert currentUrl.contains("/signup.html") : "El usuario no está en la página de registro";
    }
}
