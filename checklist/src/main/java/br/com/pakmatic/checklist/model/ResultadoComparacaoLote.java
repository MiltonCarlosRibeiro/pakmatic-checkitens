package br.com.pakmatic.checklist.model;


import java.util.List;
import br.com.pakmatic.checklist.model.ResultadoComparacao;


/**
 * ✅ ResultadoComparacaoLote
 *
 * Representa um agrupador de resultados de múltiplas comparações feitas entre pares de colunas
 * de duas planilhas distintas (códigos e quantidades).
 *
 * Esse modelo é retornado pelo backend para o frontend, encapsulando vários objetos
 * ResultadoComparacao — um para cada par de comparação definido pelo usuário.
 */
public class ResultadoComparacaoLote {

    private List<ResultadoComparacao> resultados;

    public ResultadoComparacaoLote(List<ResultadoComparacao> resultados) {
        this.resultados = resultados;
    }

    public List<ResultadoComparacao> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoComparacao> resultados) {
        this.resultados = resultados;
    }
}
