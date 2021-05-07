package br.com.zup.proposta.analise;

public class SolicitacaoAnalise {

    private String documento;

    private String nome;

    private String idProposta;

    @Deprecated
    public SolicitacaoAnalise() {}

    public SolicitacaoAnalise(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
