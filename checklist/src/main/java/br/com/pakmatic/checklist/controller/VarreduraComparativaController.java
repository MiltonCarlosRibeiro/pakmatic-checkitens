package br.com.pakmatic.checklist.controller;

import br.com.pakmatic.checklist.model.ResultadoVarredura;
import br.com.pakmatic.checklist.service.VarreduraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * Controller dedicado à varredura automática entre duas planilhas,
 * identificando itens comuns, exclusivos e diferenças de quantidade.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VarreduraComparativaController {

    @Autowired
    private VarreduraService varreduraService;

    @PostMapping("/varredura-comparativa")
    public ResponseEntity<ResultadoVarredura> compararAutomaticamente(
            @RequestParam("arquivo1") MultipartFile arquivo1,
            @RequestParam("arquivo2") MultipartFile arquivo2,
            @RequestParam(value = "colunaProduto1", required = false) String colunaProduto1,
            @RequestParam(value = "colunaProduto2", required = false) String colunaProduto2,
            @RequestParam("procurarTodaLinha") boolean procurarTodaLinha
    ) throws IOException {
        ResultadoVarredura resultado = varreduraService.analisarComparativo(
                arquivo1, arquivo2, colunaProduto1, colunaProduto2, procurarTodaLinha
        );
        return ResponseEntity.ok(resultado);
    }
}
