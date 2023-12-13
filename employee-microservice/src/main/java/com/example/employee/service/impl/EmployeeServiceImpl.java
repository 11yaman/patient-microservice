package com.example.employee.service.impl;

import com.example.employee.exception.NotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.model.Encounter;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.EncounterRepository;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EncounterRepository encounterRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EncounterRepository encounterRepository) {
        this.employeeRepository = employeeRepository;
        this.encounterRepository = encounterRepository;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<Encounter> getFutureEncountersForDoctor(Long employeeId) {
        return encounterRepository.findByEmployeeIdAndDateTimeAfterOrderByDateTimeAsc(
                employeeId, LocalDateTime.now().toLocalDate().atTime(0, 0)
        );
    }
}
