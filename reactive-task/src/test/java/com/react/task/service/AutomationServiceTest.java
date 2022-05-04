package com.react.task.service;

import com.react.task.ReactiveTaskApplication;
import com.react.task.exception.AutomationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AutomationServiceTest {

    @Mock
    ScheduleService scheduleService;

    @Mock
    TaskService taskService;

    @InjectMocks
    private AutomationService automationService;

    @BeforeEach
    void setupService(){
        Mockito.when(scheduleService.getScheduleByTaskId(any())).thenCallRealMethod();
        Mockito.when(scheduleService.getScheduleList()).thenCallRealMethod();
        Mockito.when(taskService.getTaskListByTaskId(any())).thenCallRealMethod();
    }

    @Test
    void getAutomations() {
        var automations = automationService.getAutomations();
        StepVerifier.create(automations)
                .assertNext( automation -> {
                    assertEquals(123L, automation.getSchedule().getId());
                    assertEquals(3 , automation.getTaskList().size());
                })
                .assertNext( automation -> {
                    assertEquals(456L, automation.getSchedule().getId());
                    assertEquals(3 , automation.getTaskList().size());
                })
                .assertNext( automation -> {
                    assertEquals(124L, automation.getSchedule().getId());
                    assertEquals(3 , automation.getTaskList().size());
                })
                .verifyComplete();
    }

    @Test
    void getAutomationById() {
        var automation = automationService.getAutomationById(1L);
        StepVerifier.create(automation)
                .assertNext( a -> {
                    assertEquals(124L, a.getSchedule().getId());
                    assertEquals(3 , a.getTaskList().size());
                }).verifyComplete();
    }

    @Test
    void getAutomationsException() {
        Mockito.when(taskService.getTaskListByTaskId(any()))
                .thenThrow(new IllegalStateException("Exception on task list "));
        var automations = automationService.getAutomations();

        StepVerifier.create(automations)
                .expectError(AutomationException.class)
                .verify();
    }

    @Test
    void getAutomationsRetryException() {
        Mockito.when(taskService.getTaskListByTaskId(any()))
                .thenThrow(new IllegalStateException("Exception on task list "));
        var automations = automationService.getAutomationsRetry();

        StepVerifier.create(automations)
                .expectError(AutomationException.class)
                .verify();
    }
}