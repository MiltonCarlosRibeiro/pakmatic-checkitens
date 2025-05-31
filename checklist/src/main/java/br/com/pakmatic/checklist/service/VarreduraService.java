package br.com.pakmatic.checklist.service;

import br.com.pakmatic.checklist.model.ResultadoVarredura;
import br.com.pakmatic.checklist.util.ExcelReaderVarredura;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Serviço que realiza a varredura completa entre duas listas com base em colunas de produto e quantidade.
 */
@Service
public class VarreduraService {

    public ResultadoVarredura analisar(MultipartFile file1, MultipartFile file2,
                                       String colProduto1, String colQtd1,
                                       String colProduto2, String colQtd2) throws IOException {

        Map<String, Integer> mapa1 = ExcelReaderVarredura.lerArquivo(file1, colProduto1, colQtd1);
        Map<String, Integer> mapa2 = ExcelReaderVarredura.lerArquivo(file2, colProduto2, colQtd2);

        Set<String> todos = new HashSet<>();
        todos.addAll(mapa1.keySet());
        todos.addAll(mapa2.keySet());

        List<String> emAmbas = new ArrayList<>();
        List<String> apenasNaTabela1 = new ArrayList<>();
        List<String> apenasNaTabela2 = new ArrayList<>();
        List<String> quantidadeDiferente = new ArrayList<>();

        for (String produto : todos) {
            boolean em1 = mapa1.containsKey(produto);
            boolean em2 = mapa2.containsKey(produto);

            if (em1 && em2) {
                if (Objects.equals(mapa1.get(produto), mapa2.get(produto))) {
                    emAmbas.add(produto);
                } else {
                    quantidadeDiferente.add(produto + " (" + mapa1.get(produto) + " vs " + mapa2.get(produto) + ")");
                }
            } else if (em1) {
                apenasNaTabela1.add(produto);
            } else {
                apenasNaTabela2.add(produto);
            }
        }

        return new ResultadoVarredura(emAmbas, apenasNaTabela1, apenasNaTabela2, quantidadeDiferente);
    }
}
