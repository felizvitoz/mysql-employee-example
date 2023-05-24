package com.core.usecase.employee;

import com.core.entity.Employee;

import java.util.List;

public interface FilterEmployeeOutputBoundary {

    void present(List<Employee> employees);

}
