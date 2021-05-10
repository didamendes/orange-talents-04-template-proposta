package br.com.zup.cartao;

import br.com.zup.biometria.Biometria;
import br.com.zup.proposta.Proposta;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias;

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
}
