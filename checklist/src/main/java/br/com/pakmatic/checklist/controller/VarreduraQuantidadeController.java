package br.com.pakmatic.checklist.controller;

import br.com.pakmatic.checklist.dto.ItemQuantidadeDTO;
import br.com.pakmatic.checklist.service.VarreduraQuantidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VarreduraQuantidadeController {

    @Autowired
    private VarreduraQuantidadeService service;

    /**
     * Endpoint para varredura de um item específico e soma total de quantidades.
     *
     * @param file             Excel enviado
     * @param colunaItem       Nome da coluna onde está o código do item
     * @param colunaQuantidade Nome da coluna onde está a quantidade
     * @param item             Item que deseja procurar (ex: M8x10)
     * @return Lista de ocorrências e soma total
     */
    @PostMapping("/varredura-quantidade")
    public ResponseEntity<ItemQuantidadeDTO> buscarItem(
            @RequestParam("arquivo") MultipartFile file,
            @RequestParam("colunaItem") String colunaItem,
            @RequestParam("colunaQuantidade") String colunaQuantidade,
            @RequestParam("item") String item
    ) {
        ItemQuantidadeDTO resultado = service.varrerItem(file, colunaItem, colunaQuantidade, item);
        return ResponseEntity.ok(resultado);
    }
}
