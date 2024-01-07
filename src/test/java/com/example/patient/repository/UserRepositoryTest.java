package com.example.patient.repository;

import com.example.patient.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    void checkIfPatientCreated() {
        Patient patient = new Patient("patienttest1", "112233", "test", "test", LocalDate.now());

        userRepository.save(patient);

        Patient patient1 = (Patient) userRepository.findByUsername("patienttest1").orElse(null);
        assertNotNull(patient1, "Patient should be created");
        assertEquals("patienttest1", patient1.getUsername(), "Patient username should match");
        assertEquals("test", patient1.getFirstName(), "Patient first name should match");
        assertEquals("test", patient1.getLastName(), "Patient last name should match");
        assertEquals(LocalDate.now(), patient1.getBirthDate(), "Patient birth date should match");

        userRepository.delete(patient1);
    }
}