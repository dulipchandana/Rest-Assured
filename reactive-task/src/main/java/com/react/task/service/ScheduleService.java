package com.react.task.service;

import com.react.task.domin.Schedule;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ScheduleService {

    public Flux<Schedule> getScheduleList(){

        var scheduleList = List.of(
                Schedule.builder().Id(123L).taskId(8L).description("nodeSCH").build(),
                Schedule.builder().Id(456L).taskId(7L).description("pySCH").build(),
                Schedule.builder().Id(124L).taskId(6L).description("jsSCH").build()
        );
        return Flux.fromIterable(scheduleList);
    }

    public Mono<Schedule> getScheduleByTaskId(Long taskId){
        return Mono.just(Schedule.builder().Id(124L).taskId(6L).description("jsSCH").build());
    }
}
