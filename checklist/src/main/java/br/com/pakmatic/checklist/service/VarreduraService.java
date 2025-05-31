package br.com.pakmatic.checklist.service;

import br.com.pakmatic.checklist.model.ResultadoVarredura;
import br.com.pakmatic.checklist.util.ExcelReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

/**
 * Serviço que realiza a varredura automática entre duas planilhas.
 */
@Service
public class VarreduraService {

    public ResultadoVarredura analisarComparativo(
            MultipartFile arquivo1,
            MultipartFile arquivo2,
            String colunaProduto1,
            String colunaProduto2,
            boolean procurarTodaLinha
    ) throws IOException {

        Map<String, Integer> produtos1 = ExcelReader.lerProdutos(arquivo1, colunaProduto1, procurarTodaLinha);
        Map<String, Integer> produtos2 = ExcelReader.lerProdutos(arquivo2, colunaProduto2, procurarTodaLinha);

        Set<String> todosItens = new HashSet<>();
        todosItens.addAll(produtos1.keySet());
        todosItens.addAll(produtos2.keySet());

        List<String> emAmbas = new ArrayList<>();
        List<String> apenasNaTabela1 = new ArrayList<>();
        List<String> apenasNaTabela2 = new ArrayList<>();
        List<String> quantidadeDiferente = new ArrayList<>();

        for (String item : todosItens) {
            boolean existe1 = produtos1.containsKey(item);
            boolean existe2 = produtos2.containsKey(item);

            if (existe1 && existe2) {
                int qtd1 = produtos1.get(item);
                int qtd2 = produtos2.get(item);
                if (qtd1 == qtd2) {
                    emAmbas.add(item);
                } else {
                    quantidadeDiferente.add(item + " (T1: " + qtd1 + ", T2: " + qtd2 + ")");
                }
            } else if (existe1) {
                apenasNaTabela1.add(item + " (Qtd: " + produtos1.get(item) + ")");
            } else {
                apenasNaTabela2.add(item + " (Qtd: " + produtos2.get(item) + ")");
            }
        }

        return new ResultadoVarredura(emAmbas, apenasNaTabela1, apenasNaTabela2, quantidadeDiferente);
    }
}
