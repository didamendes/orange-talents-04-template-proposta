package br.com.zup.bloqueio;

import java.time.OffsetDateTime;

public class BloqueioResponse {

    private Long id;

    private OffsetDateTime bloqueadoEm;

    private String ipCliente;

    private String userAgent;

    private String cartao;

    public BloqueioResponse(Bloqueio bloqueio) {
        this.id = bloqueio.getId();
        this.bloqueadoEm = bloqueio.getBloqueadoEm();
        this.ipCliente = bloqueio.getIpCliente();
        this.userAgent = bloqueio.getUserAgent();
        this.cartao = bloqueio.getCartao().getId();
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getCartao() {
        return cartao;
    }
}
