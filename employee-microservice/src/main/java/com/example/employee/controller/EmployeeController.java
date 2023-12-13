package com.example.employee.controller;

import com.example.employee.dto.request.CreateEmployeeRequest;
import com.example.employee.dto.response.EncounterDto;
import com.example.employee.dto.response.UserDto;
import com.example.employee.mapping.StrategyMapper;
import com.example.employee.model.Employee;
import com.example.employee.model.Encounter;
import com.example.employee.model.User;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final StrategyMapper<User, UserDto> userMapper;
    private final StrategyMapper<Encounter, EncounterDto> encounterMapper;

    @Autowired
    public EmployeeController(UserService userService, EmployeeService employeeService, StrategyMapper<User, UserDto> userMapper, StrategyMapper<Encounter, EncounterDto> encounterMapper) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.userMapper = userMapper;
        this.encounterMapper = encounterMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest,
                                                  Authentication authentication) {
        try {
            User user = userService.createUser(
                    new Employee(createEmployeeRequest.email(), createEmployeeRequest.password(),
                            createEmployeeRequest.firstName(), createEmployeeRequest.lastName(),
                            createEmployeeRequest.birthDate(),
                            Employee.Position.valueOf(createEmployeeRequest.position()))
            );
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getEmployee(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(userMapper.map(employee));

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/{id}/future_encounters")
    public ResponseEntity<List<EncounterDto>> getEmployeeFutureEncounters(@PathVariable Long id) {
        try {
            List<Encounter> encounters = employeeService.getFutureEncountersForDoctor(id);
            return ResponseEntity.ok(encounterMapper.mapAll(encounters));
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
    }
}
