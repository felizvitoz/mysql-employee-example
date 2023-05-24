package com.service.presenter;

import com.core.entity.Employee;
import com.core.usecase.employee.FilterEmployeeOutputBoundary;
import com.service.controller.EmployeeController;
import com.service.model.EmployeeResponse;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class FilterEmployeePresenter implements FilterEmployeeOutputBoundary {

    private List<EmployeeResponse> employeeResponses;

    @Override
    public void present(List<Employee> employees) {
        this.employeeResponses = employees.stream().filter(Objects::nonNull)
                .map(this::convertToResponse).collect(Collectors.toList());
    }

    private EmployeeResponse convertToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(employee, response);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class)
                .getAllEmployees(employee.getEmployeeNo())).withSelfRel();
        response.add(selfLink);
        return response;
    }

}
