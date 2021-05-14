package br.com.zup.carteira;

import br.com.zup.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @CreationTimestamp
    private OffsetDateTime associadaEm;

    @Enumerated(EnumType.STRING)
    private Tipo emissor;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    @Deprecated
    public Carteira() {}

    public Carteira(String email, Tipo emissor, Cartao cartao) {
        this.email = email;
        this.emissor = emissor;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public OffsetDateTime getAssociadaEm() {
        return associadaEm;
    }

    public Tipo getEmissor() {
        return emissor;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
