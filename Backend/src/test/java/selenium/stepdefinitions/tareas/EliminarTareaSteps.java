package selenium.stepdefinitions.tareas;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.stepdefinitions.Hooks;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EliminarTareaSteps {

    Logger log = Logger.getLogger(EliminarTareaSteps.class);
    WebDriver driver;

    public EliminarTareaSteps() {
        driver = Hooks.driver;
    }

    @When("^El usuario elimina una tarea$")
    public void elUsuarioEliminaUnaTarea() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("/dashboard.html")
        );

        WebElement tareaEliminable = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//tr[td[contains(text(), 'Nueva tarea de prueba creada')]]" +
                                "//button[@id='eliminarTareaBtn']"))
        );
        tareaEliminable.click();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            log.info("Alert de confirmación de eliminación aceptado.");
        } catch (Exception e) {
            log.warn("No se encontró alert para confirmar eliminación: " + e.getMessage());
        }

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("notification-container"))
        );

        log.info("Tarea eliminada correctamente.");
    }


    @Then("^La tarea eliminada ya no aparece en la lista de tareas$")
    public void laTareaYaNoApareceEnLaLista() {
        WebElement listaTareas = driver.findElement(By.id("tareasBody"));
        boolean tareaEliminada = listaTareas.findElements(
                By.xpath("//*[contains(text(), 'Nueva tarea de prueba creada')]")).isEmpty();

        assertTrue(tareaEliminada, "La tarea no fue eliminada correctamente.");
        log.info("La tarea ya no aparece en el dashboard.");
    }
}
