package selenium.stepdefinitions.tareas;

import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.stepdefinitions.Hooks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditarTareaSteps {

    Logger log = Logger.getLogger(EditarTareaSteps.class);
    WebDriver driver;

    public EditarTareaSteps() {
        driver = Hooks.driver;
    }

    @When("^El usuario modifica el título, agrega una descripción, actualiza la fecha a futura y cambia el estado a \"([^\"]*)\"$")
    public void elUsuarioModificaLaTarea(String estado) {
        accederAFormularioDeEdicion();

        WebElement tituloInput = driver.findElement(By.id("titulo"));
        WebElement descripcionInput = driver.findElement(By.id("descripcion"));
        WebElement fechaInput = driver.findElement(By.id("fechaTerminal"));
        WebElement estadoSelect = driver.findElement(By.id("estado"));

        tituloInput.clear();
        tituloInput.sendKeys("Nueva tarea de prueba actualizada");
        descripcionInput.clear();
        descripcionInput.sendKeys("Esta es una descripción actualizada de la tarea.");

        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaInput.sendKeys(fechaFutura.format(formatter));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        estadoSelect.sendKeys(estado);

        log.info("Título, descripción, fecha y estado modificados.");
    }

    @When("^El usuario borra el título y asigna una fecha pasada$")
    public void elUsuarioBorraTituloYFechaPasada() {
        accederAFormularioDeEdicion();

        WebElement tituloInput = driver.findElement(By.id("titulo"));
        WebElement fechaInput = driver.findElement(By.id("fechaTerminal"));

        tituloInput.clear();

        LocalDateTime fechaPasada = LocalDateTime.now().minusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaInput.sendKeys(fechaPasada.format(formatter));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        log.info("Título borrado y fecha pasada asignada.");
    }


    private void accederAFormularioDeEdicion() {
        WebElement tareaExistente = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//tr[td[contains(text(), 'Nueva tarea de prueba creada')]]" +
                                "//button[@id='editarTareaBtn']"))
        );
        tareaExistente.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("task-form.html")
        );

        log.info("Accedió al formulario de edición de la tarea.");
    }
}
