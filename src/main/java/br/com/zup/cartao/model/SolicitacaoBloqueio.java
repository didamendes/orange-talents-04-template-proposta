package br.com.zup.cartao.model;

public class SolicitacaoBloqueio {

    private String sistemaResponsavel;

    @Deprecated
    public SolicitacaoBloqueio() {}

    public SolicitacaoBloqueio(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
