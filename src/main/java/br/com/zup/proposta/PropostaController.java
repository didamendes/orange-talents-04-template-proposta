package br.com.zup.proposta;

import br.com.zup.proposta.analise.ResultadoAnaliseClient;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Validated
@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final Tracer tracer;

    private PropostaRepository propostaRepository;

    private ResultadoAnaliseClient resultadoAnaliseClient;

    @Autowired
    public PropostaController(Tracer tracer, PropostaRepository propostaRepository, ResultadoAnaliseClient resultadoAnaliseClient) {
        this.tracer = tracer;
        this.propostaRepository = propostaRepository;
        this.resultadoAnaliseClient = resultadoAnaliseClient;
    }

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @GetMapping(path = "/{id}")
    public ResponseEntity<PropostaResponse> acompanharProposta(@NotNull @PathVariable Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);

        if (proposta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PropostaResponse(proposta.get()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PropostaResponse> salvar(@Valid @RequestBody PropostaRequest propostaRequest) {
        Span span = tracer.activeSpan();
        logger.info(" Proposta do cliente= {} com salario= {} recebida ", propostaRequest.getNome(), propostaRequest.getSalario());
        Proposta proposta = propostaRequest.converter();
        boolean exists = propostaRepository.existsByDocumento(proposta.getDocumento());

        if (exists) {
            logger.error("Proposta existente");
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }

        propostaRepository.save(proposta);
        proposta.analisar(resultadoAnaliseClient);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        span.setTag("Nova Proposta", "diogo.souza@zup.com.br");
        span.log("Teste de LOG com Jaeger no Salvar Proposta !!! ");
        return ResponseEntity.created(uri).body(new PropostaResponse(proposta));
    }

}
