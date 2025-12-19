# Zara Test Otomasyon Projesi

Bu proje, **Selenium WebDriver** kullanılarak Zara Türkiye web sitesi üzerinde
uçtan uca (E2E) test otomasyonu gerçekleştirmek amacıyla hazırlanmıştır.
Proje, gerçek bir e-ticaret senaryosunu temel alır ve otomasyon mimarisi,
kurumsal projelerde kullanılan standartlara uygun şekilde tasarlanmıştır.

---

## Kullanılan Teknolojiler

- **Java**
- **Maven**
- **Selenium WebDriver**
- **JUnit 5**
- **Log4j** (loglama)
- **Apache POI** (Excel veri okuma)
- **Rest-Assured** (API otomasyonu – opsiyonel modül)

---

## Proje Mimarisi

- **Page Object Model (POM)** tasarım deseni kullanılmıştır
- **OOP (Nesne Yönelimli Programlama)** prensiplerine uygun geliştirilmiştir
- Web ve API otomasyonları **ayrı katmanlarda** ele alınmıştır
- Testler okunabilir, sürdürülebilir ve genişletilebilir olacak şekilde tasarlanmıştır

---

## Selenium Web Otomasyon Senaryosu

1. Zara Türkiye web sitesi açılır
2. Çerez (cookie) bilgilendirme popup’ı kabul edilir

### Login Akışı

- Login ekranı **Page Object** olarak oluşturulmuştur

> **Önemli Not:**  
> Login işlemi teknik olarak tasarlanmış ve gerekli locator/metotlar
> tanımlanmıştır.  
> Ancak Zara giriş süreci telefon numarası ve SMS doğrulaması gerektirdiği için,
> test çalıştırmaları sırasında **bilinçli olarak atlanmıştır**.
>
> Senaryo, **misafir kullanıcı (guest user)** akışı ile devam eder.  
> Bu yaklaşım, güvenlik ve doğrulama gerektiren adımların otomasyon dışında
> bırakılması açısından **sektörde yaygın ve kabul edilebilir bir yöntemdir**.

---

## Ürün Arama ve Sepet Senaryosu

- Menü → **Erkek → Tümünü Gör** adımlarına gidilir
- Arama kelimeleri **Excel dosyasından** okunur
  - İlk kelime yazılır ve silinir
  - İkinci kelime yazılır ve **Enter** ile arama yapılır
- Arama sonuçlarından **rastgele bir ürün** seçilir
- Ürün adı ve fiyat bilgisi alınır
- Ürün bilgileri `.txt` dosyasına yazılır
- Ürün sepete eklenir
  - Rastgele uygun beden seçimi yapılır
- Ürün detay sayfasındaki fiyat ile sepetteki birim fiyat karşılaştırılır
- Ürün adedi artırılarak fiyat doğrulaması yapılır
- Ürün sepetten silinir
- Sepetin boş olduğu kontrol edilir

---

## Bekleme (Wait) Yönetimi

- Testlerde **hard wait kullanımından kaçınılmıştır**
- Ağırlıklı olarak:
  - `WebDriverWait`
  - `ExpectedConditions`
    kullanılmıştır

Bazı sayfa geçişlerinde, akışın daha net gözlemlenebilmesi amacıyla
**bilinçli olarak kısa bekleme süreleri** eklenmiştir.

Bu yapı;
- Dinamik elementlerin stabil yakalanmasını
- Demo ve değerlendirme süreçlerinde test akışının daha anlaşılır olmasını
  amaçlar.

CI/CD veya performans odaklı çalışmalarda bu süreler kolaylıkla
optimize edilebilir yapıdadır.

---

## API Otomasyonu (Ek Modül)

Proje kapsamında Selenium dışında, örnek amaçlı bir **API otomasyon modülü**
de yer almaktadır.

- **Trello REST API**
- **Rest-Assured** kullanılmıştır
- Board oluşturma
- Kart oluşturma
- Rastgele kart seçimi
- Temizlik işlemleri (kart ve board silme)

> API otomasyonu, Selenium testlerinden **bağımsız** bir modül olarak
> yapılandırılmıştır.

---

## Genel Notlar

- Bu proje bir **test otomasyonu ödevi / demo çalışması** kapsamında hazırlanmıştır
- Gerçek kullanıcı verisi, SMS doğrulama ve güvenlik gerektiren adımlar
  bilinçli olarak test kapsamı dışında bırakılmıştır
- Proje, kurumsal Selenium projelerinde kullanılan mimari ve yaklaşımları
  örneklemek amacıyla geliştirilmiştir

---

## Çalıştırma

```bash
mvn clean test
