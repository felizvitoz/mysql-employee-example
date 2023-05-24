package com.core.usecase.employee;

public interface DeleteEmployeeInputBoundary {

    void delete(FilterEmployeeRequest request) throws Exception;

}
