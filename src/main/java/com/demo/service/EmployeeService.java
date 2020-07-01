package com.demo.service;

import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(String id) {
        return employeeRepository.findById(id);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void update(Employee employee) {
        employeeRepository.update(employee);
    }

    public void delete(String id) {
        employeeRepository.delete(id);
    }
}
