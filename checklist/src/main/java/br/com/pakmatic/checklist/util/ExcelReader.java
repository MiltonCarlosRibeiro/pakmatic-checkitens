package br.com.pakmatic.checklist.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe utilitária para ler colunas específicas de arquivos Excel.
 */
@Component
public class ExcelReader {

    /**
     * Extrai todos os valores de uma coluna específica com base no nome do cabeçalho.
     *
     * @param file     Arquivo Excel .xlsx
     * @param nomeColuna Nome do cabeçalho da coluna a ser extraída
     * @return Lista de Strings com os valores da coluna
     */
    public List<String> extrairValores(MultipartFile file, String nomeColuna) {
        List<String> valores = new ArrayList<>();

        try (InputStream input = file.getInputStream(); Workbook workbook = new XSSFWorkbook(input)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) return valores;

            Row headerRow = rowIterator.next();
            int colunaIndex = -1;
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equalsIgnoreCase(nomeColuna.trim())) {
                    colunaIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (colunaIndex == -1) return valores;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(colunaIndex);
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    valores.add(cell.getStringCellValue().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valores;
    }
}
