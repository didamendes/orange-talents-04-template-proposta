package br.com.zup.cartao;

import br.com.zup.proposta.Proposta;
import br.com.zup.proposta.PropostaRepository;
import br.com.zup.proposta.StatusSolicitacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CartaoScheduling {

    @Autowired
    private CartaoClient cartaoClient;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(CartaoScheduling.class);

//    @Scheduled(cron = "${cron}")
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 5 * 60 * 1000)
    @Transactional
    public void associarCartao() {
            List<Proposta> propostas = propostaRepository.findAllByStatusSolicitacaoAndCartaoIsNull(StatusSolicitacao.ELEGIVEL);
            logger.info("Proposta encontradas: " + propostas.size() + " sem cartão gerado.");

            if (!propostas.isEmpty()) {
                propostas.stream().forEach(proposta -> {
                    try {
                        Cartao cartao = cartaoClient.consultar(proposta.getId().toString());
                        proposta.adicionarCartao(cartao);
                        cartaoRepository.save(cartao);
                        propostaRepository.save(proposta);
                    } catch (Exception e) {
                        logger.error("Error: " + e.getMessage());
                    }
                });
            }
    }

}
