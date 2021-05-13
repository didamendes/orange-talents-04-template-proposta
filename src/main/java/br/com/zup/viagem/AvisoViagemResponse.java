package br.com.zup.viagem;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class AvisoViagemResponse {

    private Long id;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private LocalDate validoAte;

    private String destino;

    private String ipClient;

    private String userAgent;

    private LocalDateTime dataCriacao;

    private String cartao;

    public AvisoViagemResponse(AvisoViagem avisoViagem) {
        this.id = avisoViagem.getId();
        this.validoAte = avisoViagem.getValidoAte();
        this.destino = avisoViagem.getDestino();
        this.ipClient = avisoViagem.getIpClient();
        this.userAgent = avisoViagem.getUserAgent();
        this.dataCriacao = avisoViagem.getDataCriacao();
        this.cartao = avisoViagem.getCartao().getId();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getCartao() {
        return cartao;
    }
}
