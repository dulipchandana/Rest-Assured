package com.expose.service.advisor;

import com.expose.service.modal.EmployeeModal;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(
            final FeignException ex, final WebRequest request) {
        EmployeeModal employeeModal
                = new EmployeeModal(HttpStatus.valueOf(ex.status()).toString(),ex.getMessage());

        return new ResponseEntity<>(employeeModal, HttpStatus.valueOf(ex.status()));
    }
}
