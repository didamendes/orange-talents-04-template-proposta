package br.com.zup.cartao;

import br.com.zup.cartao.model.*;
import br.com.zup.viagem.SolicitacaoAvisoViagem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "cartao", url = "${feign.url.cartao}")
public interface CartaoClient {

    @RequestMapping(method = GET, value = "/cartoes", produces = MediaType.APPLICATION_JSON_VALUE)
    Cartao consultar(@RequestParam("idProposta") String idProposta);

    @RequestMapping(method = POST, value ="/cartoes/{id}/bloqueios", produces = MediaType.APPLICATION_JSON_VALUE)
    ResultadoBloqueio bloqueio(@PathVariable("id") String id, @RequestBody SolicitacaoBloqueio solicitacaoBloqueio);

    @RequestMapping(method = POST, value = "/cartoes/{id}/avisos", produces = MediaType.APPLICATION_JSON_VALUE)
    ResultadoAvisoViagem viagem(@PathVariable("id") String id, @RequestBody SolicitacaoAvisoViagem solicitacaoAvisoViagem);

    @RequestMapping(method = POST, value = "/cartoes/{id}/carteiras", produces = MediaType.APPLICATION_JSON_VALUE)
    ResultadoCarteira associar(@PathVariable("id") String id, @RequestBody SolicitacaoInclusaoCarteira solicitacaoInclusaoCarteira);

}
