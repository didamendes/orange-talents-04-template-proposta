package br.com.zup.biometria;

import br.com.zup.cartao.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Base64;

public class BiometriaRequest {

    @NotBlank
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$", message = "Biometria invalido. Deve ser base64")
    public String biometria;

    public String getBiometria() {
        return biometria;
    }

    public Biometria converter(Cartao cartao) {
        return new Biometria(Base64.getDecoder().decode(biometria), cartao);
    }

}
