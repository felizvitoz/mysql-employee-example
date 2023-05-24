package com.service.presenter;

import com.core.entity.Employee;
import com.core.usecase.employee.CreateEmployeeOutputBoundary;
import com.service.controller.EmployeeController;
import com.service.model.EmployeeResponse;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter
public class CreateEmployeePresenter implements CreateEmployeeOutputBoundary {

    private EmployeeResponse employeeResponse;

    @Override
    public void present(Employee employee) {
        this.employeeResponse = EmployeeResponse.builder().employeeNo(employee.getEmployeeNo())
                .firstName(employee.getFirstName()).lastName(employee.getLastName())
                .gender(employee.getGender()).birthDate(employee.getBirthDate()).hireDate(employee.getHireDate()).build();
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class)
                .getAllEmployees(employee.getEmployeeNo())).withSelfRel();
        this.employeeResponse.add(selfLink);
    }

}
