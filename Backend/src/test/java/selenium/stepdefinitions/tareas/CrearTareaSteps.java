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

public class CrearTareaSteps {

    Logger log = Logger.getLogger(CrearTareaSteps.class);
    WebDriver driver;

    public CrearTareaSteps() {
        driver = Hooks.driver;
    }

    @When("^El usuario ingresa un título válido y una fecha futura en el formulario de creación$")
    public void elUsuarioIngresaTituloYFechaFutura() {
        WebElement nuevaTareaButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nuevaTareaBtn"))
        );
        nuevaTareaButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("task-form.html")
        );

        WebElement tituloInput = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("titulo"))
        );
        WebElement fechaInput = driver.findElement(By.id("fechaTerminal"));

        tituloInput.sendKeys("Nueva tarea de prueba creada");

        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaInput.sendKeys(fechaFutura.format(formatter));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        log.info("Título y fecha futura ingresados.");
    }

    @When("^El usuario deja el título en blanco y asigna una fecha pasada en el formulario de creación$")
    public void elUsuarioDejaTituloEnBlancoYFechaPasada() {
        WebElement nuevaTareaButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nuevaTareaBtn"))
        );
        nuevaTareaButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("task-form.html")
        );

        WebElement tituloInput = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("titulo"))
        );
        WebElement fechaInput = driver.findElement(By.id("fechaTerminal"));

        tituloInput.clear();

        LocalDateTime fechaPasada = LocalDateTime.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaInput.sendKeys(fechaPasada.format(formatter));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        log.info("Título en blanco y fecha pasada ingresados.");
    }
}
