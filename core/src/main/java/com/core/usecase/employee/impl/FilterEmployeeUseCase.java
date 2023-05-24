package com.core.usecase.employee.impl;

import com.core.entity.Employee;
import com.core.gateway.EmployeeGateway;
import com.core.usecase.employee.FilterEmployeeInputBoundary;
import com.core.usecase.employee.FilterEmployeeOutputBoundary;
import com.core.usecase.employee.FilterEmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FilterEmployeeUseCase implements FilterEmployeeInputBoundary {

    @Autowired
    private EmployeeGateway employeeGateway;

    @Override
    public void filter(FilterEmployeeRequest request, FilterEmployeeOutputBoundary presenter) {
        List<Employee> employees;
        // better replace with criteria specification for cleaner code...
        if (Objects.nonNull(request.getEmployeeNo())) {
            employees = Arrays.asList(this.employeeGateway.findById(request.getEmployeeNo()));
        } else {
            employees = this.employeeGateway.findAll();
        }
        presenter.present(employees);
    }

}
