package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Cookies kabul butonu
    By acceptCookiesBtn = By.id("onetrust-accept-btn-handler");

    public void acceptCookiesIfPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn)).click();
        } catch (Exception e) {
            // Cookies çıkmazsa sorun yok
        }
    }
}
