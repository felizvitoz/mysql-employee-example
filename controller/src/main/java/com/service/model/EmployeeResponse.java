package com.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends RepresentationModel<EmployeeResponse> {

    private Integer employeeNo;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthDate;

    private LocalDate hireDate;

}
