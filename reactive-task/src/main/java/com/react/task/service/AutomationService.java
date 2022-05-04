package com.react.task.service;

import com.react.task.domin.Automation;
import com.react.task.domin.Schedule;
import com.react.task.exception.AutomationException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;


@Slf4j
/**
 * The first common service
 */
@AllArgsConstructor
public class AutomationService{

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TaskService taskService;

    public Flux<Automation> getAutomations () {
        var allSchedules = scheduleService.getScheduleList();
        return allSchedules
                .flatMap(schedule -> {
                    var tasks = taskService.getTaskListByTaskId(schedule.getTaskId())
                            .collectList();
                    return tasks.map(task -> Automation.builder().taskList(task)
                            .schedule(schedule).build());
                })
                .onErrorMap(throwable -> {
                    log.error("Exception {}", throwable);
                    return new AutomationException("Exception Occurred");
                })
                .retry(3) //retry in exception
                .log();
    }

    public Mono<Automation> getAutomationById(Long taskId) {
        var schedule = scheduleService.getScheduleByTaskId(taskId);
        var task = taskService.getTaskListByTaskId(taskId)
                .collectList();

        return schedule
                .zipWith(task,(s,t) -> Automation.builder().taskList(t).schedule(s).build());

    }

    public Flux<Automation> getAutomationsRetry () {
        //var retrySpec = getRetryBackoffSpec();
        var allSchedules = scheduleService.getScheduleList();
        return allSchedules
                .flatMap(schedule -> {
                    var tasks = taskService.getTaskListByTaskId(schedule.getTaskId())
                            .collectList();
                    return tasks.map(task -> Automation.builder().taskList(task)
                            .schedule(schedule).build());
                })
                .onErrorMap(throwable -> {
                    log.error("Exception {}", throwable);
                    return new AutomationException("Exception Occurred");
                })
                .retryWhen(getRetryBackoffSpec()) //retry in exception
                .log();
    }

    private RetryBackoffSpec getRetryBackoffSpec() {
        return Retry.backoff(
                        3,
                        Duration.ofMillis(1000)
                ).filter(throwable -> throwable instanceof AutomationException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure())
                );
    }

}
