package br.com.zup.cartao;

import br.com.zup.biometria.Biometria;
import br.com.zup.bloqueio.Bloqueio;
import br.com.zup.proposta.Proposta;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
}
