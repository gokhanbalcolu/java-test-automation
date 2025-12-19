# Zara Test Otomasyon Projesi (Web + Trello API)

Bu proje, bir otomasyon ödevi kapsamında **Zara web senaryosu** (Selenium + JUnit) ve **Trello REST API senaryosu** (Rest-Assured) içerir.

## Kullanılan Teknolojiler
- Java 17
- Maven
- Selenium WebDriver
- JUnit 5
- WebDriverManager
- Apache POI (Excel okuma)
- Rest-Assured (API testleri)
- Log4j / SLF4J

## Proje Yapısı
- Page Object Model (POM) tasarım deseni kullanılmıştır.
- Web ve API testleri ayrı sınıflar halinde geliştirilmiştir.
- Test verisi Excel üzerinden okunur, ürün bilgisi TXT dosyasına yazılır.

## Web Otomasyon Senaryosu (Zara)
1. Zara Türkiye sitesi açılır
2. Cookie pop-up kabul edilir
3. **Login Page** oluşturulmuştur ve aşağıdaki adımlar gerçekleştirilir:
  - Login ekranına gidilir
  - Dummy e-posta ve şifre alanları doldurulur
  - Geçersiz e-posta mesajı kontrol edilir
  - Zara logosuna tıklanarak ana sayfaya dönülür

> Not: Gerçek login akışı telefon/SMS doğrulaması gerektirebildiğinden, testte gerçek kullanıcı doğrulaması yapılmaz. Login sayfası POM olarak uygulanmış ve temel doğrulamalar eklenmiştir.

4. Menü > Erkek > Tümünü Gör
5. Excel’den veri okuma:
  - A1 hücresinden “şort” yazılır, silinir
  - B1 hücresinden “gömlek” yazılır ve Enter basılır
6. Arama sonucundan rastgele ürün seçilir
7. Ürün adı ve fiyatı konsola yazdırılır ve TXT dosyasına kaydedilir
8. Rastgele beden seçilerek sepete eklenir
9. Ürün sayfasındaki fiyat ile sepetteki ürün birim fiyatı karşılaştırılır
10. Adet 2 yapılır ve doğrulanır
11. Ürün sepetten silinir, sepetin boş olduğu kontrol edilir

### Çıktılar
- `target\test-output\product.txt` içine ürün adı ve fiyatı yazılır.

## API Otomasyon Senaryosu (Trello)
1. Board oluşturulur
2. Belirlenen kullanıcılar board’a davet edilir
3. Board listeleri okunur, ilk listeye 2 adet kart eklenir
4. Kartlardan biri rastgele seçilir
5. Cleanup:
  - Kartlar silinir
  - Board silinir

### Trello Key/Token (ENV)
Bu proje Trello API için **environment variable** kullanır:

- `TRELLO_KEY`
- `TRELLO_TOKEN`

#### IntelliJ üzerinden ekleme
Run/Debug Configurations → Environment variables kısmına ekleyin.

#### IntelliJ üzerinden ekleme

Bu proje, otomasyon ödevi amacıyla hazırlanmıştır.

Zara sitesindeki dinamik yapı nedeniyle beklemeler WebDriverWait ile yönetilmiştir.

#### Komut satırı örneği (PowerShell)
```powershell
$env:TRELLO_KEY="xxx"
$env:TRELLO_TOKEN="yyy"
mvn test