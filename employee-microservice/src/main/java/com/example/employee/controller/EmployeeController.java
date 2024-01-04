package com.example.employee.controller;

import com.example.employee.dto.request.CreateEmployeeRequest;
import com.example.employee.dto.response.EncounterDto;
import com.example.employee.dto.response.UserDto;
import com.example.employee.mapping.StrategyMapper;
import com.example.employee.model.Employee;
import com.example.employee.model.Encounter;
import com.example.employee.model.User;
import com.example.employee.security.KeycloakSecurityUtil;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('EMPLOYEE')")
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    KeycloakSecurityUtil keycloakUtil;
    @Value("${realm}")
    private String realm;
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
            if(createEmployeeRequest.email().isBlank() || createEmployeeRequest.password().isBlank() ||
                    createEmployeeRequest.firstName().isBlank() || createEmployeeRequest.lastName().isBlank() ||
                    createEmployeeRequest.birthDate() == null)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");

            User user = new Employee(createEmployeeRequest.email(), createEmployeeRequest.password(),
                    createEmployeeRequest.firstName(), createEmployeeRequest.lastName(),
                    createEmployeeRequest.birthDate(),
                    Employee.Position.valueOf(createEmployeeRequest.position()));

            createKeycloakUser(user);
            userService.createUser(user);

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

    private void createKeycloakUser(User user) {
        UserRepresentation userRep = mapToUserRep(user);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        Response res = keycloak.realm(realm).users().create(userRep);
    }

    private UserRepresentation mapToUserRep(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getUsername());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        List<String> roles = List.of(user.getRole().name());
        userRep.setRealmRoles(roles);
        return userRep;
    }
}
