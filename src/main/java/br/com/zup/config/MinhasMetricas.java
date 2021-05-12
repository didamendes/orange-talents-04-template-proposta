package br.com.zup.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MinhasMetricas {

    private final MeterRegistry meterRegistry;

    private final Collection<String> strings = new ArrayList<>();

    public MinhasMetricas(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meuContador();
        meuTimer();
        criarGauge();
    }

    public void meuContador() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);

        contadorDePropostasCriadas.increment();
    }

    public void meuTimer() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
        timerConsultarProposta.record(() -> {
            // Método da sua operação
            meuContador();
        });
    }

    public void criarGauge() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        this.meterRegistry.gauge("meu_gauge", tags, strings, Collection::size);
    }

}
