package com.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.model.Employee;
import com.repository.repo.EmployeeRepository;
import com.service.model.EmployeeRequest;
import lombok.SneakyThrows;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmployeeControllerIntegrationTest {

    private final Integer DEFAULT_EMPLOYEE_NO = 2023052102;

    private final String DEFAULT_EMPLOYEE_FIRST_NAME = "JONNY";

    private final String DEFAULT_EMPLOYEE_LAST_NAME = "ANUGRAH";

    private final String DEFAULT_UPDATED_EMPLOYEE_LAST_NAME = "ANUGRAHI";

    private final String DEFAULT_EMPLOYEE_GENDER = "MEN";

    private final String DEFAULT_UPDATED_EMPLOYEE_GENDER = "WOMEN";

    private final String DEFAULT_BIRTH_DATE = "1992-12-01";

    private final String DEFAULT_HIRE_DATE = "2023-05-22";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        this.employeeRepository.deleteAll();
    }

    @Test
    public void givenValidEmployeeRequest_WhenCreateEmployee_shouldGet200() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generateValidCreateEmployeeRequest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href", StringContains.containsString("/employees/"+ DEFAULT_EMPLOYEE_NO)));
        Employee savedEmployee = employeeRepository.getById(DEFAULT_EMPLOYEE_NO);
        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    public void givenValidEmployeeNo_WhenGetEmployee_shouldReturnCorrectData() throws Exception {
        this.employeeRepository.save(generateValidEmployeeModel());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/employees/"+ DEFAULT_EMPLOYEE_NO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenValidEmployeeNo_WhenUpdateEmployee_shouldReturn200() throws Exception {
        this.employeeRepository.save(generateValidEmployeeModel());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/employees/"+ DEFAULT_EMPLOYEE_NO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generateValidUpdateEmployeeRequest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"));

        Employee savedEmployee = employeeRepository.findById(DEFAULT_EMPLOYEE_NO).orElse(null);
        Assertions.assertTrue(Objects.nonNull(savedEmployee));
        Assertions.assertEquals(savedEmployee.getLastName(), DEFAULT_UPDATED_EMPLOYEE_LAST_NAME);
        Assertions.assertEquals(savedEmployee.getGender().toString(), DEFAULT_UPDATED_EMPLOYEE_GENDER);
    }

    @Test
    public void givenValidEmployeeNo_WhenDeleteEmployee_shouldReturn200() throws Exception {
        this.employeeRepository.save(generateValidEmployeeModel());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/employees/"+ DEFAULT_EMPLOYEE_NO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Employee savedEmployee = employeeRepository.findById(DEFAULT_EMPLOYEE_NO).orElse(null);
        Assertions.assertTrue(Objects.isNull(savedEmployee));

    }

    @SneakyThrows
    private String generateValidCreateEmployeeRequest() {
        EmployeeRequest request = EmployeeRequest.builder()
                .employeeNo(DEFAULT_EMPLOYEE_NO).firstName(DEFAULT_EMPLOYEE_FIRST_NAME).lastName(DEFAULT_EMPLOYEE_LAST_NAME)
                .gender(DEFAULT_EMPLOYEE_GENDER).birthDate(DEFAULT_BIRTH_DATE).hireDate(DEFAULT_HIRE_DATE).build();
        return mapper.writeValueAsString(request);
    }

    @SneakyThrows
    private String generateValidUpdateEmployeeRequest() {
        EmployeeRequest request = EmployeeRequest.builder()
                .employeeNo(DEFAULT_EMPLOYEE_NO).firstName(DEFAULT_EMPLOYEE_FIRST_NAME).lastName(DEFAULT_UPDATED_EMPLOYEE_LAST_NAME)
                .gender(DEFAULT_UPDATED_EMPLOYEE_GENDER).birthDate(DEFAULT_BIRTH_DATE).hireDate(DEFAULT_HIRE_DATE).build();
        return mapper.writeValueAsString(request);
    }

    @SneakyThrows
    private Employee generateValidEmployeeModel() {
        LocalDate birthDate = LocalDate.parse(DEFAULT_BIRTH_DATE);
        LocalDate hireDate = LocalDate.parse(DEFAULT_HIRE_DATE);
        Employee result = Employee.builder()
                .employeeNo(DEFAULT_EMPLOYEE_NO).firstName(DEFAULT_EMPLOYEE_FIRST_NAME).lastName(DEFAULT_EMPLOYEE_LAST_NAME)
                .gender(Employee.Gender.MEN).birthDate(birthDate).hireDate(hireDate)
                .salaries(Arrays.asList())
                .build();
        return result;
    }

}
