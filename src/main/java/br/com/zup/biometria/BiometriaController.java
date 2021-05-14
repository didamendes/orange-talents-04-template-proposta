package br.com.zup.biometria;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.exception.NenhumRegistroEncontado;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/biometrias")
public class BiometriaController {

    private final Tracer tracer;

    private CartaoRepository cartaoRepository;

    private BiometriaRepository biometriaRepository;

    @Autowired
    public BiometriaController(Tracer tracer, CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.tracer = tracer;
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> salvar(@Valid @RequestBody BiometriaRequest biometriaRequest,
                                    @NotNull @PathVariable String id) {
        Span span = tracer.activeSpan();
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartao não encontrado"));
        Biometria bio = biometriaRequest.converter(cartao);

        biometriaRepository.save(bio);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(bio.getId()).toUri();
        span.log("LOG com Jaeger cadastrar Biometria");
        return ResponseEntity.created(uri).build();
    }

}
