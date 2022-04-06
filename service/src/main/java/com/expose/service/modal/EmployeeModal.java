package com.expose.service.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class EmployeeModal {

    /*{"id":1,"employee_name":"Tiger Nixon","employee_salary":320800,"employee_age":61,"profile_image":""}*/
    @JsonProperty("status")
    public String status;
    @JsonProperty("data")
    public List<Datum> data = null;
    @JsonProperty("message")
    public String message;




}
