package br.com.pakmatic.checklist.service;

import br.com.pakmatic.checklist.model.ResultadoItemQuantidade;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Serviço que realiza a varredura de um item em uma planilha Excel,
 * somando a quantidade total associada ao item (mesma linha).
 */
@Service
public class ItemQuantidadeService {

    /**
     * Realiza a análise da planilha em busca do item informado,
     * somando as quantidades encontradas na mesma linha.
     *
     * @param file Arquivo Excel enviado
     * @param itemBusca Texto a ser procurado (ex: M8x10)
     * @param colunaProduto Nome da coluna com os itens (ex: B1 ou vazio para procurar na linha inteira)
     * @param buscarEmTodaLinha Se true, busca o item em todas as células da linha
     * @return ResultadoItemQuantidade com contagem e soma de quantidades
     * @throws IOException Erro de leitura da planilha
     */
    public ResultadoItemQuantidade analisar(MultipartFile file, String itemBusca, String colunaProduto, boolean buscarEmTodaLinha) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, String> headerMap = new HashMap<>();
        ResultadoItemQuantidade resultado = new ResultadoItemQuantidade(itemBusca);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                }
                continue;
            }

            boolean encontrou = false;
            String textoLinha = "";
            Double qtdLinha = null;

            for (Cell cell : row) {
                String cellText = getCellText(cell);
                textoLinha += cellText + " | ";

                if (buscarEmTodaLinha && cellText.equalsIgnoreCase(itemBusca)) {
                    encontrou = true;
                }
            }

            if (!buscarEmTodaLinha && colunaProduto != null && !colunaProduto.isBlank()) {
                for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(colunaProduto)) {
                        Cell produtoCell = row.getCell(entry.getKey());
                        if (produtoCell != null && getCellText(produtoCell).equalsIgnoreCase(itemBusca)) {
                            encontrou = true;
                            break;
                        }
                    }
                }
            }

            if (encontrou) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        qtdLinha = cell.getNumericCellValue();
                        break;
                    }
                }
                resultado.adicionarLinha(row.getRowNum() + 1, textoLinha.trim(), qtdLinha);
            }
        }

        workbook.close();
        return resultado;
    }

    /**
     * Método compatível com o controller para varredura de item com quantidade.
     */
    public ResultadoItemQuantidade contarItemQuantidade(MultipartFile arquivo, String item, String colunaProduto, boolean modoHibrido) throws IOException {
        return analisar(arquivo, item, colunaProduto, modoHibrido);
    }

    private String getCellText(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
