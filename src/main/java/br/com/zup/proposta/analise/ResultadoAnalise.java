package br.com.zup.proposta.analise;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ResultadoAnalise {

    private String documento;

    private String nome;

    private String idProposta;

    @Enumerated(EnumType.STRING)
    private TipoResultadoSolicitacao resultadoSolicitacao;

    @Deprecated
    private ResultadoAnalise() {}

    public ResultadoAnalise(String documento, String nome, String idProposta, TipoResultadoSolicitacao resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
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

    public TipoResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
