package com.core.usecase.employee.impl;

import com.core.entity.Employee;
import com.core.gateway.EmployeeGateway;
import com.core.RetryAbleExecutor;
import com.core.usecase.employee.CreateEmployeeInputBoundary;
import com.core.usecase.employee.CreateEmployeeOutputBoundary;
import com.core.usecase.employee.CreateEmployeeRequest;
import com.error.customerror.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
public class CreateEmployeeUseCase implements CreateEmployeeInputBoundary {

    @Autowired
    private EmployeeGateway employeeGateway;

    @Override
    public void create(CreateEmployeeRequest request, CreateEmployeeOutputBoundary presenter) {
        RetryAbleExecutor.retryAbleExecution(() -> {
            this.validateRequest(request);
            Employee employee = new Employee();
            BeanUtils.copyProperties(request, employee);
            employeeGateway.save(employee);
            presenter.present(employee);
        });
    }

    private void validateRequest(CreateEmployeeRequest request) {
        Employee employee = this.employeeGateway.findById(request.getEmployeeNo());
        if (Objects.nonNull(employee)) {
            throw new BusinessException("Employee is already exists.");
        }
    }

}
