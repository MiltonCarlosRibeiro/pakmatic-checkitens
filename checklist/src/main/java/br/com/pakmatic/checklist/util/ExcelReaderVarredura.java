package br.com.pakmatic.checklist.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utilitário para leitura de arquivos Excel dinâmicos com colunas personalizadas.
 */
public class ExcelReaderVarredura {

    /**
     * Lê o conteúdo de um arquivo Excel e retorna um mapa com produto -> quantidade.
     * @param file arquivo .xlsx enviado
     * @param colProduto nome exato da coluna com os códigos do produto
     * @param colQtd nome exato da coluna com a quantidade associada
     * @return Mapa com produto como chave e quantidade como valor
     * @throws IOException se ocorrer erro de leitura
     */
    public static Map<String, Integer> lerArquivo(MultipartFile file, String colProduto, String colQtd) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        Map<String, Integer> mapa = new HashMap<>();
        int idxProduto = -1;
        int idxQtd = -1;

        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                if (header.equalsIgnoreCase(colProduto)) idxProduto = cell.getColumnIndex();
                if (header.equalsIgnoreCase(colQtd)) idxQtd = cell.getColumnIndex();
            }
        }

        if (idxProduto == -1 || idxQtd == -1) {
            throw new IllegalArgumentException("Colunas não encontradas. Verifique os nomes exatos informados.");
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cellProd = row.getCell(idxProduto);
            Cell cellQtd = row.getCell(idxQtd);

            if (cellProd == null || cellQtd == null) continue;

            String prod = cellProd.getStringCellValue().trim();
            int qtd = (int) cellQtd.getNumericCellValue();

            mapa.put(prod, qtd);
        }

        workbook.close();
        return mapa;
    }
}
