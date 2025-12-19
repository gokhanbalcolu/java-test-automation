# Zara Test Otomasyon Projesi (Web + Trello API)

Bu proje, bir **test otomasyonu ödevi** kapsamında geliştirilmiş olup:

* **Zara web sitesi** için Selenium tabanlı UI otomasyon senaryolarını
* **Trello REST API** için Rest-Assured tabanlı API otomasyon senaryolarını
  içermektedir.

Web ve API testleri **ayrı katmanlarda** ele alınmış, gerçek hayat senaryolarına uygun şekilde kurgulanmıştır.

---

## Kullanılan Teknolojiler

* **Java 17**
* **Maven**
* **Selenium WebDriver**
* **JUnit 5**
* **WebDriverManager**
* **Apache POI** (Excel okuma)
* **Rest-Assured** (API testleri)
* **Log4j / SLF4J**

---

## Proje Yapısı

* **Page Object Model (POM)** tasarım deseni uygulanmıştır.
* Web UI testleri ve API testleri **ayrı test sınıflarında** geliştirilmiştir.
* Test verileri **Excel** dosyasından okunur.
* Ürün bilgileri **TXT** dosyasına yazdırılır.
* Beklemeler, dinamik yapı nedeniyle **WebDriverWait** ile yönetilmiştir.

---

## Web Otomasyon Senaryosu (Zara)

1. Zara Türkiye web sitesi açılır.
2. Cookie (çerez) pop-up kabul edilir.
3. **Login Page** Page Object olarak oluşturulmuştur ve aşağıdaki kontroller yapılır:

    * Login ekranına yönlendirme
    * Dummy e-posta ve şifre alanlarının doldurulması
    * Geçersiz e-posta uyarı mesajının doğrulanması
    * Zara logosuna tıklanarak ana sayfaya dönüş

> **Not:**
> Zara’nın gerçek login akışı telefon numarası ve SMS doğrulaması gerektirdiğinden,
> test senaryosunda gerçek kullanıcı doğrulaması **bilinçli olarak yapılmamıştır**.
> Login sayfası POM yapısında modellenmiş ve temel validasyonlar test edilmiştir.

4. Menü → **Erkek** → **Tümünü Gör** adımlarına gidilir.
5. Excel üzerinden veri okuma:

    * A1 hücresine “şort” yazılır ve silinir
    * B1 hücresinden “gömlek” okunur ve Enter tuşuna basılır
6. Arama sonucundan **rastgele bir ürün** seçilir.
7. Ürün adı ve fiyat:

    * Konsola yazdırılır
    * TXT dosyasına kaydedilir
8. Rastgele beden seçilerek ürün sepete eklenir.
9. Ürün sayfasındaki fiyat ile sepetteki ürün birim fiyatı karşılaştırılır.
10. Ürün adedi **2** yapılır ve doğrulanır.
11. Ürün sepetten silinir ve sepetin boş olduğu kontrol edilir.

### Çıktılar

* Ürün adı ve fiyat bilgileri
  `target/test-output/product.txt`
  dosyasına yazılır.

---

## API Otomasyon Senaryosu (Trello)

1. Yeni bir **Board** oluşturulur.
2. Belirlenen kullanıcılar board’a davet edilir.
3. Board üzerindeki listeler okunur.
4. İlk listeye **2 adet kart** eklenir.
5. Kartlardan biri **rastgele** seçilir.
6. **Cleanup işlemleri** gerçekleştirilir:

    * Kartlar silinir
    * Board silinir

---

## Trello API Key / Token (Environment Variable)

Bu proje Trello API erişimi için **environment variable** kullanmaktadır.

Gerekli değişkenler:

* `TRELLO_KEY`
* `TRELLO_TOKEN`

### IntelliJ IDEA üzerinden ekleme

`Run / Debug Configurations` → `Environment Variables` alanına ekleyiniz.

### Komut Satırı (PowerShell) Örneği

```powershell
$env:TRELLO_KEY="xxx"
$env:TRELLO_TOKEN="yyy"
mvn test
```

---

## Notlar

* Bu proje **otomasyon ödevi** amacıyla hazırlanmıştır.
* Zara sitesinin dinamik yapısı nedeniyle beklemeler explicit wait ile yönetilmiştir.
* Gerçek kullanıcı verileri ve canlı hesaplar kullanılmamıştır.
