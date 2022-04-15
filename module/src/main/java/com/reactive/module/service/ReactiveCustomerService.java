package com.reactive.module.service;

import com.reactive.module.dao.ReactiveCustomerDao;
import com.reactive.module.dto.ReactiveCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReactiveCustomerService {

    @Autowired
    private ReactiveCustomerDao reactiveCustomerDao;

    public Flux<ReactiveCustomerDto> getReactiveCustomerList(){
        return reactiveCustomerDao.getCustomer();
    }
}
