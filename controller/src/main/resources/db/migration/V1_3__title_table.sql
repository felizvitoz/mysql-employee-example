CREATE TABLE titles (
   id CHAR(40) default random_uuid(),
   emp_no INT (11) NOT NULL,
   title VARCHAR(50) ,
   from_date DATE NOT NULL,
   to_date DATE NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY titles_emp_unique (`emp_no`,`title`,`from_date`),
   foreign key (`emp_no`) references employees(`emp_no`)
);