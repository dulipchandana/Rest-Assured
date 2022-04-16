package com.react.task.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FluxMonoHandleTest {

    @InjectMocks
    private FluxMonoHandle fluxMonoHandle;

    @Test
    void onErrorHandle() {

        StepVerifier.create(fluxMonoHandle.onErrorHandle())
                .expectNext("dsd","dsdtr","on error")
                .verifyComplete();
    }

    @Test
    void onErrorContinueHandle() {
        StepVerifier.create(fluxMonoHandle.onErrorContinueHandle().log())
                .expectNext("TE","YU")
                        .verifyComplete();
    }

    @Test
    void onErrorMapError() {
        StepVerifier.create(fluxMonoHandle.onErrorMapError().log())
                .expectNext("TE")
                .expectError(IllegalStateException.class)
                .verify();
    }
    
    @Test
    void doOnErrorHandle() {
        StepVerifier.create(fluxMonoHandle.doOnErrorHandle().log())
                .expectNext("TE")
                .expectError(RuntimeException.class)
                .verify();
    }
}