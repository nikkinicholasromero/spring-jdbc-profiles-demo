package com.demo.mapper;

import com.demo.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee student = new Employee();
        student.setId(resultSet.getInt("ID"));
        student.setFirstName(resultSet.getString("FIRST_NAME"));
        student.setMiddleName(resultSet.getString("MIDDLE_NAME"));
        student.setLastName(resultSet.getString("LAST_NAME"));
        student.setSalary(resultSet.getDouble("SALARY"));
        student.setSomeDate(resultSet.getDate("SOME_DATE").toLocalDate());
        student.setSomeTime(resultSet.getTime("SOME_TIME").toLocalTime());
        student.setSomeDatetime(resultSet.getTimestamp("SOME_DATETIME").toLocalDateTime());
        student.setActive(resultSet.getBoolean("ACTIVE"));
        return student;
    }
}
