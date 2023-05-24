package com.core.usecase.employee.impl;

import com.core.entity.Employee;
import com.core.gateway.EmployeeGateway;
import com.core.RetryAbleExecutor;
import com.core.usecase.employee.CreateEmployeeOutputBoundary;
import com.core.usecase.employee.CreateEmployeeRequest;
import com.core.usecase.employee.UpdateEmployeeInputBoundary;
import com.error.customerror.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
public class UpdateEmployeeUseCase extends RetryAbleExecutor implements UpdateEmployeeInputBoundary {

    @Autowired
    private EmployeeGateway employeeGateway;

    @Override
    public void update(CreateEmployeeRequest request, CreateEmployeeOutputBoundary presenter) {
        RetryAbleExecutor.retryAbleExecution(() -> {
            this.validateRequest(request);
            Employee employeeToUpdate = Employee.builder()
                    .employeeNo(request.getEmployeeNo()).firstName(request.getFirstName())
                    .lastName(request.getLastName()).gender(request.getGender())
                    .birthDate(request.getBirthDate()).hireDate(request.getHireDate()).build();
            this.employeeGateway.save(employeeToUpdate);
            presenter.present(employeeToUpdate);
        });
    }

    private void validateRequest(CreateEmployeeRequest request) {
        Employee employee = this.employeeGateway.findById(request.getEmployeeNo());
        if (Objects.isNull(employee)) {
            throw new BusinessException("Invalid EmployeeNo");
        }
    }

}
