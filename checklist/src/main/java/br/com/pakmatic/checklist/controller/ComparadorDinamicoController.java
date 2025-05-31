package br.com.pakmatic.checklist.controller;

import br.com.pakmatic.checklist.model.ResultadoComparacao;
import br.com.pakmatic.checklist.model.ResultadoComparacaoLote;
import br.com.pakmatic.checklist.service.ComparadorDinamicoService;
import br.com.pakmatic.checklist.util.ExcelDynamicComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Controller para comparação dinâmica de planilhas com múltiplos pares de colunas.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ComparadorDinamicoController {

    @Autowired
    private ComparadorDinamicoService comparadorService;

    /**
     * Endpoint para comparar vários pares de colunas dinamicamente.
     *
     * @param tabela1 Arquivo Excel 1
     * @param tabela2 Arquivo Excel 2
     * @param colProd1 Lista de colunas de código da Tabela 1
     * @param colQtd1 Lista de colunas de quantidade da Tabela 1
     * @param colProd2 Lista de colunas de código da Tabela 2
     * @param colQtd2 Lista de colunas de quantidade da Tabela 2
     * @return ResultadoComparacaoLote
     */
    @PostMapping("/comparar-dinamico")
    public ResponseEntity<ResultadoComparacaoLote> compararMultiplosPares(
            @RequestParam("tabela1") MultipartFile tabela1,
            @RequestParam("tabela2") MultipartFile tabela2,
            @RequestParam("colProd1[]") List<String> colProd1,
            @RequestParam("colQtd1[]") List<String> colQtd1,
            @RequestParam("colProd2[]") List<String> colProd2,
            @RequestParam("colQtd2[]") List<String> colQtd2
    ) {
        List<ResultadoComparacao> resultados = new ArrayList<>();

        for (int i = 0; i < colProd1.size(); i++) {
            Map<String, Integer> mapa1 = ExcelDynamicComparator.extrairCodigosEQuantidades(tabela1, colProd1.get(i), colQtd1.get(i));
            Map<String, Integer> mapa2 = ExcelDynamicComparator.extrairCodigosEQuantidades(tabela2, colProd2.get(i), colQtd2.get(i));
            resultados.add(comparadorService.comparar(mapa1, mapa2));
        }

        return ResponseEntity.ok(new ResultadoComparacaoLote(resultados));
    }
}
