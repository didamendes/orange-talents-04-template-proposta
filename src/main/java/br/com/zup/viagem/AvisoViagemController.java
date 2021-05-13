package br.com.zup.viagem;

import br.com.zup.bloqueio.BloqueioController;
import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoClient;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.exception.NenhumRegistroEncontado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CartaoClient cartaoClient;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
    
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> salvar(@PathVariable String id,
                                    @Valid @RequestBody SolicitacaoAvisoViagem solicitacaoAvisoViagem,
                                    @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                    HttpServletRequest httpServletRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartão não encontrado"));
        AvisoViagem avisoViagem = solicitacaoAvisoViagem.converter(userAgent, httpServletRequest.getRemoteAddr(), cartao);

        try {
            cartaoClient.viagem(cartao.getId(), solicitacaoAvisoViagem);
            avisoViagemRepository.save(avisoViagem);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(new AvisoViagemResponse(avisoViagem));
    }

}
