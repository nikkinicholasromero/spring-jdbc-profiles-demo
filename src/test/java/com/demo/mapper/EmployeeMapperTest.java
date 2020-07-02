package com.demo.mapper;

import com.demo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EmployeeMapperTest {
    @InjectMocks
    private EmployeeMapper target;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Date date;

    @Mock
    private Time time;

    @Mock
    private Timestamp timestamp;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(date.toLocalDate()).thenReturn(LocalDate.of(2020, 7, 2));
        when(time.toLocalTime()).thenReturn(LocalTime.of(7, 22));
        when(timestamp.toLocalDateTime()).thenReturn(LocalDateTime.of(2020, 7, 2, 7, 22));

        when(resultSet.getInt("ID")).thenReturn(1);
        when(resultSet.getString("FIRST_NAME")).thenReturn("Nikki Nicholas");
        when(resultSet.getString("MIDDLE_NAME")).thenReturn("Domingo");
        when(resultSet.getString("LAST_NAME")).thenReturn("Romero");
        when(resultSet.getDouble("SALARY")).thenReturn(15000.0);
        when(resultSet.getDate("SOME_DATE")).thenReturn(date);
        when(resultSet.getTime("SOME_TIME")).thenReturn(time);
        when(resultSet.getTimestamp("SOME_DATETIME")).thenReturn(timestamp);
        when(resultSet.getBoolean("ACTIVE")).thenReturn(true);
    }

    @Test
    public void mapRow() throws SQLException {
        Employee actual = target.mapRow(resultSet, 0);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getFirstName()).isEqualTo("Nikki Nicholas");
        assertThat(actual.getMiddleName()).isEqualTo("Domingo");
        assertThat(actual.getLastName()).isEqualTo("Romero");
        assertThat(actual.getSalary()).isEqualTo(15000.0);
        assertThat(actual.getSomeDate()).isEqualTo(LocalDate.of(2020, 7, 2));
        assertThat(actual.getSomeTime()).isEqualTo(LocalTime.of(7, 22));
        assertThat(actual.getSomeDatetime()).isEqualTo(LocalDateTime.of(2020, 7, 2, 7, 22));
        assertThat(actual.isActive()).isTrue();
    }
}
