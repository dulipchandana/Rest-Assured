package com.react.task.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FluxMonoExecuterTest {

    @InjectMocks
    FluxMonoExecuter fluxMonoExecuter;

    @Test
    void testGetConfig(){
        fluxMonoExecuter.getConfig().subscribe(s ->{
            System.out.println(s);
        });
        StepVerifier.create(fluxMonoExecuter.getConfig())
                .expectNext("ff","tt","ll")
                .verifyComplete();
    }

    @Test
    void getConfigFlatMap() {
        fluxMonoExecuter.getConfigFlatMap().subscribe(s -> {
                    System.out.println(s);
                });
        StepVerifier.create(fluxMonoExecuter.getConfigFlatMap())
                .expectNextCount(14)
                .verifyComplete();
    }

    @Test
    void getConfigMonoMap() {
        StepVerifier.create(fluxMonoExecuter.getConfigMonoMap())
                //.expectNextCount(1)
                .expectNext(List.of("H","K","S","Y"))
                .verifyComplete();
    }

    @Test
    void getConfigFlatMapMany() {
        StepVerifier.create(fluxMonoExecuter.getConfigFlatMapMany())
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void getConfigZip() {

        fluxMonoExecuter.getConfigZip().subscribe(s -> {
            System.out.println(s);
                }
        );
        StepVerifier.create(fluxMonoExecuter.getConfigZip())
                .expectNextCount(2).verifyComplete();
    }
}
