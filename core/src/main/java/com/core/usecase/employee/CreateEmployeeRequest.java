package com.core.usecase.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    private Integer employeeNo;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthDate;

    private LocalDate hireDate;

}
