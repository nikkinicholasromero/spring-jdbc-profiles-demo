package com.demo.controller;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void findAll() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(1);
        Employee employee2 = new Employee();
        employee2.setId(2);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.findAll()).thenReturn(employees);

        this.mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        verify(employeeService, times(1)).findAll();
    }

    @Test
    public void findById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeService.findById("1")).thenReturn(employee);

        this.mockMvc.perform(get("/employee/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));

        verify(employeeService, times(1)).findById("1");
    }

    @Test
    public void save() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);

        this.mockMvc.perform(put("/employee")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(employeeService, times(1)).save(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue()).isNotNull();
        assertThat(employeeArgumentCaptor.getValue().getId()).isEqualTo(1);
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);

        this.mockMvc.perform(post("/employee")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(employeeService, times(1)).update(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue()).isNotNull();
        assertThat(employeeArgumentCaptor.getValue().getId()).isEqualTo(1);
    }

    @Test
    public void deleteEndpoint() throws Exception {
        this.mockMvc.perform(delete("/employee/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(employeeService, times(1)).delete("1");
    }
}
