package selenium.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.stepdefinitions.autenticacion.LoginSteps;
import selenium.stepdefinitions.autenticacion.RegistroSteps;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class CommonSteps {

    private static Properties prop = new Properties();
    private static InputStream in = RegistroSteps.class.getResourceAsStream("/test.properties");

    static Logger log = Logger.getLogger(LoginSteps.class);
    static WebDriver driver;

    public CommonSteps() {
        driver = Hooks.driver;
    }

    @Given("^El usuario ha iniciado sesión correctamente$")
    public void elUsuarioHaIniciadoSesionCorrectamente() throws IOException {
        prop.load(in);
        String url = prop.getProperty("MainAppUrlBase");
        log.info("Navegar a: " + url + "/login.html");
        driver.get(url + "/login.html");

        WebElement loginField = driver.findElement(By.id("login"));
        WebElement passwordField = driver.findElement(By.id("password"));
        loginField.sendKeys("juliobro");
        passwordField.sendKeys("123abc");

        WebElement loginButton = driver.findElement(By.cssSelector(".card__submit-btn"));
        loginButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("/dashboard.html")
        );

        String token = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('authToken');");

        assertTrue(token != null && !token.isEmpty(),
                "El token de autenticación no está presente en el localStorage");

        log.info("Token de autenticación encontrado: " + token);
    }

    /* ------------------------------ Configuración de tareas ------------------------------ */
    @Given("^Existe una tarea en el dashboard$")
    public void existeUnaTareaEnElDashboard() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("/dashboard.html")
        );

        WebElement listaTareas = driver.findElement(By.id("tareasBody"));

        if (listaTareas.findElements(
                By.xpath("//*[contains(text(), 'Nueva tarea de prueba creada')]")).isEmpty()) {
            agregarTareaInicial();
        }

        log.info("Se verificó que existe una tarea en el dashboard.");
    }

    @When("^El usuario guarda la tarea$")
    public void elUsuarioGuardaLaTarea() {
        WebElement formulario = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(By.id("formTarea")));
        formulario.submit();
        log.info("La tarea fue guardada");
    }

    @Then("^La tarea \"([^\"]*)\" aparece en la lista de tareas$")
    public void laTareaConfiguradaApareceEnLaLista(String accion) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("/dashboard.html")
        );
        WebElement listaTareas = driver.findElement(By.id("tareasBody"));
        WebElement tareaConfigurada = listaTareas.findElement(
                By.xpath("//*[contains(text(), 'Nueva tarea de prueba " + accion + "')]"));

        assertTrue(tareaConfigurada.isDisplayed(), "La tarea " + accion + " no aparece en la lista de tareas");
        log.info("La tarea " + accion + " está visible en el dashboard.");
    }

    @Then("^El usuario es redirigido a su dashboard$")
    public void elUsuarioEsRedirigidoADashboard() {
        String currentUrl = driver.getCurrentUrl();
        log.info("Verificando redirección al dashboard");
        assert currentUrl.contains("/dashboard.html") : "El usuario no fue redirigido al dashboard";
    }

    @Then("^El usuario permanece en la página de creación de tareas$")
    public void elUsuarioPermaneceEnTaskForm() {
        try {
            Thread.sleep(2000);
            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.contains("task-form.html")) {
                log.info("El usuario permanece en task-form.html debido a datos inválidos.");
            } else if (currentUrl.contains("dashboard.html")) {
                fail("El usuario fue redirigido al dashboard, lo cual no debería suceder con datos inválidos.");
            } else {
                fail("El usuario fue redirigido a una página inesperada: " + currentUrl);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("La espera fue interrumpida.");
        }
    }


    private static void agregarTareaInicial() {
        WebElement nuevaTareaButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nuevaTareaBtn"))
        );
        nuevaTareaButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("task-form.html")
        );

        WebElement tituloInput = driver.findElement(By.id("titulo"));
        WebElement descripcionInput = driver.findElement(By.id("descripcion"));
        WebElement fechaInput = driver.findElement(By.id("fechaTerminal"));

        tituloInput.sendKeys("Nueva tarea de prueba creada");
        descripcionInput.sendKeys("Descripción de tarea inicial.");

        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaInput.sendKeys(fechaFutura.format(formatter));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        WebElement formulario = driver.findElement(By.id("formTarea"));
        formulario.submit();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.urlContains("/dashboard.html")
        );

        log.info("Se agregó una tarea inicial en el dashboard.");
    }
}
