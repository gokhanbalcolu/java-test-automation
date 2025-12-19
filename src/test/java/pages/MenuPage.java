package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MenuPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int STEP_SLEEP_MS = 1000;

    private final By menuButton =
            By.cssSelector("button[data-qa-id='layout-header-toggle-menu']");

    private final By menMenu = By.xpath(
            "//span[contains(@class,'layout-categories-category-name') and normalize-space()='ERKEK']"
    );

    private final By viewAllButton = By.xpath(
            "//span[contains(@class,'layout-categories-category-name') and normalize-space()='TÜMÜNÜ GÖR']"
    );

    public MenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /* ===================== ACTIONS ===================== */

    public void openMenu() {
        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(menuButton)
        );

        menu.click();
        sleepStep();
    }

    public void openMenCategory() {
        WebElement men = wait.until(
                ExpectedConditions.visibilityOfElementLocated(menMenu)
        );

        men.click();
        sleepStep();
    }

    public void clickViewAll() {
        WebElement viewAll = wait.until(
                ExpectedConditions.elementToBeClickable(viewAllButton)
        );

        viewAll.click();
        sleepStep();
    }

    /* ===================== UTIL ===================== */

    private void sleepStep() {
        try {
            Thread.sleep(STEP_SLEEP_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}
