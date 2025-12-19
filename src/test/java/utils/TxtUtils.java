package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TxtUtils {

    private static final String FILE_PATH =
            "src/test/resources/output/product.txt";

    public static void writeProductInfo(String name, String price) {

        try {
            Path path = Paths.get(FILE_PATH);

            // Dosya yoksa oluştur
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            String content =
                    "Ürün Adı  : " + name + System.lineSeparator() +
                            "Ürün Fiyat: " + price + System.lineSeparator() +
                            "------------------------" + System.lineSeparator();

            Files.write(
                    path,
                    content.getBytes(),
                    StandardOpenOption.APPEND
            );

            System.out.println("📝 TXT dosyasına yazıldı → " + path.toAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("TXT dosyasına yazılamadı!", e);
        }
    }
}
