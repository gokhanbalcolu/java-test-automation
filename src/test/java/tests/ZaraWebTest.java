package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.*;
import utils.ExcelUtils;
import utils.TxtUtils;

public class ZaraWebTest extends BaseTest {

    @Test
    void zaraShoppingScenarioTest() {

        // 0) Test başlangıcı: ana sayfayı aç
        driver.get("https://www.zara.com/tr/");

        HomePage homePage = new HomePage(driver);
        homePage.acceptCookiesIfPresent();

        // 1) Negatif login kontrolü (validasyon mesajı doğrulama)
        // Amaç: Login akışına gidilebildiğini ve invalid email uyarısının geldiğini doğrulamak
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

// 1) Hatalı format email -> field error doğrula
        String invalidEmailMsg = loginPage.verifyInvalidEmailFormat("abc");
        Assertions.assertTrue(
                invalidEmailMsg.toLowerCase().contains("geçersiz"),
                "Format hata mesajı bekleniyordu. Gelen: " + invalidEmailMsg
        );

// 2) Email'i temizle
        loginPage.clearEmail();

// 3) Format doğru ama dummy email + şifre -> popup title doğrula
        String popupTitle = loginPage.verifyInvalidCredentialsPopup(
                "dummy.user@example.com",
                "dummyPass123"
        );
        Assertions.assertTrue(
                popupTitle.toLowerCase().contains("maalesef"),
                "Popup başlığı beklenmiyordu. Gelen: " + popupTitle
        );

// 4) Ana sayfaya dön
        loginPage.clickLogoToReturnHome();

        // 2) Menü navigasyonu: Erkek kategorisi -> Tümünü Gör
        // Amaç: kategori sayfasına stabil şekilde erişmek
        MenuPage menuPage = new MenuPage(driver);
        menuPage.openMenu();
        menuPage.openMenCategory();
        menuPage.clickViewAll();

        // 3) Arama ekranı aç
        SearchPage searchPage = new SearchPage(driver);
        searchPage.openSearch();

        // 4) Test datası: Excel’den arama kelimelerini oku (A1, B1)
        String excelPath = "src/test/resources/testdata/searchData.xlsx";
        String shortWord = ExcelUtils.getCellData(excelPath, 0, 0); // A1
        String shirtWord = ExcelUtils.getCellData(excelPath, 0, 1); // B1

        // 5) Arama davranışı: önce yaz/sil, sonra gerçek kelimeyle ara
        // Amaç: input temizleme + arama tetikleme akışını doğrulamak
        searchPage.writeText(shortWord);

        searchPage.clearText();

        searchPage.writeText(shirtWord);
        searchPage.pressEnter();

        // 6) Sonuçlardan rastgele ürün seç
        SearchResultPage resultPage = new SearchResultPage(driver);
        resultPage.selectRandomProductFromResults();

        // 7) Ürün detay: isim + fiyatı al ve txt’ye yaz
        ProductDetailPage productPage = new ProductDetailPage(driver);
        String productNameText = productPage.getProductName();
        String productPriceText = productPage.getProductPrice();

        TxtUtils.writeProductInfo(productNameText, productPriceText);

        // 8) Sepete ekle (beden gerekiyorsa seç) ve sepete git
        productPage.addToCartSelectingRandomSize();
        productPage.goToCartFromAddToCartPopup();

        // 9) Sepet doğrulaması: ürün sayfası fiyatı == sepet birim fiyat
        CartPage cartPage = new CartPage(driver);
        String cartUnitPriceText = cartPage.getCartItemUnitPrice();

        Assertions.assertEquals(
                CartPage.normalizePrice(productPriceText),
                CartPage.normalizePrice(cartUnitPriceText),
                "Ürün fiyatı ile sepetteki birim fiyat eşleşmiyor!"
        );

        // 10) Adet artır ve 2 olduğunu doğrula (metodun içinde assert/verify varsa ayrıca gerek yok)
        cartPage.increaseQuantityTo2();

        // 11) Ürünü tamamen sil ve sepetin boş olduğunu doğrula
        cartPage.removeItemCompletely();
    }
}
