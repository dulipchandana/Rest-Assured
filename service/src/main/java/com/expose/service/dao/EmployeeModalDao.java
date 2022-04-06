package com.expose.service.dao;

import com.expose.service.modal.EmployeeModal;
import com.expose.service.util.FeignClientUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeModalDao {

    @Autowired
    FeignClientUtil feignClientUtil;

    public Optional<EmployeeModal> getEmployeeList(){
        return Optional.of(feignClientUtil.getEmployeeModalList());
    }
}
