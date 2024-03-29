package com.expose.service.controler;

import com.expose.service.modal.Employee;
import com.expose.service.service.CommonModelService;
import com.expose.service.dao.EmployeeModalDao;
import com.expose.service.modal.CommonModal;
import com.expose.service.modal.EmployeeModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/modalmgt")
public class GenaricControler {

    @Autowired
    private CommonModelService commonModelService;

    @Autowired
    private EmployeeModalDao empModalDao;

    @RequestMapping(
            value = "/modals",
            method = GET,
            produces = "application/json")
    public ResponseEntity<List<CommonModal>> getModal() {
        return new ResponseEntity<>(commonModelService.getCommonModalList().get(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/modal/create",
            method = POST,
            produces = "application/json")
    public ResponseEntity<CommonModal> saveModal(@RequestBody CommonModal commonModal) {
        return new ResponseEntity<CommonModal>(commonModelService.saveCommonModal(commonModal).get(), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/auth/modals",
            method = GET,
            produces = "application/json")
    public ResponseEntity<List<CommonModal>> getAuthModal() {
        return new ResponseEntity<>(commonModelService.getCommonModalList().get(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/employee/modals",
            method = GET,
            produces = "application/json")
    public ResponseEntity<EmployeeModal> getEmpModal() {
        return new ResponseEntity<>(empModalDao.getEmployeeList().get(), HttpStatus.OK);

    }

    @RequestMapping(
            value = "/employee/modal/{employeeId}",
            method = GET,
            produces = "application/json")
    public ResponseEntity<Employee> getEmpModalById(@PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(empModalDao.getEmployeeModalByEmployeeId(employeeId).get(), HttpStatus.OK);

    }
}
