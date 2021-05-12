package br.com.zup.bloqueio;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.exception.NenhumRegistroEncontado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioResopitory bloqueioResopitory;

    @PostMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<?> bloquear(@PathVariable String id,
                                      @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                      HttpServletRequest httpServletRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartão não encontrado"));

        Bloqueio bloqueio = new Bloqueio(httpServletRequest.getRemoteAddr(), userAgent, cartao);

        if (cartao.isBloqueado()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        cartao.bloquear();
        bloqueioResopitory.save(bloqueio);

        return ResponseEntity.ok().build();
    }

}
