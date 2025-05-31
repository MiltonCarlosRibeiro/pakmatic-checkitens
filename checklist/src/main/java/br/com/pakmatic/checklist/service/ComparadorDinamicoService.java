package br.com.pakmatic.checklist.service;

import br.com.pakmatic.checklist.model.ResultadoComparacao;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serviço para comparar códigos de duas listas com quantidades personalizadas.
 */
@Service
public class ComparadorDinamicoService {

    /**
     * Compara dois mapas código → quantidade e classifica os itens.
     *
     * @param mapa1 Dados da Tabela 1: código → quantidade
     * @param mapa2 Dados da Tabela 2: código → quantidade
     * @return ResultadoComparacao com itens comuns e exclusivos
     */
    public ResultadoComparacao comparar(Map<String, Integer> mapa1, Map<String, Integer> mapa2) {
        Set<String> todosCodigos = new HashSet<>();
        todosCodigos.addAll(mapa1.keySet());
        todosCodigos.addAll(mapa2.keySet());

        List<String> apenasNaListaAntiga = new ArrayList<>();
        List<String> apenasNaListaNova = new ArrayList<>();
        List<String> emAmbas = new ArrayList<>();

        for (String codigo : todosCodigos) {
            boolean em1 = mapa1.containsKey(codigo);
            boolean em2 = mapa2.containsKey(codigo);

            if (em1 && em2) {
                emAmbas.add(codigo + " (T1=" + mapa1.get(codigo) + ", T2=" + mapa2.get(codigo) + ")");
            } else if (em1) {
                apenasNaListaAntiga.add(codigo + " (Qtd=" + mapa1.get(codigo) + ")");
            } else {
                apenasNaListaNova.add(codigo + " (Qtd=" + mapa2.get(codigo) + ")");
            }
        }

        Collections.sort(apenasNaListaAntiga);
        Collections.sort(apenasNaListaNova);
        Collections.sort(emAmbas);

        return new ResultadoComparacao(apenasNaListaAntiga, apenasNaListaNova, emAmbas);
    }
}
