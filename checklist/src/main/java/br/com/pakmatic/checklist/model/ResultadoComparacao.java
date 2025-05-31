package br.com.pakmatic.checklist.model;

import java.util.List;

/**
 * Representa o resultado de uma única comparação entre duas colunas de códigos e quantidades.
 * Contém os itens:
 * - Apenas na Tabela 1
 * - Apenas na Tabela 2
 * - Comuns a ambas
 */
public class ResultadoComparacao {

    private List<String> apenasNaListaAntiga;
    private List<String> apenasNaListaNova;
    private List<String> emAmbas;

    public ResultadoComparacao(List<String> apenasNaListaAntiga, List<String> apenasNaListaNova, List<String> emAmbas) {
        this.apenasNaListaAntiga = apenasNaListaAntiga;
        this.apenasNaListaNova = apenasNaListaNova;
        this.emAmbas = emAmbas;
    }

    public List<String> getApenasNaListaAntiga() {
        return apenasNaListaAntiga;
    }

    public void setApenasNaListaAntiga(List<String> apenasNaListaAntiga) {
        this.apenasNaListaAntiga = apenasNaListaAntiga;
    }

    public List<String> getApenasNaListaNova() {
        return apenasNaListaNova;
    }

    public void setApenasNaListaNova(List<String> apenasNaListaNova) {
        this.apenasNaListaNova = apenasNaListaNova;
    }

    public List<String> getEmAmbas() {
        return emAmbas;
    }

    public void setEmAmbas(List<String> emAmbas) {
        this.emAmbas = emAmbas;
    }
}
