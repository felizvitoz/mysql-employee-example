package com.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "dept_no", nullable = false)
    private String departmentNo;

    @Column(name = "dept_name", nullable = false)
    private String departmentName;

}
