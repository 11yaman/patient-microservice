package com.example.employee.controller;

import com.example.employee.dto.request.CreateEmployeeRequest;
import com.example.employee.dto.response.UserDto;
import com.example.employee.mapping.StrategyMapper;
import com.example.employee.model.Employee;
import com.example.employee.model.User;
import com.example.employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;

    @Autowired
    public EmployeeController(UserService userService, StrategyMapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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
}
