package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class TxtUtils {

    // Test çıktısı: target altında (git'e push edilmez, CI/CD'de doğru yer)
    private static final Path OUTPUT_FILE =
            Paths.get("target", "test-output", "product.txt");

    public static void writeProductInfo(String name, String price) {
        String content =
                "Ürün Adı  : " + name + System.lineSeparator() +
                        "Ürün Fiyat: " + price + System.lineSeparator() +
                        "------------------------" + System.lineSeparator();

        appendToFile(content);
    }

    // İstersen public de yapabiliriz; şimdilik içeride kullanalım
    private static void appendToFile(String content) {
        try {
            // Klasör yoksa oluştur
            Files.createDirectories(OUTPUT_FILE.getParent());

            // Dosyaya yaz (yoksa oluştur, varsa ekle)
            Files.writeString(
                    OUTPUT_FILE,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("📝 TXT dosyasına yazıldı → " + OUTPUT_FILE.toAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("TXT dosyasına yazılamadı!", e);
        }
    }

    // Her test koşusunda dosyayı sıfırlamak istersen bunu çağırabilirsin
    public static void clearOutputFile() {
        try {
            Files.createDirectories(OUTPUT_FILE.getParent());
            Files.writeString(
                    OUTPUT_FILE,
                    "",
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("TXT output temizlenemedi!", e);
        }
    }
}
