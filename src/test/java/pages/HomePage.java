package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int STEP_SLEEP_MS = 1000;

    // Cookies kabul butonu
    private final By acceptCookiesBtn = By.id("onetrust-accept-btn-handler");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void acceptCookiesIfPresent() {
        try {
            sleepStep(); // sayfa açılışını "insan gibi" bekle

            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn))
                    .click();

            sleepStep(); // cookie kapandıktan sonra bekle
        } catch (Exception ignored) {
            // Cookie çıkmayabilir, sorun değil
        }
    }

    /* ----------------- UTIL ----------------- */

    private void sleepStep() {
        try {
            Thread.sleep(STEP_SLEEP_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}
