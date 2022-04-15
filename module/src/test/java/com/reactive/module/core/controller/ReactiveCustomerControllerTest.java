package com.reactive.module.core.controller;

import com.reactive.module.controller.ReactiveCustomerController;
import com.reactive.module.dao.ReactiveCustomerDao;
import com.reactive.module.dto.ReactiveCustomerDto;
import com.reactive.module.service.ReactiveCustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReactiveCustomerController.class)
public class ReactiveCustomerControllerTest {

    @MockBean
    private ReactiveCustomerService reactiveCustomerService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getReactiveCustomerTest(){
        Mockito.when(reactiveCustomerService.getReactiveCustomerList()).thenReturn(
                Flux.just(new ReactiveCustomerDto(1,"test-customer"))
        );
        webTestClient.get()
                .uri("/reactive")
                .accept(MediaType.ALL)
                .header("Content-Type", "text/event-stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReactiveCustomerDto.class);
    }
}
