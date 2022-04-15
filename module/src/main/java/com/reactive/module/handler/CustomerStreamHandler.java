package com.reactive.module.handler;

import com.reactive.module.dao.ReactiveCustomerDao;
import com.reactive.module.dto.ReactiveCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class CustomerStreamHandler {

    @Autowired
    ReactiveCustomerDao reactiveCustomerDao;

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(reactiveCustomerDao.getCustomer(),
                ReactiveCustomerDto.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request){

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(reactiveCustomerDao.getCustomer().
                        filter(c -> c.getId() == Integer.valueOf(
                                request.pathVariable("cuId"))),
                        ReactiveCustomerDto.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<ReactiveCustomerDto> reactiveCustomerDtoMono =
                request.bodyToMono(ReactiveCustomerDto.class);
        /*reactiveCustomerDtoMono.map(dto ->
                System.out::println());*/
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(reactiveCustomerDtoMono,
                        ReactiveCustomerDto.class);

    }
}
