package com.react.task.service;

import com.react.task.domin.Automation;
import com.react.task.domin.Task;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.MethodNotAllowedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class TaskService{

    public Flux<Task> getTaskListByTaskId(Long taskId) {
        var taskList = List.of(
                Task.builder().taskId(123L).daskDescription("testD").scheduleOrder(2).build(),
                Task.builder().taskId(456L).daskDescription("testNG").scheduleOrder(4).build(),
                Task.builder().taskId(2535L).daskDescription("testLocust").scheduleOrder(5).build()
        );
        return Flux.fromIterable(taskList);
    }
}
