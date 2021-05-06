package br.com.zup.proposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @PostMapping
    public ResponseEntity<PropostaResponse> salvar(@Valid @RequestBody PropostaRequest propostaRequest) {
        logger.info(" Proposta do cliente= {} com salario= {} recebida ", propostaRequest.getNome(), propostaRequest.getSalario());
        Proposta proposta = propostaRequest.converter();
        boolean exists = propostaRepository.existsByDocumento(proposta.getDocumento());

        if (exists) {
            logger.error("Proposta existente");
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }

        propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new PropostaResponse(proposta));
    }

}
