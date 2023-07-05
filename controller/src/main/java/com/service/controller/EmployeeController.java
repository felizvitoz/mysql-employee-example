package com.service.controller;

import com.core.entity.Employee;
import com.core.usecase.employee.*;
import com.service.model.EmployeeRequest;
import com.service.model.EmployeeResponse;
import com.service.presenter.CreateEmployeePresenter;
import com.service.presenter.FilterEmployeePresenter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private CreateEmployeeInputBoundary createEmployeeUseCase;

    @Autowired
    private FilterEmployeeInputBoundary filterEmployeeUseCase;

    @Autowired
    private UpdateEmployeeInputBoundary updateEmployeeUseCase;

    @Autowired
    private DeleteEmployeeInputBoundary deleteEmployeeUseCase;

    @GetMapping(value = {"/{employeeNo}", ""})
    public List<EmployeeResponse> getAllEmployees(@PathVariable(required = false) final Integer employeeNo) {
        FilterEmployeeRequest request = FilterEmployeeRequest.builder().employeeNo(employeeNo).build();
        FilterEmployeePresenter presenter = new FilterEmployeePresenter();
        this.filterEmployeeUseCase.filter(request, presenter);
        return presenter.getEmployeeResponses();
    }

    @PostMapping(consumes = "application/json")
    public EmployeeResponse saveEmployees(@RequestBody @Valid EmployeeRequest employeeRequest) {
        AtomicReference<EmployeeResponse> savedEmployeeReference = new AtomicReference<>();
        RetryAbleExecutor.retryAbleExecution(() -> {
            savedEmployeeReference.set(saveOrUpdate(employeeRequest, createEmployeeUseCase::create));
        });
        return savedEmployeeReference.get();
    }

    @PutMapping(value = {"/{employeeNo}"})
    public EmployeeResponse updateEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) throws Exception {
        AtomicReference<EmployeeResponse> savedEmployeeReference = new AtomicReference<>();
        RetryAbleExecutor.retryAbleExecution(() -> {
            savedEmployeeReference.set(saveOrUpdate(employeeRequest, updateEmployeeUseCase::update));
        });
        return savedEmployeeReference.get();
    }

    @DeleteMapping(value = {"/{employeeNo}"})
    public ResponseEntity deleteEmployee(@PathVariable final Integer employeeNo) throws Exception {
        RetryAbleExecutor.retryAbleExecution(() -> {
            FilterEmployeeRequest request = FilterEmployeeRequest.builder().employeeNo(employeeNo).build();
            deleteEmployeeUseCase.delete(request);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private EmployeeResponse saveOrUpdate(EmployeeRequest employeeRequest,
                                          BiConsumer<CreateEmployeeRequest, CreateEmployeePresenter> actualMethod) {
        CreateEmployeePresenter presenter = new CreateEmployeePresenter();
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        BeanUtils.copyProperties(employeeRequest, createEmployeeRequest, "birthDate", "hireDate");
        createEmployeeRequest.setBirthDate(LocalDate.parse(employeeRequest.getBirthDate()));
        createEmployeeRequest.setHireDate(LocalDate.parse(employeeRequest.getHireDate()));
        actualMethod.accept(createEmployeeRequest, presenter);
        return presenter.getEmployeeResponse();
    }

}
