package br.com.pakmatic.checklist.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * Utilitário para leitura de colunas específicas de código e quantidade em planilhas Excel.
 */
public class ExcelDynamicComparator {

    /**
     * Extrai um mapa código → soma de quantidade com base em duas colunas fornecidas.
     *
     * @param file         Arquivo Excel (.xlsx)
     * @param colCodigo    Nome da coluna com os códigos
     * @param colQuantidade Nome da coluna com as quantidades
     * @return Mapa código → quantidade somada
     */
    public static Map<String, Integer> extrairCodigosEQuantidades(MultipartFile file, String colCodigo, String colQuantidade) {
        Map<String, Integer> mapa = new HashMap<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) return mapa;

            Row headerRow = rowIterator.next();
            int idxCodigo = -1, idxQuantidade = -1;

            for (Cell cell : headerRow) {
                String valor = cell.getStringCellValue().trim();
                if (valor.equalsIgnoreCase(colCodigo)) idxCodigo = cell.getColumnIndex();
                if (valor.equalsIgnoreCase(colQuantidade)) idxQuantidade = cell.getColumnIndex();
            }

            if (idxCodigo == -1 || idxQuantidade == -1) return mapa;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cellCod = row.getCell(idxCodigo);
                Cell cellQtd = row.getCell(idxQuantidade);

                if (cellCod != null && cellQtd != null) {
                    cellCod.setCellType(CellType.STRING);
                    cellQtd.setCellType(CellType.NUMERIC);
                    String cod = cellCod.getStringCellValue().trim();
                    int qtd = (int) cellQtd.getNumericCellValue();
                    if (!cod.isEmpty()) {
                        mapa.put(cod, mapa.getOrDefault(cod, 0) + qtd);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapa;
    }
}
