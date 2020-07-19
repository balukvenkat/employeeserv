package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.findings.Errors;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.model.EmployeeTable;
import com.paypal.bfs.test.employeeserv.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private InputValidator inputValidator;

    private EmployeeResourceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, InputValidator inputValidator) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.inputValidator = inputValidator;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

        Optional<EmployeeTable> et = employeeRepository.findById(Integer.valueOf(id));

        if(et.isPresent()){
            return new ResponseEntity<>(employeeMapper.forAPI(et.get()),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity createEmployee(Employee employeeRequest) {
       // final String errors = getErrors(employeeRequest);
        if(null != employeeRequest.getId() ){
            Optional<EmployeeTable> et = employeeRepository.findById(employeeRequest.getId());
            if(et.isPresent()){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Employee Exists!");
            }
        }

        Optional<List<Errors>> error = inputValidator.getErrors(employeeRequest);
        if(error.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }

        try{

             employeeRepository.save(employeeMapper.forDB(employeeRequest));
            return new ResponseEntity<>(HttpStatus.CREATED);


        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }

    }



}
