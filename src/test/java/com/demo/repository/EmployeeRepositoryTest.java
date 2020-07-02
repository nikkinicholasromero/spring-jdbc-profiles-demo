package com.demo.repository;

import com.demo.mapper.EmployeeMapper;
import com.demo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeRepositoryTest {
    @InjectMocks
    private EmployeeRepository target;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private List<Employee> employees;

    @Mock
    private Employee employee;

    @Captor
    private ArgumentCaptor<Map<String, Object>> mapArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(employees.get(0)).thenReturn(employee);
        when(employee.getId()).thenReturn(1);
        when(employee.getFirstName()).thenReturn("Nikki Nicholas");
        when(employee.getMiddleName()).thenReturn("Domingo");
        when(employee.getLastName()).thenReturn("Romero");
        when(employee.getSalary()).thenReturn(15000.0);
        when(employee.getSomeDate()).thenReturn(LocalDate.of(2020, 7, 2));
        when(employee.getSomeTime()).thenReturn(LocalTime.of(7, 9));
        when(employee.getSomeDatetime()).thenReturn(LocalDateTime.of(2020, 7, 2, 7, 9));
        when(employee.isActive()).thenReturn(true);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(employees);
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class))).thenReturn(employees);
    }

    @Test
    public void findAll() {
        List<Employee> actual = target.findAll();

        assertThat(actual).isEqualTo(employees);
        verify(jdbcTemplate, times(1))
                .query("SELECT * FROM EMPLOYEES", employeeMapper);
    }

    @Test
    public void findById() {
        Employee actual = target.findById("1");

        assertThat(actual).isEqualTo(employee);
        verify(jdbcTemplate, times(1))
                .query(eq("SELECT * FROM EMPLOYEES WHERE ID = :ID"), mapArgumentCaptor.capture(), eq(employeeMapper));

        Map<String, Object> parameters = mapArgumentCaptor.getValue();
        assertThat(parameters).isNotNull();
        assertThat(parameters).isNotEmpty();
        assertThat(parameters.size()).isEqualTo(1);
        assertThat(parameters.get("ID")).isEqualTo("1");
    }

    @Test
    public void save() {
        target.save(employee);

        verify(jdbcTemplate, times(1))
                .update(eq(
                        "INSERT INTO EMPLOYEES (ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, " +
                                "SALARY, SOME_DATE, SOME_TIME, SOME_DATETIME, ACTIVE)  VALUES " +
                                "(:ID, :FIRST_NAME, :MIDDLE_NAME, :LAST_NAME, :SALARY, " +
                                ":SOME_DATE, :SOME_TIME, :SOME_DATETIME, :ACTIVE)"),
                        mapArgumentCaptor.capture());

        Map<String, Object> parameters = mapArgumentCaptor.getValue();
        assertThat(parameters).isNotNull();
        assertThat(parameters).isNotEmpty();
        assertThat(parameters.size()).isEqualTo(9);
        assertThat(parameters.get("ID")).isEqualTo(1);
        assertThat(parameters.get("FIRST_NAME")).isEqualTo("Nikki Nicholas");
        assertThat(parameters.get("MIDDLE_NAME")).isEqualTo("Domingo");
        assertThat(parameters.get("LAST_NAME")).isEqualTo("Romero");
        assertThat(parameters.get("SALARY")).isEqualTo(15000.0);
        assertThat(parameters.get("SOME_DATE")).isEqualTo(LocalDate.of(2020, 7, 2));
        assertThat(parameters.get("SOME_TIME")).isEqualTo(LocalTime.of(7, 9));
        assertThat(parameters.get("SOME_DATETIME")).isEqualTo(LocalDateTime.of(2020, 7, 2, 7, 9));
        assertThat(parameters.get("ACTIVE")).isEqualTo(true);
    }

    @Test
    public void update() {
        target.update(employee);

        verify(jdbcTemplate, times(1))
                .update(eq(
                        "UPDATE EMPLOYEES SET FIRST_NAME = :FIRST_NAME, MIDDLE_NAME = :MIDDLE_NAME, " +
                                "LAST_NAME = :LAST_NAME, SALARY = :SALARY, SOME_DATE = :SOME_DATE, " +
                                "SOME_TIME = :SOME_TIME, SOME_DATETIME = :SOME_DATETIME, ACTIVE = :ACTIVE WHERE ID = :ID"),
                        mapArgumentCaptor.capture());

        Map<String, Object> parameters = mapArgumentCaptor.getValue();
        assertThat(parameters).isNotNull();
        assertThat(parameters).isNotEmpty();
        assertThat(parameters.size()).isEqualTo(9);
        assertThat(parameters.get("ID")).isEqualTo(1);
        assertThat(parameters.get("FIRST_NAME")).isEqualTo("Nikki Nicholas");
        assertThat(parameters.get("MIDDLE_NAME")).isEqualTo("Domingo");
        assertThat(parameters.get("LAST_NAME")).isEqualTo("Romero");
        assertThat(parameters.get("SALARY")).isEqualTo(15000.0);
        assertThat(parameters.get("SOME_DATE")).isEqualTo(LocalDate.of(2020, 7, 2));
        assertThat(parameters.get("SOME_TIME")).isEqualTo(LocalTime.of(7, 9));
        assertThat(parameters.get("SOME_DATETIME")).isEqualTo(LocalDateTime.of(2020, 7, 2, 7, 9));
        assertThat(parameters.get("ACTIVE")).isEqualTo(true);
    }

    @Test
    public void delete() {
        target.delete("1");

        verify(jdbcTemplate, times(1))
                .update(eq("DELETE FROM EMPLOYEES WHERE ID = :ID"), mapArgumentCaptor.capture());

        Map<String, Object> parameters = mapArgumentCaptor.getValue();
        assertThat(parameters).isNotNull();
        assertThat(parameters).isNotEmpty();
        assertThat(parameters.size()).isEqualTo(1);
        assertThat(parameters.get("ID")).isEqualTo("1");
    }
}
