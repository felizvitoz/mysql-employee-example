CREATE TABLE dept_emp (
   emp_no INT(11) NOT NULL,
   dept_no CHAR(4) NOT NULL,
   from_date DATE NOT NULL,
   to_date DATE NOT NULL,
   type TINYINT NOT NULL,
   PRIMARY KEY (`emp_no`,`dept_no`),
   foreign key (`emp_no`) references employees(`emp_no`),
   foreign key (`dept_no`) references departments(`dept_no`)
);