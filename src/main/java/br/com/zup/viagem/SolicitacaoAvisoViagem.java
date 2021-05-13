package br.com.zup.viagem;

import br.com.zup.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class SolicitacaoAvisoViagem {

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private LocalDate validoAte;

    @NotBlank
    private String destino;

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public AvisoViagem converter(String userAgent, String remoteAddr, Cartao cartao) {
        return new AvisoViagem(validoAte, destino, remoteAddr, userAgent, cartao);
    }
}
