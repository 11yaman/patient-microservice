package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.Encounter;
import com.example.employee.model.User;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(Long employeeId);

    List<Encounter> getFutureEncountersForDoctor(Long employeeId);
}
