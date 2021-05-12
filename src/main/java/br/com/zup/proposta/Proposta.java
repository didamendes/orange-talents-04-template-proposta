package br.com.zup.proposta;

import br.com.zup.cartao.Cartao;
import br.com.zup.proposta.analise.ResultadoAnalise;
import br.com.zup.proposta.analise.ResultadoAnaliseClient;
import br.com.zup.proposta.analise.SolicitacaoAnalise;
import br.com.zup.proposta.analise.TipoResultadoSolicitacao;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "documento", nullable = false)
    private String documento;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao statusSolicitacao;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "cartao_id", referencedColumnName = "id")
    private Cartao cartao;

    @Deprecated
    public Proposta() {}

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusSolicitacao getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void analisar(ResultadoAnaliseClient resultadoAnaliseClient) {
        ResultadoAnalise resultadoAnalise = null;

        try {
            SolicitacaoAnalise solicitacaoAnalise = this.solicitacaoAnalise();
            resultadoAnalise = resultadoAnaliseClient.resultado(solicitacaoAnalise);
        } catch (Exception e) {

        } finally {
            if (resultadoAnalise != null) {
                statusSolicitacao = StatusSolicitacao.ELEGIVEL;
            } else {
                statusSolicitacao = StatusSolicitacao.NAO_ELEGIVEL;
            }
        }
    }

    public SolicitacaoAnalise solicitacaoAnalise() {
        return new SolicitacaoAnalise(documento, nome, id.toString());
    }

    public void adicionarCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
