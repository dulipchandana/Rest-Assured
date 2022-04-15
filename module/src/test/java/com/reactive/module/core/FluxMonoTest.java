package com.reactive.module.core;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoTest {

    @Test
    void testMono(){
        //The publisher
        Mono<String> stringMono = Mono.just("skfskfsfjskfjskjfskjf" +
                "sfjskfjsklfksjfls").log();
        stringMono.subscribe(System.out::println);

    }

    @Test
    void testOnError(){
        Mono<?> stringMono = Mono.just("skfskfsfjskfjskjfskjf" +
                "sfjskfjsklfksjfls")
                .thenReturn(Mono.error(new RuntimeException("Runtime exception")))
                .log();
        stringMono.subscribe(System.out::println,e->System.out.println(e.getMessage()));
    }
    @Test
    void testFlux(){
        Flux<String> stringFlux = Flux.just("dad","adad","ddrwr","rwrw")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Flux runtime exception")))
                .concatWithValues("This Message cant be subscribe Because of the prior error")
                .log();
        stringFlux.subscribe(System.out::println,e-> System.out.println(e.getMessage()));
    }
}
