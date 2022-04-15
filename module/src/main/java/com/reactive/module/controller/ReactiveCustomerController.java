package com.reactive.module.controller;

import com.reactive.module.dto.ReactiveCustomerDto;
import com.reactive.module.service.ReactiveCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController()
@RequestMapping()
public class ReactiveCustomerController {

    @Autowired
    private ReactiveCustomerService reactiveCustomerService;

    @GetMapping(value = "/reactive" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReactiveCustomerDto> getReactiveCustomer(){
        return reactiveCustomerService.getReactiveCustomerList();
    }
}
