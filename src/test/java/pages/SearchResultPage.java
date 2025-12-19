package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchResultPage {

    WebDriver driver;
    WebDriverWait wait;

    // 🔴 SADECE arama sonuçlarındaki ürün görselleri
    private By productImages =
            By.cssSelector("img[data-qa-qualifier='media-image']");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void selectRandomProductFromResults() {

        // 1️⃣ Arama sonuçları gelsin
        wait.until(ExpectedConditions.presenceOfElementLocated(productImages));

        // 2️⃣ Lazy load için scroll
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0, document.body.scrollHeight / 2);"
        );

        sleep(2000);

        List<WebElement> images = driver.findElements(productImages);

        if (images.size() == 0) {
            throw new RuntimeException("No product images found in search results!");
        }

        // 3️⃣ Güvenli random
        int randomIndex = new Random().nextInt(images.size());
        WebElement selectedImage = images.get(randomIndex);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", selectedImage
        );

        sleep(1500);

        // 🔥 EN KRİTİK NOKTA
        // img yerine PARENT LINK'e çıkıyoruz
        WebElement productCard =
                selectedImage.findElement(By.xpath("./ancestor::a | ./ancestor::div[@role='link']"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", productCard);

        // Ürün detay sayfasının gerçekten açıldığını doğrula
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h1[data-qa-qualifier='product-detail-info-name']")
        ));

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
