package br.com.zup.bloqueio;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoClient;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.cartao.model.SolicitacaoBloqueio;
import br.com.zup.exception.NenhumRegistroEncontado;
import br.com.zup.proposta.PropostaController;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Tracer tracer;

    private CartaoClient cartaoClient;

    private CartaoRepository cartaoRepository;

    private BloqueioResopitory bloqueioResopitory;

    @Autowired
    public BloqueioController(Tracer tracer, CartaoClient cartaoClient, CartaoRepository cartaoRepository, BloqueioResopitory bloqueioResopitory) {
        this.tracer = tracer;
        this.cartaoClient = cartaoClient;
        this.cartaoRepository = cartaoRepository;
        this.bloqueioResopitory = bloqueioResopitory;
    }

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PostMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<?> bloquear(@PathVariable String id,
                                      @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                      HttpServletRequest httpServletRequest) {
        Span span = tracer.activeSpan();
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartão não encontrado"));

        Bloqueio bloqueio = new Bloqueio(httpServletRequest.getRemoteAddr(), userAgent, cartao);

        if (cartao.isBloqueado()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            cartaoClient.bloqueio(cartao.getId(), new SolicitacaoBloqueio("Proposta"));
            cartao.bloquear();
            bloqueioResopitory.save(bloqueio);

            span.log("LOG com Jasper Bloqueio Cartão ! ");
            return ResponseEntity.ok(new BloqueioResponse(bloqueio));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}
