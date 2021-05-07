package br.com.zup.cartao;

import br.com.zup.proposta.Proposta;
import br.com.zup.proposta.PropostaController;
import br.com.zup.proposta.PropostaRepository;
import br.com.zup.proposta.StatusSolicitacao;
import br.com.zup.proposta.analise.SolicitacaoAnalise;
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
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(CartaoScheduling.class);

    @Scheduled(cron = "${cron}")
    @Transactional
    public void associarCartao() {
            List<Proposta> propostas = propostaRepository.findAllByStatusSolicitacaoAndNumeroCartaoIsNull(StatusSolicitacao.ELEGIVEL);
            logger.info("Proposta encontradas: " + propostas.size() + " sem cartão gerado.");

            if (!propostas.isEmpty()) {
                propostas.stream().forEach(proposta -> {
                    try {
                        SolicitacaoAnalise solicitacaoAnalise = proposta.solicitacaoAnalise();
                        Cartao cartao = cartaoClient.salvar(solicitacaoAnalise);
                        proposta.adicionarCartao(cartao);
                    } catch (Exception e) {
                        logger.error("Error: " + e.getMessage());
                    }
                });
            }
    }

}
