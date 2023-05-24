package com.core.usecase.employee;

public interface CreateEmployeeInputBoundary {

    void create(CreateEmployeeRequest request, CreateEmployeeOutputBoundary presenter);

}
