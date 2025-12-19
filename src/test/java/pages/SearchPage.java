package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int STEP_SLEEP_MS = 1000;

    private final By searchLink =
            By.cssSelector("a[data-qa-id='header-search-text-link']");

    private final By searchInput =
            By.cssSelector("input[id='search-home-form-combo-input']");

    // 🔥 Click’i engelleyen overlay/backdrop
    private final By backdrop =
            By.cssSelector("div.zds-backdrop[data-hidden='false']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openSearch() {
        WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(searchLink));
        sleepStep();

        // 🔥 Backdrop kapansın
        waitForBackdropToDisappear();
        sleepStep();

        // 🔥 Normal click -> olmazsa JS click
        safeClick(search);
        sleepStep();

        // Search açıldı mı
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("search"),
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        ));
        sleepStep();
    }

    public void writeText(String text) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        sleepStep();
        input.click();
        sleepStep();
        input.sendKeys(text);
        sleepStep();
    }

    public void clearText() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        sleepStep();
        input.sendKeys(Keys.CONTROL + "a");
        sleepStep();
        input.sendKeys(Keys.DELETE);
        sleepStep();
    }

    public void pressEnter() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        sleepStep();
        input.sendKeys(Keys.ENTER);
        sleepStep();
    }

    /* ----------------- HELPERS ----------------- */

    private void safeClick(WebElement el) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el));
            el.click();
        } catch (WebDriverException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private void waitForBackdropToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(backdrop));
        } catch (TimeoutException ignored) {
            // Bazı anlarda backdrop locator kaçabilir; yine de JS click fallback var
        }
    }

    private void sleepStep() {
        try {
            Thread.sleep(STEP_SLEEP_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}
