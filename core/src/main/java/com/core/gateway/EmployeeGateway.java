package com.core.gateway;

import com.core.entity.Employee;

import java.util.List;

public interface EmployeeGateway {

    void save(Employee employee);

    Employee findById(Integer employeeNo);

    List<Employee> findAll();

    void delete(Employee employee);

}
