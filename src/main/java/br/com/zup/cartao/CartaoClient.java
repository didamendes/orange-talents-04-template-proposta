package br.com.zup.cartao;

import br.com.zup.proposta.analise.SolicitacaoAnalise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "cartao", url = "${feign.url.cartao}")
public interface CartaoClient {

    @RequestMapping(method = POST, value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
    Cartao salvar(@RequestBody SolicitacaoAnalise solicitacaoAnalise);

}
