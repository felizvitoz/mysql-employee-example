package com.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotNull(message = "Employee No is required.")
    private Integer employeeNo;

    @NotEmpty(message = "First Name is required.")
    @Size(
            max = 14,
            message = "firstName length exceeded"
    )
    private String firstName;

    @NotEmpty(message = "Last Name is required.")
    @Size(
            max = 16,
            message = "lastName length exceeded"
    )
    private String lastName;

    @NotEmpty(message = "Gender Name is required.")
    private String gender;

    @NotEmpty(message = "Birth Date is required.")
    private String birthDate;

    @NotEmpty(message = "Hire Date is required.")
    private String hireDate;

}
