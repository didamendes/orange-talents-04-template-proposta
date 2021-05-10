package br.com.zup.biometria;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.exception.NenhumRegistroEncontado;
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

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> salvar(@Valid @RequestBody BiometriaRequest biometriaRequest,
                                    @NotNull @PathVariable String id) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartao não encontrado"));
        Biometria bio = biometriaRequest.converter(cartao);

        biometriaRepository.save(bio);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(bio.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
