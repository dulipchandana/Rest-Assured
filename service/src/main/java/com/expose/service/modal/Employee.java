package com.expose.service.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class Employee {

    @JsonProperty("status")
    public String status;
    @JsonProperty("data")
    public Datum data = null;
    @JsonProperty("message")
    public String message;
}
