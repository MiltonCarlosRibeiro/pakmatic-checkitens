package br.com.pakmatic.checklist.model;

import java.util.List;

/**
 * Modelo de dados para representar o resultado da varredura entre duas tabelas.
 */
public class ResultadoVarredura {

    private List<String> emAmbas;
    private List<String> apenasNaTabela1;
    private List<String> apenasNaTabela2;
    private List<String> quantidadeDiferente;

    public ResultadoVarredura(List<String> emAmbas, List<String> apenasNaTabela1,
                              List<String> apenasNaTabela2, List<String> quantidadeDiferente) {
        this.emAmbas = emAmbas;
        this.apenasNaTabela1 = apenasNaTabela1;
        this.apenasNaTabela2 = apenasNaTabela2;
        this.quantidadeDiferente = quantidadeDiferente;
    }

    public List<String> getEmAmbas() {
        return emAmbas;
    }

    public List<String> getApenasNaTabela1() {
        return apenasNaTabela1;
    }

    public List<String> getApenasNaTabela2() {
        return apenasNaTabela2;
    }

    public List<String> getQuantidadeDiferente() {
        return quantidadeDiferente;
    }
}
