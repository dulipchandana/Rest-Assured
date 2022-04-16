package com.react.task.demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Service
public class FluxMonoExecuter {

    public Flux<String> getConfig(){
        return Flux.fromIterable(List.of("ff","tt","ll"));
    }

    public Flux<String> getConfigFlatMap(){
        return Flux.fromIterable(List.of("ffftt","ttfss","llsd"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }

    public Mono<List<String>> getConfigMonoMap(){
        return Mono.just("HKSY")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> getConfigFlatMapMany() {
        return Mono.just("HKSY")
                .flatMapMany(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> getConfigZip(){
        return Flux.zip(Flux.just("zip","mid"),Flux.just("12","f87"),
                (con1,con2 )-> con1.toUpperCase(Locale.ROOT)+con2.toUpperCase(Locale.ROOT))
                .log();
    }
}
