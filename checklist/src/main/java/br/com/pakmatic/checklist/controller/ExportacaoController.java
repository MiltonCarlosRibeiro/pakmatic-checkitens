package br.com.pakmatic.checklist.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class ExportacaoController {

    /**
     * Exporta os dados comparativos em um arquivo Excel formatado lado a lado.
     */
    @GetMapping("/api/exportar-comparacao")
    public void exportarComparacao(HttpServletResponse response) throws IOException {
        // Mock de dados (você pode substituir por dados reais do seu serviço)
        List<Map<String, String>> dados = new ArrayList<>();

        dados.add(Map.of("ITEM_COMPONENTE", "C1-001", "STATUS", "✅ Em ambas", "QTDE_TABELA_1", "10", "QTDE_TABELA_2", "10"));
        dados.add(Map.of("ITEM_COMPONENTE", "C1-008", "STATUS", "📤 Só na Tabela 1", "QTDE_TABELA_1", "9", "QTDE_TABELA_2", "-"));
        dados.add(Map.of("ITEM_COMPONENTE", "C1-011", "STATUS", "📥 Só na Tabela 2", "QTDE_TABELA_1", "-", "QTDE_TABELA_2", "1"));

        // Configurar resposta
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Comparacao_Lado_a_Lado.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Comparação");

            // Cabeçalhos
            Row header = sheet.createRow(0);
            String[] colunas = {"ITEM_COMPONENTE", "STATUS", "QTDE_TABELA_1", "QTDE_TABELA_2"};
            for (int i = 0; i < colunas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(colunas[i]);
            }

            // Dados
            for (int i = 0; i < dados.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Map<String, String> linha = dados.get(i);
                for (int j = 0; j < colunas.length; j++) {
                    row.createCell(j).setCellValue(linha.getOrDefault(colunas[j], ""));
                }
            }

            workbook.write(response.getOutputStream());
        }
    }
}
