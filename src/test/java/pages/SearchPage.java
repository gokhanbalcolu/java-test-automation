package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    private By searchLink =
            By.cssSelector("a[data-qa-id='header-search-text-link']");

    private By searchInput =
            By.cssSelector("input[id='search-home-form-combo-input']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openSearch() {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchLink)
        );
        search.click();
        wait.until(ExpectedConditions.urlContains("search"));
    }

    public void writeText(String text) {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(searchInput)
        );
        input.click();
        input.sendKeys(text);
    }

    public void clearText() {
        WebElement input = driver.findElement(searchInput);
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }

    public void pressEnter() {
        driver.findElement(searchInput).sendKeys(Keys.ENTER);
    }
}
