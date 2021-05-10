package br.com.zup.cartao;

import br.com.zup.proposta.analise.SolicitacaoAnalise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "cartao", url = "${feign.url.cartao}")
public interface CartaoClient {

    @RequestMapping(method = GET, value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
    Cartao consultar(@RequestParam("idProposta") String idProposta);

}
