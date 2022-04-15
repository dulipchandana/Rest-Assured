package com.reactive.module.dao;

import com.reactive.module.dto.ReactiveCustomerDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class ReactiveCustomerDao {

    public Flux<ReactiveCustomerDto> getCustomer() {
        return Flux.range(1, 50)
                .delayElements(Duration.ofMillis(1000))
                .doOnNext(i -> System.out.println("processing count ->" + i))
                .map(i -> new ReactiveCustomerDto(i, "Reactive cus" + i));

    }
}
