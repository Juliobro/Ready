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

public class LoginSteps {

    private static Properties prop = new Properties();
    private static InputStream in = RegistroSteps.class.getResourceAsStream("/test.properties");

    Logger log = Logger.getLogger(LoginSteps.class);
    WebDriver driver;

    public LoginSteps() {
        driver = Hooks.driver;
    }

    @Given("^El usuario está en la página de inicio de sesión$")
    public void elUsuarioEstaEnLaPaginaDeLogin() throws IOException{
        prop.load(in);
        String url = prop.getProperty("MainAppUrlBase");
        log.info("Navegar a: " + url + "/login.html");
        driver.get(url + "/login.html");
    }

    @When("^El usuario introduce un correo electrónico válido y una contraseña válida$")
    public void elUsuarioIntroduceCredencialesValidas() throws IOException {
        SeleniumFunctions.FileName = "datos_login_usuario.json";
        JsonNode loginData = SeleniumFunctions.readEntity("validUser");

        String email = loginData.get("email").asText();
        String password = loginData.get("password").asText();

        log.info("Introduciendo credenciales: Email=" + email + ", Contraseña=" + password);

        WebElement emailField = driver.findElement(By.id("login"));
        WebElement passwordField = driver.findElement(By.id("password"));

        emailField.sendKeys(email);
        passwordField.sendKeys(password);
    }

    @When("^El usuario introduce un correo electrónico inválido y una contraseña inválida$")
    public void elUsuarioIntroduceCredencialesInvalidas() throws IOException {
        SeleniumFunctions.FileName = "datos_login_usuario.json";
        JsonNode loginData = SeleniumFunctions.readEntity("invalidUser");

        String email = loginData.get("email").asText();
        String password = loginData.get("password").asText();

        log.info("Introduciendo credenciales inválidas: Email=" + email + ", Contraseña=" + password);

        WebElement emailField = driver.findElement(By.id("login"));
        WebElement passwordField = driver.findElement(By.id("password"));

        emailField.sendKeys(email);
        passwordField.sendKeys(password);
    }

    @When("^Hace clic en el botón \"Iniciar sesión\"$")
    public void haceClicEnIniciarSesion() {
        WebElement loginButton = driver.findElement(By.cssSelector(".card__submit-btn"));
        log.info("Haciendo clic en el botón de iniciar sesión");
        loginButton.click();
    }

    @Then("^El usuario sigue en la página de inicio de sesión$")
    public void elUsuarioSigueEnLaPaginaDeLogin() {
        String currentUrl = driver.getCurrentUrl();
        log.info("Verificando que el usuario sigue en la página de login");
        assert currentUrl.contains("/login.html") : "El usuario fue redirigido a una página incorrecta";
    }

    @Then("^El usuario ve el mensaje de error de \"Credenciales incorrectas\"$")
    public void elUsuarioVeMensajeDeError() {
        WebElement notificationContainer = driver.findElement(By.id("notification-container"));
        String notificationText = notificationContainer.getText();
        log.info("Verificando el mensaje de notificación: " + notificationText);
        assert notificationText.contains("Credenciales incorrectas") : "No se mostró el mensaje de error esperado";
    }
}
