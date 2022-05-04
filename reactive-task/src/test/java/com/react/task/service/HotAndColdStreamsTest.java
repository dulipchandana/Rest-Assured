package com.react.task.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class HotAndColdStreamsTest {

    @Test
    void coldStreamTest(){
        var numbers = Flux.range(1,10);
        numbers.subscribe(integer -> System.out.println("Subscriber 1 "+ integer));
        numbers.subscribe(integer -> System.out.println("Subscriber 2 " + integer));
    }

}
