package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.*;
import utils.ExcelUtils;
import utils.TxtUtils;

public class ZaraWebTest extends BaseTest {

    @Test
    void zaraShoppingScenarioTest() throws InterruptedException {

        // 0) Siteye git
        driver.get("https://www.zara.com/tr/");
        String excelPath = "src/test/resources/testdata/searchData.xlsx";

        HomePage home = new HomePage(driver);
        home.acceptCookiesIfPresent();

        // 1) Menü -> Erkek -> Tümünü Gör
        MenuPage menuPage = new MenuPage(driver);
        menuPage.openMenu();
        menuPage.openMenCategory();
        menuPage.clickViewAll();

        // 2) Arama sayfası
        SearchPage searchPage = new SearchPage(driver);
        searchPage.openSearch();

        // 3) Excel’den kelimeleri oku (A1 ve B1)
        String shortWord = ExcelUtils.getCellData(excelPath, 0, 0); // A1: şort
        String shirtWord = ExcelUtils.getCellData(excelPath, 0, 1); // B1: gömlek

        // 4) "şort" yaz -> sil
        searchPage.writeText(shortWord);
        Thread.sleep(1500);

        searchPage.clearText();
        Thread.sleep(1000);

        // 5) "gömlek" yaz -> Enter
        searchPage.writeText(shirtWord);
        searchPage.pressEnter();

        // 6) Sonuçlardan rastgele ürün seç
        SearchResultPage resultPage = new SearchResultPage(driver);
        resultPage.selectRandomProductFromResults();

        Thread.sleep(2000); // gözle görmek için (istersen kaldır)

        // 7) Ürün detay: isim + fiyat al, txt yaz
        ProductDetailPage productPage = new ProductDetailPage(driver);

        String productNameText = productPage.getProductName();
        String productPriceText = productPage.getProductPrice(); // ✅ tek fiyat değişkeni bu

        System.out.println("📌 Ürün sayfası fiyatı: " + productPriceText);
        TxtUtils.writeProductInfo(productNameText, productPriceText);

        // 8) Sepete ekle (beden seçerek) ve popup’tan sepete git
        productPage.addToCartSelectingRandomSize();
        productPage.goToCartFromAddToCartPopup();

        // 9) Sepet: birim fiyat al, ürün fiyatıyla karşılaştır
        CartPage cartPage = new CartPage(driver);

        String cartUnitPriceText = cartPage.getCartItemUnitPrice();

        Assertions.assertEquals(
                CartPage.normalizePrice(productPriceText),
                CartPage.normalizePrice(cartUnitPriceText),
                "Ürün fiyatı ile sepetteki birim fiyat eşleşmiyor!"
        );

        System.out.println("✅ Fiyat karşılaştırıldı: Ürün sayfası (" + productPriceText +
                ") == Sepet (" + cartUnitPriceText + ")");

        // 10) Adet artır -> 2 doğrula
        cartPage.increaseQuantityTo2();

        // 11) Ürünü tamamen sil (2 kere -) -> sepet boş doğrula
        cartPage.removeItemCompletely();
    }
}
