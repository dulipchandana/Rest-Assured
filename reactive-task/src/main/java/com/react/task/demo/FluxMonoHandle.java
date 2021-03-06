package com.react.task.demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.util.Locale;

@Service
public class FluxMonoHandle {

    public Flux<String> onErrorHandle(){
        return Flux.just("dsd","dsdtr")
                .concatWith(
                        Flux.error(new RuntimeException("Error on concat"))
                )
                .concatWith(Flux.just("fksmdaji"))
                .onErrorReturn("on error")
                .log();
    }

    public Flux<String> onErrorContinueHandle(){
        return Flux.just("te","me","yu")
                .map(s -> {
                    if (s.equals("me")){
                       throw new RuntimeException("The exception") ;
                    } else {
                        return s.toUpperCase(Locale.ROOT);
                    }
                })
                .onErrorContinue((e,s) -> {
                    System.out.println(e);
                    System.out.println(s);
                });
    }

    public Flux<String> onErrorMapError(){
        return Flux.just("te","me","yu")
                .map(s -> {
                    if (s.equals("me")){
                        throw new RuntimeException("The exception") ;
                    } else {
                        return s.toUpperCase(Locale.ROOT);
                    }
                })
                .onErrorMap(throwable -> {
                    System.out.println("error -> "+ throwable.getMessage());
                    return new IllegalStateException("return error");
                        });
    }

    public Flux<String> doOnErrorHandle(){
        //Hooks.onOperatorDebug();
        return Flux.just("te","me","yu")
                .checkpoint("check point 2")
                .map(s -> {
                    if (s.equals("me")){
                        throw new RuntimeException("The exception") ;
                    } else {
                        return s.toUpperCase(Locale.ROOT);
                    }
                })
                .checkpoint("check point 1 ")
                .doOnError(throwable -> {
                    System.out.println("error -> "+ throwable.getMessage());
                });
    }
}
