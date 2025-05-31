package br.com.pakmatic.checklist.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa o resultado da varredura de um item específico dentro de uma estrutura,
 * considerando o número de aparições e a quantidade total encontrada.
 */
public class ResultadoItemQuantidade {

    private String itemBuscado;
    private int totalOcorrencias;
    private double somaQuantidade;
    private List<OcorrenciaItem> ocorrencias;

    public ResultadoItemQuantidade() {
    }

    // ✅ Construtor adicional necessário
    public ResultadoItemQuantidade(String itemBuscado) {
        this.itemBuscado = itemBuscado;
        this.totalOcorrencias = 0;
        this.somaQuantidade = 0.0;
        this.ocorrencias = new ArrayList<>();
    }

    public ResultadoItemQuantidade(String itemBuscado, int totalOcorrencias, double somaQuantidade, List<OcorrenciaItem> ocorrencias) {
        this.itemBuscado = itemBuscado;
        this.totalOcorrencias = totalOcorrencias;
        this.somaQuantidade = somaQuantidade;
        this.ocorrencias = ocorrencias;
    }

    public String getItemBuscado() {
        return itemBuscado;
    }

    public void setItemBuscado(String itemBuscado) {
        this.itemBuscado = itemBuscado;
    }

    public int getTotalOcorrencias() {
        return totalOcorrencias;
    }

    public void setTotalOcorrencias(int totalOcorrencias) {
        this.totalOcorrencias = totalOcorrencias;
    }

    public double getSomaQuantidade() {
        return somaQuantidade;
    }

    public void setSomaQuantidade(double somaQuantidade) {
        this.somaQuantidade = somaQuantidade;
    }

    public List<OcorrenciaItem> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<OcorrenciaItem> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public void adicionarLinha(int numeroLinha, String linhaCompleta, Double quantidade) {
        if (quantidade == null) {
            quantidade = 0.0;
        }
        if (this.ocorrencias == null) {
            this.ocorrencias = new ArrayList<>();
        }

        OcorrenciaItem nova = new OcorrenciaItem(numeroLinha, linhaCompleta, quantidade);
        this.ocorrencias.add(nova);
        this.totalOcorrencias++;
        this.somaQuantidade += quantidade;
    }

    /**
     * Representa uma única linha onde o item foi encontrado, com a linha original e a quantidade associada.
     */
    public static class OcorrenciaItem {
        private int numeroLinha;
        private String linhaCompleta;
        private double quantidadeEncontrada;

        public OcorrenciaItem() {}

        public OcorrenciaItem(int numeroLinha, String linhaCompleta, double quantidadeEncontrada) {
            this.numeroLinha = numeroLinha;
            this.linhaCompleta = linhaCompleta;
            this.quantidadeEncontrada = quantidadeEncontrada;
        }

        public int getNumeroLinha() {
            return numeroLinha;
        }

        public void setNumeroLinha(int numeroLinha) {
            this.numeroLinha = numeroLinha;
        }

        public String getLinhaCompleta() {
            return linhaCompleta;
        }

        public void setLinhaCompleta(String linhaCompleta) {
            this.linhaCompleta = linhaCompleta;
        }

        public double getQuantidadeEncontrada() {
            return quantidadeEncontrada;
        }

        public void setQuantidadeEncontrada(double quantidadeEncontrada) {
            this.quantidadeEncontrada = quantidadeEncontrada;
        }
    }
}
