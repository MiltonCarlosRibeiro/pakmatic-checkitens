package br.com.pakmatic.checklist.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static Map<String, Integer> lerProdutos(MultipartFile file, String colunaChave, boolean procurarTodaLinha) throws IOException {
        Map<String, Integer> mapa = new HashMap<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            int linhaInicio = 1;

            for (int i = linhaInicio; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String chave = "";
                int quantidade = 1;

                if (procurarTodaLinha) {
                    for (Cell cell : row) {
                        if (cell.getCellType() == CellType.STRING) {
                            String valor = cell.getStringCellValue().trim();
                            if (!valor.isBlank()) {
                                mapa.put(valor, mapa.getOrDefault(valor, 0) + 1);
                            }
                        }
                    }
                } else {
                    Cell cellChave = row.getCell(buscarIndiceColuna(sheet, colunaChave));
                    if (cellChave != null && cellChave.getCellType() == CellType.STRING) {
                        chave = cellChave.getStringCellValue().trim();
                        mapa.put(chave, mapa.getOrDefault(chave, 0) + quantidade);
                    }
                }
            }
        }
        return mapa;
    }

    private static int buscarIndiceColuna(Sheet sheet, String nomeColuna) {
        Row header = sheet.getRow(0);
        for (Cell cell : header) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(nomeColuna)) {
                return cell.getColumnIndex();
            }
        }
        throw new IllegalArgumentException("Coluna não encontrada: " + nomeColuna);
    }
}
