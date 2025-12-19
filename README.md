# Zara Test Otomasyon Projesi

## Kullanılan Teknolojiler
- Java
- Maven
- Selenium WebDriver
- JUnit 5
- Log4j
- Apache POI (Excel işlemleri)
- Rest-Assured (API testleri)

## Proje Yapısı
- Page Object Model (POM) tasarım deseni kullanılmıştır
- OOP (Nesne Yönelimli Programlama) prensiplerine uygun geliştirilmiştir
- Web ve API otomasyonları için ayrı sayfa (page) sınıfları bulunmaktadır

## Web Otomasyon Senaryosu

- Zara Türkiye web sitesi açılır
- Çerez (cookie) popup’ı kabul edilir

### Login Adımı
- Login sayfası Page Object olarak **oluşturulmuştur**

> **Not:**  
> Login işlemi için gerekli sayfa ve metotlar tanımlanmış olmasına rağmen,
> gerçek kullanıcı doğrulaması (telefon numarası ve SMS onayı) gerektirdiği için
> test çalıştırması sırasında **bilinçli olarak atlanmıştır**.
>
> Senaryo, misafir kullanıcı (guest user) olarak devam etmektedir.
> Bu yaklaşım, güvenlik hassasiyeti olan akışlar için otomasyon projelerinde
> yaygın ve kabul edilebilir bir yöntemdir.

- Menü → Erkek → Tümünü Gör adımlarına gidilir
- Arama kelimeleri Excel dosyasından okunur
  - İlk kelime yazılır ve silinir
  - İkinci kelime yazılır ve Enter tuşu ile arama yapılır
- Arama sonuçlarından rastgele bir ürün seçilir
- Ürün adı ve fiyat bilgisi alınır
- Ürün bilgileri `.txt` dosyasına yazılır
- Ürün sepete eklenir (rastgele uygun beden seçimi yapılır)
- Ürün sayfasındaki fiyat ile sepetteki birim fiyat karşılaştırılır
- Ürün adedi artırılarak doğrulanır
- Ürün sepetten silinir ve sepetin boş olduğu kontrol edilir

## API Otomasyon (Web Servis)

- Trello REST API kullanılarak Rest-Assured ile otomasyon yapılmıştır
- Board oluşturma
- Kart oluşturma
- Rastgele kart seçimi
- Temizlik işlemleri (kart ve board silme)

## Notlar

Bu proje, otomasyon ödevi kapsamında hazırlanmıştır.  
Login adımı teknik olarak tasarlanmış ancak gerçek kullanıcı verisi ve güvenlik
kısıtları nedeniyle test çalıştırmasında bilinçli olarak atlanmıştır.
