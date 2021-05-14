package br.com.zup.cartao.model;

public class SolicitacaoInclusaoCarteira {

    private String email;

    private String carteira;

    @Deprecated
    public SolicitacaoInclusaoCarteira() { }

    public SolicitacaoInclusaoCarteira(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
