package br.com.pakmatic.checklist.service;

import br.com.pakmatic.checklist.dto.ItemQuantidadeDTO;
import br.com.pakmatic.checklist.model.ResultadoItemQuantidade;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço que varre uma planilha em busca de um item específico,
 * somando todas as suas quantidades.
 */
@Service
public class VarreduraQuantidadeService {

    public ItemQuantidadeDTO varrerItem(MultipartFile file, String colunaItem, String colunaQuantidade, String itemBuscado) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);

            int indexItem = -1;
            int indexQtd = -1;

            for (Cell cell : header) {
                String value = cell.getStringCellValue();
                if (value.equalsIgnoreCase(colunaItem)) {
                    indexItem = cell.getColumnIndex();
                }
                if (value.equalsIgnoreCase(colunaQuantidade)) {
                    indexQtd = cell.getColumnIndex();
                }
            }

            if (indexItem == -1 || indexQtd == -1) {
                throw new IllegalArgumentException("❌ Coluna de item ou quantidade não encontrada.");
            }

            double soma = 0.0;
            List<String> linhasEncontradas = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell itemCell = row.getCell(indexItem);
                if (itemCell == null) continue;

                String item = itemCell.getStringCellValue();
                if (itemBuscado.equalsIgnoreCase(item.trim())) {
                    Cell qtdCell = row.getCell(indexQtd);
                    double qtd = (qtdCell != null && qtdCell.getCellType() == CellType.NUMERIC) ? qtdCell.getNumericCellValue() : 0.0;
                    soma += qtd;

                    linhasEncontradas.add("Linha " + (i + 1) + ": " + item + " - Quantidade: " + qtd);
                }
            }

            workbook.close();

            return new ItemQuantidadeDTO(itemBuscado, linhasEncontradas, soma);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler planilha: " + e.getMessage(), e);
        }
    }
}
