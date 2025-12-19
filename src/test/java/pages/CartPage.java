package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // 1️⃣ Sepette ürün birim fiyatı
    private final By cartItemUnitPrice =
            By.cssSelector(".shop-cart-item-pricing__current .money-amount__main");

    // 2️⃣ Adet artır (+)
    private final By increaseButton =
            By.cssSelector("div[data-qa-id='add-order-item-unit']");

    // 3️⃣ Adet input
    private final By quantityInput =
            By.cssSelector("input.shop-cart-item-quantity");

    // 4️⃣ Adet azalt (−)
    private final By decreaseButton =
            By.cssSelector("div[data-qa-id='remove-order-item-unit']");

    // 5️⃣ Sepet boş mesajı
    private final By emptyCartText =
            By.cssSelector(".zds-empty-state__title span");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // Sepetteki ürün birim fiyatı
    public String getCartItemUnitPrice() {
        WebElement priceEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartItemUnitPrice)
        );
        String price = priceEl.getText().trim();
        System.out.println("🧾 CART UNIT PRICE: " + price);
        return price;
    }

    // Adedi 2 yap ve doğrula
    public void increaseQuantityTo2() {

        WebElement plus = wait.until(ExpectedConditions.presenceOfElementLocated(increaseButton));

        // footer intercept etmesin diye görünür alana getir
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", plus);

        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // normal click -> olmazsa JS click
        try {
            wait.until(ExpectedConditions.elementToBeClickable(plus));
            plus.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", plus);
        } catch (ElementNotInteractableException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", plus);
        }

        // 2 olduğunu doğrula
        wait.until(d -> "2".equals(d.findElement(quantityInput).getAttribute("value")));

        System.out.println("➕ Adet 2 doğrulandı");
    }

    // Ürünü tamamen sil (2 defa -)
    public void removeItemCompletely() {

        WebElement minus = wait.until(ExpectedConditions.elementToBeClickable(decreaseButton));
        minus.click(); // 2 → 1
        wait.until(d -> "1".equals(d.findElement(quantityInput).getAttribute("value")));

        minus = wait.until(ExpectedConditions.elementToBeClickable(decreaseButton));
        minus.click(); // 1 → 0 (silinir)

        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartText));

        System.out.println("🗑 Ürün silindi, sepet boş");
    }

    public static String normalizePrice(String priceText) {
        if (priceText == null) return "";

        return priceText
                .replace("\u00A0", " ")
                .replace("TL", "")
                .replace(".", "")       // 2.490,00 -> 2490,00
                .replace(",", ".")      // 2490,00 -> 2490.00
                .replaceAll("[^0-9.]", "")
                .trim();
    }

}
