package com.repository.gateway;

import com.core.entity.Employee;
import com.core.gateway.EmployeeGateway;
import com.repository.repo.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EmployeeDataGateway implements EmployeeGateway {
    
    @Autowired
    private EmployeeRepository repository;

    @Override
    public void save(Employee employee) {
        com.repository.model.Employee employeeModel = this.convertToEmployeeModel(employee);
        repository.save(employeeModel);
    }

    @Override
    public Employee findById(Integer employeeNo) {
        return this.convertToEntity(repository.findById(employeeNo).orElse(null));
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll().stream().map(this::convertToEntity)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void delete(Employee employee) {
        this.repository.delete(this.convertToEmployeeModel(employee));
    }

    private com.repository.model.Employee convertToEmployeeModel(Employee employee) {
        com.repository.model.Employee employeeModel = new com.repository.model.Employee();
        BeanUtils.copyProperties(employee, employeeModel, "gender");
        com.repository.model.Employee.Gender gender =
                com.repository.model.Employee.Gender.valueOf(employee.getGender());
        employeeModel.setGender(gender);
        return employeeModel;
    }

    private Employee convertToEntity(com.repository.model.Employee employeeModel) {
        if (Objects.isNull(employeeModel)) {
            return null;
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeModel, employee, "salaries", "gender");
        employee.setGender(employeeModel.getGender().toString());
        return employee;
    }

}
