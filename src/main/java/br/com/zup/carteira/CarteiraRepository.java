package br.com.zup.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByEmailAndEmissor(String email, Tipo emissor);

}
