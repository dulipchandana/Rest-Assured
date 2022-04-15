package com.reactive.module.util;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class SequenceGeneratorTest {

    @Test
    public void whenGeneratingNumbersWithTuplesState_thenFibonacciSequenceIsProduced() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Flux<Integer> fibonacciFlux = sequenceGenerator.generateFibonacciWithTuples().take(5);

        StepVerifier.create(fibonacciFlux)
                .expectNext(0, 1, 1, 2, 3)
                .expectComplete()
                .verify();
    }

}