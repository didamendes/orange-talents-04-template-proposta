package br.com.zup.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "solicitacao", url = "${feign.url.solicitacao}")
public interface ResultadoAnaliseClient {

    @RequestMapping(method = POST, value = "/solicitacao", produces = MediaType.APPLICATION_JSON_VALUE)
    ResultadoAnalise resultado(@RequestBody SolicitacaoAnalise solicitacaoAnalise);

}
