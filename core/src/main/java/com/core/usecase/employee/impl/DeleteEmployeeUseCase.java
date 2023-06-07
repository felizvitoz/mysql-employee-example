package com.core.usecase.employee.impl;

import com.core.entity.Employee;
import com.core.gateway.EmployeeGateway;
import com.core.usecase.employee.DeleteEmployeeInputBoundary;
import com.core.usecase.employee.FilterEmployeeRequest;
import com.error.customerror.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@Transactional
public class DeleteEmployeeUseCase implements DeleteEmployeeInputBoundary {

    @Autowired
    private EmployeeGateway employeeGateway;

    @Override
    public void delete(FilterEmployeeRequest request) {
        AtomicReference<Employee> savedEmployeeReference = new AtomicReference<>();
        this.validateRequest(request, savedEmployeeReference);
        this.employeeGateway.delete(savedEmployeeReference.get());
    }

    private void validateRequest(FilterEmployeeRequest request, AtomicReference<Employee> savedEmployeeReference) {
        Employee employee = this.employeeGateway.findById(request.getEmployeeNo());
        if (Objects.isNull(employee)) {
            throw new BusinessException("Invalid EmployeeNo");
        }
        savedEmployeeReference.set(employee);
    }

}
