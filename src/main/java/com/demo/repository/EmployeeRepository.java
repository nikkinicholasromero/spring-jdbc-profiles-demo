package com.demo.repository;

import com.demo.mapper.EmployeeMapper;
import com.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepository {
    private static final String FIND_ALL = "SELECT * FROM EMPLOYEES";
    private static final String FIND_BY_ID = "SELECT * FROM EMPLOYEES WHERE ID = :ID";
    private static final String INSERT = "INSERT INTO EMPLOYEES (ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, SALARY, SOME_DATE, SOME_TIME, SOME_DATETIME, ACTIVE) " +
        " VALUES (:ID, :FIRST_NAME, :MIDDLE_NAME, :LAST_NAME, :SALARY, :SOME_DATE, :SOME_TIME, :SOME_DATETIME, :ACTIVE)";
    private static final String UPDATE = "UPDATE EMPLOYEES SET FIRST_NAME = :FIRST_NAME, MIDDLE_NAME = :MIDDLE_NAME, LAST_NAME = :LAST_NAME, " + 
        "SALARY = :SALARY, SOME_DATE = :SOME_DATE, SOME_TIME = :SOME_TIME, SOME_DATETIME = :SOME_DATETIME, ACTIVE = :ACTIVE WHERE ID = :ID";
    private static final String DELETE = "DELETE FROM EMPLOYEES WHERE ID = :ID";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> findAll() {
        return jdbcTemplate.query(FIND_ALL, employeeMapper);
    }

    public Employee findById(String id) {
        return jdbcTemplate.query(FIND_BY_ID, toParameter(id), employeeMapper).get(0);
    }

    public void save(Employee employee) {
        jdbcTemplate.update(INSERT, toParameter(employee));
    }

    public void update(Employee employee) {
        jdbcTemplate.update(UPDATE, toParameter(employee));
    }

    public void delete(String id) {
        jdbcTemplate.update(DELETE, toParameter(id));
    }

    private Map<String, Object> toParameter(Employee employee) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", employee.getId());
        parameters.put("FIRST_NAME", employee.getFirstName());
        parameters.put("MIDDLE_NAME", employee.getMiddleName());
        parameters.put("LAST_NAME", employee.getLastName());
        parameters.put("SALARY", employee.getSalary());
        parameters.put("SOME_DATE", employee.getSomeDate());
        parameters.put("SOME_TIME", employee.getSomeTime());
        parameters.put("SOME_DATETIME", employee.getSomeDatetime());
        parameters.put("ACTIVE", employee.isActive());
        return parameters;
    }

    private Map<String, Object> toParameter(String id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", id);
        return parameters;
    }
}
