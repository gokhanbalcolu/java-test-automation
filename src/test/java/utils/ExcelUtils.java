package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelUtils {

    public static String getCellData(String path, int row, int col) {
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Row r = sheet.getRow(row);
            if (r == null) {
                throw new RuntimeException("Excel row is empty: " + row);
            }

            Cell c = r.getCell(col);
            if (c == null) {
                throw new RuntimeException("Excel cell is empty: row " + row + " col " + col);
            }

            return c.getStringCellValue();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
