package com.demo.controller;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/employees", "/employees/"})
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping({"employee/{id}", "employee/{id}/"})
    public Employee findById(@PathVariable("id") String id) {
        return employeeService.findById(id);
    }

    @PutMapping({"employee", "employee/"})
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    @PostMapping({"employee", "employee/"})
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Employee employee) {
        employeeService.update(employee);
    }

    @DeleteMapping({"employee/{id}", "employee/{id}/"})
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        employeeService.delete(id);
    }
}
