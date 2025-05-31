package br.com.pakmatic.checklist.dto;

import java.util.List;

/**
 * DTO usado para retornar os resultados da varredura de quantidade de um item.
 */
public class ItemQuantidadeDTO {

    private String itemBuscado;
    private List<String> linhasEncontradas;
    private double somaTotal;

    public ItemQuantidadeDTO() {}

    public ItemQuantidadeDTO(String itemBuscado, List<String> linhasEncontradas, double somaTotal) {
        this.itemBuscado = itemBuscado;
        this.linhasEncontradas = linhasEncontradas;
        this.somaTotal = somaTotal;
    }

    public String getItemBuscado() {
        return itemBuscado;
    }

    public void setItemBuscado(String itemBuscado) {
        this.itemBuscado = itemBuscado;
    }

    public List<String> getLinhasEncontradas() {
        return linhasEncontradas;
    }

    public void setLinhasEncontradas(List<String> linhasEncontradas) {
        this.linhasEncontradas = linhasEncontradas;
    }

    public double getSomaTotal() {
        return somaTotal;
    }

    public void setSomaTotal(double somaTotal) {
        this.somaTotal = somaTotal;
    }
}
