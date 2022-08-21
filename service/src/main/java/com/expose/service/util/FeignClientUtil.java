package com.expose.service.util;

import com.expose.service.modal.Employee;
import com.expose.service.modal.EmployeeModal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="feignClient" ,url="${remote.baseUrl}")
public interface FeignClientUtil {

    @GetMapping("/api/v1/employees")
    EmployeeModal getEmployeeModalList();

    @GetMapping("/api/v1/employee/{employeeId}")
    Employee getEmployeeModalByEmployeeId(@PathVariable(value = "employeeId")
                                          final Integer employeeId);
}
