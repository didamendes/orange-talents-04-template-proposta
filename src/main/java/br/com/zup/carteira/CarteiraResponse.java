package br.com.zup.carteira;

import java.time.OffsetDateTime;

public class CarteiraResponse {

    private Long id;

    private String email;

    private OffsetDateTime associadaEm;

    private Tipo emissor;

    private String cartao;

    public CarteiraResponse(Carteira carteira) {
        this.id = carteira.getId();
        this.email = carteira.getEmail();
        this.associadaEm = carteira.getAssociadaEm();
        this.emissor = carteira.getEmissor();
        this.cartao = carteira.getCartao().getId();
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

    public String getCartao() {
        return cartao;
    }
}
