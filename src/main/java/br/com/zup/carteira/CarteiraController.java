package br.com.zup.carteira;

import br.com.zup.cartao.Cartao;
import br.com.zup.cartao.CartaoClient;
import br.com.zup.cartao.CartaoRepository;
import br.com.zup.cartao.model.SolicitacaoInclusaoCarteira;
import br.com.zup.exception.NenhumRegistroEncontado;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final Tracer tracer;

    private CartaoClient cartaoClient;

    private CartaoRepository cartaoRepository;

    private CarteiraRepository carteiraRepository;

    public CarteiraController(Tracer tracer, CartaoClient cartaoClient, CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository) {
        this.tracer = tracer;
        this.cartaoClient = cartaoClient;
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> associar(@PathVariable String id, @Valid @RequestBody CarteiraRequest carteiraRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NenhumRegistroEncontado("Cartão não encontrado"));

        if (carteiraRepository.existsByEmailAndEmissor(carteiraRequest.getEmail(), carteiraRequest.getTipo()) ||
                cartao.isAssociado(carteiraRequest.getTipo())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            Carteira carteira = carteiraRequest.converter(cartao);
            cartaoClient.associar(cartao.getId(), new SolicitacaoInclusaoCarteira(carteira.getEmail(), carteira.getEmissor().name()));
            carteiraRepository.save(carteira);
            return ResponseEntity.ok(new CarteiraResponse(carteira));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}
