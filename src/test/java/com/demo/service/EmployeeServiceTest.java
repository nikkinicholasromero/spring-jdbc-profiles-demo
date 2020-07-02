package com.demo.service;

import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    @Captor
    public ArgumentCaptor<String> employeeArgumentCaptor;

    @InjectMocks
    private EmployeeService target;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {
        Employee employee1 = new Employee();
        employee1.setId(1);

        Employee employee2 = new Employee();
        employee2.setId(2);

        List<Employee> expected = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(expected);

        List<Employee> actual = target.findAll();

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isNotNull();
        assertThat(actual.get(0).getId()).isEqualTo(1);
        assertThat(actual.get(1)).isNotNull();
        assertThat(actual.get(1).getId()).isEqualTo(2);

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void findById_whenNotNull_returnSameObject() {
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeRepository.findById("1")).thenReturn(employee);

        Employee actual = target.findById("1");

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1);

        verify(employeeRepository, times(1)).findById("1");
    }

    @Test
    public void findById_whenNull_returnBlankObject() {
        when(employeeRepository.findById(anyString())).thenReturn(null);

        Employee actual = target.findById("1");

        assertThat(actual).isNull();

        verify(employeeRepository, times(1)).findById("1");
    }

    @Test
    public void save() {
        Employee employee = new Employee();
        employee.setId(1);

        target.save(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void update() {
        Employee employee = new Employee();
        employee.setId(1);

        target.update(employee);

        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    public void delete() {
        target.delete("1");

        verify(employeeRepository, times(1)).delete(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue()).isEqualTo("1");
    }
}
