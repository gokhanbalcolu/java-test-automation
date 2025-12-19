package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MenuPage {

    WebDriver driver;
    WebDriverWait wait;

    private By menuButton =
            By.cssSelector("button[data-qa-id='layout-header-toggle-menu']");

    private By menMenu = By.xpath(
            "//span[contains(@class,'layout-categories-category-name') and normalize-space()='ERKEK']"
    );

    private By viewAllButton = By.xpath(
            "//span[contains(@class,'layout-categories-category-name') and normalize-space()='TÜMÜNÜ GÖR']"
    );

    public MenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openMenu() {
        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(menuButton)
        );
        menu.click();
    }

    public void openMenCategory() {
        WebElement men = wait.until(
                ExpectedConditions.visibilityOfElementLocated(menMenu)
        );
        men.click();
    }

    public void clickViewAll() {
        WebElement viewAll = wait.until(
                ExpectedConditions.elementToBeClickable(viewAllButton)
        );
        viewAll.click();

        try {
            Thread.sleep(5000); // 5 saniye bekle
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
