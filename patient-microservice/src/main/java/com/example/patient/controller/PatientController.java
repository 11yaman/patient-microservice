package com.example.patient.controller;

import com.example.patient.dto.request.RegisterRequest;
import com.example.patient.dto.response.PatientDto;
import com.example.patient.dto.response.UserDto;
import com.example.patient.exception.DuplicatedUserInfoException;
import com.example.patient.mapping.StrategyMapper;
import com.example.patient.model.Patient;
import com.example.patient.model.User;
import com.example.patient.service.PatientService;
import com.example.patient.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class PatientController {
    private final PatientService patientService;
    private final StrategyMapper<Patient, PatientDto> patientMapper;
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;

    public PatientController(PatientService patientService,
                             UserService userService,
                             StrategyMapper<Patient, PatientDto> patientMapper,
                             StrategyMapper<User, UserDto> userMapper) {
        this.patientService = patientService;
        this.userService = userService;
        this.patientMapper = patientMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/api/v1/patients/register")
    public ResponseEntity<UserDto> registerPatient(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println("Test Register");
            if(registerRequest.email().isBlank() || registerRequest.password().isBlank() ||
                    registerRequest.firstName().isBlank() || registerRequest.lastName().isBlank() ||
                    registerRequest.birthDate() == null)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");

            User user = userService.createUser(
                    new Patient(registerRequest.email(), registerRequest.password(),
                            registerRequest.firstName(), registerRequest.lastName(),  registerRequest.birthDate())
            );
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);
        } catch (DuplicatedUserInfoException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already has been used", registerRequest.email()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");
        }
    }

    @GetMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientDto> getPatient(Authentication authentication, @PathVariable Long id) {
        if(!userService.isEmployeeOrResourceOwner(authentication, id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            Patient patient;
            if (userService.isEmployee(authentication))
                patient = patientService.getPatientById(id);
            else
                patient = patientService.getPatientByUsername(authentication.getName());
            return ResponseEntity.ok(patientMapper.map(patient));

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/api/v1/patients/list")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public List<PatientDto> getAllPatients() {
        return patientMapper.mapAll(patientService.getAllPatients());
    }
}
