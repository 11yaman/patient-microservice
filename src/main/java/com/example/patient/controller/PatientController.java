package com.example.patient.controller;

import com.example.patient.dto.request.RegisterRequest;
import com.example.patient.dto.response.PatientDto;
import com.example.patient.dto.response.UserDto;
import com.example.patient.exception.DuplicatedUserInfoException;
import com.example.patient.mapping.StrategyMapper;
import com.example.patient.model.Patient;
import com.example.patient.model.User;
import com.example.patient.security.KeycloakSecurityUtil;
import com.example.patient.service.PatientService;
import com.example.patient.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
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

@RestController
@CrossOrigin
public class PatientController {
    @Autowired
    KeycloakSecurityUtil keycloakUtil;
    @Value("${realm}")
    private String realm;

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
            if(registerRequest.email().isBlank() || registerRequest.password().isBlank() ||
                    registerRequest.firstName().isBlank() || registerRequest.lastName().isBlank() ||
                    registerRequest.birthDate() == null)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");

            User user = new Patient(registerRequest.email(), registerRequest.password(),
                    registerRequest.firstName(), registerRequest.lastName(),  registerRequest.birthDate());

            createKeycloakUser(user);
            userService.createUser(user);

            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);
        } catch (DuplicatedUserInfoException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already has been used", registerRequest.email()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User could not be created");
        }
    }

    @GetMapping("/api/v1/patients/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or @customSecurityExpressionMethods.isPatient(authentication, #id)")
    public ResponseEntity<PatientDto> getPatient(Authentication authentication, @PathVariable Long id) {
        try {
            Patient patient;
            if (userService.isEmployee(authentication))
                patient = patientService.getPatientById(id);
            else {
                String username = userService.getAuthenticatedUsername(authentication);
                patient = patientService.getPatientByUsername(username);
            }
            return ResponseEntity.ok(patientMapper.map(patient));

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/api/v1/patients/list")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<PatientDto> getAllPatients() {
        return patientMapper.mapAll(patientService.getAllPatients());
    }

    private void createKeycloakUser(User user) {
        UserRepresentation userRep = mapToUserRep(user);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        Response res = keycloak.realm(realm).users().create(userRep);
        //assignRoles(user.getUsername(), List.of(user.getRole().name()));
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

    /*
    private void assignRoles(String username, List<String> roles) {
        List<RoleRepresentation> roleList = rolesToRealmRoleRepresentation(roles);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        UserRepresentation user  = keycloak.realm(realm)
                .users()
                .search(username).get(0);
        if (user != null) user.setRealmRoles(roles);

    }
    private List<RoleRepresentation> rolesToRealmRoleRepresentation(List<String> roles) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        List<RoleRepresentation> existingRoles = keycloak.realm(realm)
                .roles()
                .list();

        List<String> serverRoles = existingRoles
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
        List<RoleRepresentation> resultRoles = new ArrayList<>();

        for (String role : roles) {
            int index = serverRoles.indexOf(role);
            if (index != -1) {
                resultRoles.add(existingRoles.get(index));
            } else {
                System.out.println("Role doesn't exist");
            }
        }
        return resultRoles;
    }

     */
}
