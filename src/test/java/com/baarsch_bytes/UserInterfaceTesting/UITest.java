package com.baarsch_bytes.UserInterfaceTesting;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITest {

    // This is necessary to load up the web page and access the elements.
    private WebDriver driver;

    @BeforeEach
    void setUp() {

        // Ensure chromedriver is in the system path
        driver = new ChromeDriver();

        // If you want to test the HTML page directly without having to load it
        // onto a web server, use this resource.  Maven will find the .html
        // file in the target/classes/UserInterfaceTesting folder.
        // Loading the Resource from the UITest class
        // FYI:  The two roots seem to be target/test-classes and target/classes
        // if the generic ClasspathLoader is used, it will not find the target/
        // classes directory, but only the target/test-classes directory.
        // However, since the .HTML file represents "Production code," we don't
        // want it in the test-resources directory.
        URL resource = UITest.class.getResource("/UserInterfaceTesting/FuelChecker.html");
        if (resource != null) {
            driver.get(resource.toExternalForm());
        } else {
            throw new RuntimeException("Could not find the HTML file");
        }

        // if this resource were being hosted by the web server--simply use this
        //driver.get("http://localhost:8080/fuel-checker.html");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("S1A1: Low-volatility fuel fits (1200L)")
    void testLowVolatilityFits() {
        Select typeDropdown = new Select(driver.findElement(By.id("fuel-type")));
        typeDropdown.selectByValue("low");

        driver.findElement(By.id("fuel-amount")).sendKeys("1200");
        driver.findElement(By.id("check-button")).click();

        WebElement result = driver.findElement(By.id("result"));
        assertTrue(result.getText().contains("ACCEPT"));
        assertEquals("success", result.getAttribute("class"));
    }

    @Test
    @Order(2)
    @DisplayName("S1A2: High-volatility fuel fits (800L)")
    void testHighVolatilityFits() {
        Select typeDropdown = new Select(driver.findElement(By.id("fuel-type")));
        typeDropdown.selectByValue("high");

        driver.findElement(By.id("fuel-amount")).sendKeys("800");
        driver.findElement(By.id("check-button")).click();

        WebElement result = driver.findElement(By.id("result"));
        assertTrue(result.getText().contains("ACCEPT"));
    }

    @Test
    @Order(3)
    @DisplayName("S1A3: Low-volatility fuel doesn't fit (1201L)")
    void testLowVolatilityOverLimit() {
        new Select(driver.findElement(By.id("fuel-type"))).selectByValue("low");
        driver.findElement(By.id("fuel-amount")).sendKeys("1201");
        driver.findElement(By.id("check-button")).click();

        WebElement result = driver.findElement(By.id("result"));
        assertTrue(result.getText().contains("REJECT"));
        assertEquals("failure", result.getAttribute("class"));
    }

    @Test
    @Order(4)
    @DisplayName("S1A7: Identify user input data error (Negative values)")
    void testInputError() {
        new Select(driver.findElement(By.id("fuel-type"))).selectByValue("high");
        driver.findElement(By.id("fuel-amount")).sendKeys("-50");
        driver.findElement(By.id("check-button")).click();

        WebElement result = driver.findElement(By.id("result"));
        assertTrue(result.getText().contains("Error"));
        assertEquals("failure", result.getAttribute("class"));
    }

    @Test
    @Order(5)
    @DisplayName("S1A6: Exit application")
    void testExitApp() {
        driver.findElement(By.id("exit-button")).click();
        WebElement result = driver.findElement(By.id("result"));
        assertTrue(result.getText().contains("Session ended"));

        // Verify the button is disabled
        assertFalse(driver.findElement(By.id("check-button")).isEnabled());
    }

}
