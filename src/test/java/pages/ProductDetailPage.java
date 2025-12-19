package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ProductDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /* ===================== LOCATORS ===================== */

    // Ürün adı
    private final By productName =
            By.cssSelector("h1[data-qa-qualifier='product-detail-info-name']");

    // Ürün fiyatı
    private final By productPrice =
            By.xpath("//span[@data-qa-id='price-container-current']//*[contains(text(),'TL')]");

    // Sepete ekle butonu
    private final By addToCartButton =
            By.cssSelector("button[data-qa-action='add-to-cart']");

    // Beden listesi root
    private final By sizeListRoot =
            By.cssSelector("ul.size-selector-sizes");

    // Stokta olan beden butonları
    private final By inStockSizeButtons =
            By.cssSelector("ul.size-selector-sizes button[data-qa-action='size-in-stock']");

    /* ===================== CONSTRUCTOR ===================== */

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /* ===================== GETTERS ===================== */

    public String getProductName() {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        String name = nameEl.getText().trim();
        System.out.println("✅ PRODUCT NAME: " + name);
        return name;
    }

    public String getProductPrice() {
        WebElement priceElement = wait.until(d -> d.findElement(productPrice));
        String price = priceElement.getAttribute("innerText").trim();
        System.out.println("💰 PRODUCT PRICE: " + price);
        return price;
    }

    /* ===================== ACTIONS ===================== */

    // 1) Sepete ekleye tıkla (beden listesi açılır)
    public void clickAddToCart() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        sleep(800);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(btn));
            btn.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (ElementNotInteractableException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        // beden listesi gelsin
        wait.until(ExpectedConditions.presenceOfElementLocated(sizeListRoot));
    }

    // 2) Açılan listeden random beden seç
    public void selectRandomInStockSize() {

        List<WebElement> sizes = wait.until(d -> {
            List<WebElement> els = d.findElements(inStockSizeButtons);
            els.removeIf(e -> !e.isDisplayed());
            return els.isEmpty() ? null : els;
        });

        WebElement choice = sizes.get(new Random().nextInt(sizes.size()));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", choice);

        sleep(500);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(choice));
            choice.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", choice);
        } catch (ElementNotInteractableException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", choice);
        }

        System.out.println("✅ Random beden seçildi");

    }

    // 3) Tek metotta: Sepete ekle -> beden seç -> gerekiyorsa tekrar ekle
    public void addToCartSelectingRandomSize() {

        clickAddToCart();
        selectRandomInStockSize();

        // Bazı ürünlerde beden seçince otomatik ekler, bazılarında yeniden "Ekle" ister
        // Kırmadan deniyoruz, fail olursa sepette doğrularız
        sleep(500);

        try {
            WebElement btn2 = driver.findElement(addToCartButton);
            if (btn2.isDisplayed() && btn2.isEnabled()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn2);
                sleep(400);
                btn2.click();
                System.out.println("🛒 İkinci tıklama ile sepete eklendi");
            }
        } catch (NoSuchElementException ignored) {
        } catch (WebDriverException ignored) {
        }
    }

    public void goToCartFromAddToCartPopup() {

        By goToCartFromPopup =
                By.cssSelector("button[data-qa-action='nav-to-cart']");

        WebElement goToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(goToCartFromPopup)
        );

        goToCartBtn.click();

        System.out.println("🛒 Popup üzerinden sepete gidildi");
    }


    /* ===================== HELPERS ===================== */

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
