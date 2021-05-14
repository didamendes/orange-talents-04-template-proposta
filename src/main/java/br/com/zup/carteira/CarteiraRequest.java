package br.com.zup.carteira;

import br.com.zup.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Tipo tipo;

    public String getEmail() {
        return email;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Carteira converter(Cartao cartao) {
        return new Carteira(email, tipo, cartao);
    }
}
