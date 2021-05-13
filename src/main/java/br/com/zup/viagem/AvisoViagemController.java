package br.com.zup.viagem;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.exception.NenhumRegistroEncontado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/avisos-viagens")
public class AvisoViagemController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;
    
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> salvar(@PathVariable String id,
                                    @Valid @RequestBody AvisoViagemRequest avisoViagemRequest,
                                    @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                    HttpServletRequest httpServletRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartão não encontrado"));
        AvisoViagem avisoViagem = avisoViagemRequest.converter(userAgent, httpServletRequest.getRemoteAddr(), cartao);

        avisoViagemRepository.save(avisoViagem);
        return ResponseEntity.ok(new AvisoViagemResponse(avisoViagem));
    }

}
