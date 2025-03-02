package br.com.zup.cartao;

import br.com.zup.biometria.Biometria;
import br.com.zup.bloqueio.Bloqueio;
import br.com.zup.carteira.Carteira;
import br.com.zup.carteira.Tipo;
import br.com.zup.proposta.Proposta;
import br.com.zup.viagem.AvisoViagem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cartao {

    @Id
    private String id;

    private String titular;

    private String idProposta;

    private LocalDateTime emitidoEm;

    private Integer limite;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias;

    @OneToMany(mappedBy = "cartao")
    private Set<Bloqueio> bloqueios;

    @OneToMany(mappedBy = "cartao")
    private Set<AvisoViagem> avisoViagems;

    @OneToMany(mappedBy = "cartao")
    private Set<Carteira> carteiras;

    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Integer getLimite() {
        return limite;
    }

    public boolean isBloqueado() {
        return this.status.equals(Status.BLOQUEADO);
    }

    public void bloquear() {
        this.status = Status.BLOQUEADO;
    }

    public boolean isAssociado(Tipo tipo) {
        Set<Carteira> carteirasTipo = carteiras.stream().filter(carteira -> carteira.getEmissor().equals(tipo)).collect(Collectors.toSet());

        return carteirasTipo.size() > BigDecimal.ZERO.longValue();
    }
}
