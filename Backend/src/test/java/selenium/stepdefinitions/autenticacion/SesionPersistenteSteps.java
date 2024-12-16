package selenium.stepdefinitions.autenticacion;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.stepdefinitions.Hooks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SesionPersistenteSteps {

    Logger log = Logger.getLogger(SesionPersistenteSteps.class);
    WebDriver driver;

    public SesionPersistenteSteps() {
        driver = Hooks.driver;
    }

    @When("^El usuario recarga la página del dashboard$")
    public void elUsuarioRecargaLaPaginaDelDashboard() {
        log.info("Recargando la página del dashboard...");
        driver.navigate().refresh();
    }

    @Then("^El usuario sigue autenticado y ve el contenido del dashboard$")
    public void elUsuarioSigueAutenticadoYVeElContenidoDelDashboard() {
        String token = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('authToken');");

        assertTrue(token != null && !token.isEmpty(),
                "El token de autenticación debería persistir después de recargar la página");
        log.info("El token sigue presente en el localStorage.");

        WebElement taskList = driver.findElement(By.id("tareasBody"));
        assertTrue(taskList.isDisplayed(),
                "El usuario no ve el contenido del dashboard después de recargar");
        log.info("El contenido del dashboard se ha cargado correctamente.");
    }
}
