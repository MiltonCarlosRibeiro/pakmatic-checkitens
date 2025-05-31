package br.com.pakmatic.checklist.controller;

import br.com.pakmatic.checklist.model.ResultadoVarredura;
import br.com.pakmatic.checklist.model.ResultadoItemQuantidade;
import br.com.pakmatic.checklist.service.VarreduraService;
import br.com.pakmatic.checklist.service.ItemQuantidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller responsável pelas rotas de varredura entre planilhas Excel.
 */
@RestController
@RequestMapping("/api")
public class VarreduraController {

    @Autowired
    private VarreduraService varreduraService;

    @Autowired
    private ItemQuantidadeService itemQuantidadeService;

    /**
     * Varredura completa entre duas planilhas.
     */
    @PostMapping("/varredura")
    public ResponseEntity<ResultadoVarredura> fazerVarredura(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam("colProduto1") String colProduto1,
            @RequestParam("colQtd1") String colQtd1,
            @RequestParam("colProduto2") String colProduto2,
            @RequestParam("colQtd2") String colQtd2
    ) throws IOException {
        ResultadoVarredura resultado = varreduraService.analisar(file1, file2, colProduto1, colQtd1, colProduto2, colQtd2);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Nova rota: Varredura de quantidade de itens na estrutura.
     * Permite buscar quantas vezes um item aparece, somando as quantidades, com modo híbrido.
     */
    @PostMapping("/varredura-item-quantidade")
    public ResponseEntity<ResultadoItemQuantidade> buscarQuantidadeItem(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("item") String item,
            @RequestParam(value = "colunaProduto", required = false) String colunaProduto,
            @RequestParam(value = "modoHibrido", defaultValue = "false") boolean modoHibrido
    ) throws IOException {
        ResultadoItemQuantidade resultado = itemQuantidadeService.contarItemQuantidade(
                arquivo, item, colunaProduto, modoHibrido
        );
        return ResponseEntity.ok(resultado);
    }
}
