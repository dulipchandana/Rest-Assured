package com.reactive.module.router;

import com.reactive.module.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    CustomerStreamHandler customerStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customer",customerStreamHandler::getCustomer)
                .GET("/router/customer/{cuId}",customerStreamHandler::getCustomerById)
                .POST("/router/customer",customerStreamHandler::saveCustomer)
                .build();
    }

}
