package com.paypal.bfs.test.employeeserv.util;


import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.findings.Errors;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class InputValidator {

    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String DATE_OF_BIRTH = "Date of Birth";
    private static final String ADDRESS_LINE_1 = "Address Line 1";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIP_CODE = "zip code";
    private static final String COUNTRY = "country";
    private static final int MAX_LENGTH = 255;
    private static final String REQUIRED = "This is required";

    public Optional<List<Errors>> getErrors(Employee employeeRequest) {
        List<Errors> errorsList = new ArrayList<>();

        checkForRequired(employeeRequest, errorsList);
        //max length
        checkForLength(employeeRequest, errorsList);
        //Date format

        if(errorsList.size()>0){
            return Optional.of(errorsList);
        }

        return Optional.empty();
    }

    private void checkForLength(Employee employeeRequest, List<Errors> errorsList) {
        if(isMaxLength(employeeRequest.getFirstName(),MAX_LENGTH)){
            errorsList.add(Errors.builder().field(FIRST_NAME).message("Max length is "+MAX_LENGTH).build());
        }

        if(isMaxLength(employeeRequest.getZipCode(),10)){
            errorsList.add(Errors.builder().field(ZIP_CODE).message("Max length is "+10).build());
        }
    }

    private boolean isMaxLength(String value,int maxLength) {
        return value.length() > maxLength;
    }

    private void checkForRequired(Employee employeeRequest, List<Errors> errorsList) {
        //last name
        //first name
        if(isEmpty(employeeRequest.getFirstName())){
            errorsList.add(Errors.builder().field(FIRST_NAME).message(REQUIRED).build());
        }

        if(isEmpty(employeeRequest.getLastName())){
            errorsList.add(Errors.builder().field(LAST_NAME).message(REQUIRED).build());
        }

        if(isEmpty(employeeRequest.getDateOfBirth())){
            errorsList.add(Errors.builder().field(DATE_OF_BIRTH).message(REQUIRED).build());
        }

        if(isEmpty(employeeRequest.getAddressLine1())){
            errorsList.add(Errors.builder().field(ADDRESS_LINE_1).message(REQUIRED).build());
        }

        if(isEmpty(employeeRequest.getCity())){
            errorsList.add(Errors.builder().field(CITY).message(REQUIRED).build());
        }
        if(isEmpty(employeeRequest.getState())){
            errorsList.add(Errors.builder().field(STATE).message(REQUIRED).build());
        }
        if(isEmpty(employeeRequest.getZipCode())){
            errorsList.add(Errors.builder().field(ZIP_CODE).message(REQUIRED).build());
        }
        if(isEmpty(employeeRequest.getCountry())){
            errorsList.add(Errors.builder().field(COUNTRY).message(REQUIRED).build());
        }
    }


    private boolean isEmpty(String value){
        return null == value;
    }

}
