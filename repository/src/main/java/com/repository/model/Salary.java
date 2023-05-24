package com.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "salaries")
public class Salary {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

}
