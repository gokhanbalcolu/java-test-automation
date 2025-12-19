package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchResultPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int STEP_SLEEP_MS = 2000;
    private static final int SCROLL_SLEEP_MS = 1500;

    // 🔴 SADECE arama sonuçlarındaki ürün görselleri
    private final By productImages =
            By.cssSelector("img[data-qa-qualifier='media-image']");

    // Ürün detay sayfası kontrolü
    private final By productNameOnDetail =
            By.cssSelector("h1[data-qa-qualifier='product-detail-info-name']");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void selectRandomProductFromResults() {

        // 1️⃣ Arama sonuçlarının yüklenmesini bekle
        wait.until(ExpectedConditions.presenceOfElementLocated(productImages));
        sleepStep();

        // 2️⃣ Lazy load tetiklemek için scroll
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0, document.body.scrollHeight / 2);"
        );
        sleepStep();

        // 3️⃣ Ürün görsellerini topla
        List<WebElement> images = driver.findElements(productImages);
        if (images.isEmpty()) {
            throw new RuntimeException("❌ Arama sonuçlarında ürün bulunamadı!");
        }

        // 4️⃣ Rastgele ürün seç
        WebElement selectedImage =
                images.get(new Random().nextInt(images.size()));

        // 5️⃣ Seçilen ürünü merkeze al
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", selectedImage
        );
        sleepStep();

        // 6️⃣ img yerine parent link/card elementini bul
        WebElement productCard = selectedImage.findElement(
                By.xpath("./ancestor::a | ./ancestor::div[@role='link']")
        );

        // 7️⃣ JS click (overlay/intercept riskine karşı)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", productCard);

        // 8️⃣ Ürün detay sayfası açıldı mı doğrula
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameOnDetail));
        sleepStep();
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
