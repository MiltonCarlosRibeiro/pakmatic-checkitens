package br.com.pakmatic.checklist.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Exporta os dados comparativos em PDF, lado a lado, com cabeçalho e rodapé.
 */
@RestController
public class ExportacaoPdfController {

    @GetMapping("/api/exportar-pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        // Mock de dados (substitua com dados reais futuramente)
        List<Map<String, String>> dados = Arrays.asList(
                Map.of("ITEM_COMPONENTE", "C1-001", "STATUS", "✅ Em ambas", "QTDE_TABELA_1", "10", "QTDE_TABELA_2", "10"),
                Map.of("ITEM_COMPONENTE", "C1-008", "STATUS", "📤 Só na Tabela 1", "QTDE_TABELA_1", "9", "QTDE_TABELA_2", "-"),
                Map.of("ITEM_COMPONENTE", "C1-011", "STATUS", "📥 Só na Tabela 2", "QTDE_TABELA_1", "-", "QTDE_TABELA_2", "1")
        );

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Relatorio_Comparacao.pdf");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("📋 Relatório de Comparação", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Tabela
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{3, 3, 3, 3});

            addCellHeader(table, "ITEM_COMPONENTE");
            addCellHeader(table, "STATUS");
            addCellHeader(table, "QTDE_TABELA_1");
            addCellHeader(table, "QTDE_TABELA_2");

            for (Map<String, String> linha : dados) {
                table.addCell(linha.get("ITEM_COMPONENTE"));
                table.addCell(linha.get("STATUS"));
                table.addCell(linha.get("QTDE_TABELA_1"));
                table.addCell(linha.get("QTDE_TABELA_2"));
            }

            document.add(table);

            // Rodapé
            Paragraph rodape = new Paragraph("📎 Sistema Pakmatic - Comparação de estruturas", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            rodape.setAlignment(Element.ALIGN_CENTER);
            rodape.setSpacingBefore(30);
            document.add(rodape);

            document.close();
            writer.close();
        } catch (DocumentException e) {
            throw new IOException("Erro ao gerar PDF", e);
        }
    }

    private void addCellHeader(PdfPTable table, String texto) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setPhrase(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        table.addCell(header);
    }
}
