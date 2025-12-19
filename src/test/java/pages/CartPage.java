package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int STEP_SLEEP_MS = 1000;

    // 1) Sepette ürün birim fiyatı
    private final By cartItemUnitPrice =
            By.cssSelector(".shop-cart-item-pricing__current .money-amount__main");

    // 2) Adet artır (+)
    private final By increaseButton =
            By.cssSelector("div[data-qa-id='add-order-item-unit']");

    // 3) Adet input
    private final By quantityInput =
            By.cssSelector("input.shop-cart-item-quantity");

    // 4) Adet azalt (−)
    private final By decreaseButton =
            By.cssSelector("div[data-qa-id='remove-order-item-unit']");

    // 5) Sepet boş mesajı
    private final By emptyCartText =
            By.cssSelector(".zds-empty-state__title span");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public String getCartItemUnitPrice() {
        WebElement priceEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartItemUnitPrice)
        );
        sleepStep();

        String price = priceEl.getText().trim();
        System.out.println("🧾 CART UNIT PRICE: " + price);
        return price;
    }

    // Adedi 2 yap ve doğrula
    public void increaseQuantityTo2() {

        WebElement plus = wait.until(ExpectedConditions.presenceOfElementLocated(increaseButton));
        scrollToCenter(plus);
        sleepStep();

        safeClick(plus);
        sleepStep();

        wait.until(d -> "2".equals(d.findElement(quantityInput).getAttribute("value")));
        sleepStep();

        System.out.println("➕ Adet 2 doğrulandı");
    }

    // Ürünü tamamen sil (2 defa -)
    public void removeItemCompletely() {

        WebElement minus = wait.until(ExpectedConditions.presenceOfElementLocated(decreaseButton));
        scrollToCenter(minus);
        sleepStep();

        // 2 -> 1
        safeClick(minus);
        sleepStep();
        wait.until(d -> "1".equals(d.findElement(quantityInput).getAttribute("value")));
        sleepStep();

        // 1 -> 0 (silinir)
        minus = wait.until(ExpectedConditions.presenceOfElementLocated(decreaseButton));
        scrollToCenter(minus);
        sleepStep();

        safeClick(minus);
        sleepStep();

        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartText));
        sleepStep();

        System.out.println("🗑 Ürün silindi, sepet boş");
    }

    public static String normalizePrice(String priceText) {
        if (priceText == null) return "";

        return priceText
                .replace("\u00A0", " ")
                .replace("TL", "")
                .replace(".", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "")
                .trim();
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

    private void scrollToCenter(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el
        );
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
