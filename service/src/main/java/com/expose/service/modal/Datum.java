package com.expose.service.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Datum {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("employee_name")
    public String employeeName;
    @JsonProperty("employee_salary")
    public Integer employeeSalary;
    @JsonProperty("employee_age")
    public Integer employeeAge;
    @JsonProperty("profile_image")
    public String profileImage;
}
