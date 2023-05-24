CREATE TABLE salaries (
   id CHAR(40) default random_uuid(),
   emp_no INT(11) NOT NULL,
   salary INT(11) NOT NULL,
   from_date DATE NOT NULL,
   to_date DATE NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY salary_emp_unique (`emp_no`, `from_date`),
   foreign key (`emp_no`) references employees(`emp_no`)
);