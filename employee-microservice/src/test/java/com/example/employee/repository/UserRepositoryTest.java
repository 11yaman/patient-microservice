package com.example.employee.repository;

import com.example.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void checkIfEmployeeCreated() {
        Employee employee = new Employee("employeetest1", "112233", "test", "test", LocalDate.now(), Employee.Position.DOCTOR);

        userRepository.save(employee);

        Employee employee1 = (Employee) userRepository.findByUsername("employeetest1").orElse(null);
        assertNotNull(employee1, "Employee should be created");
        assertEquals("employeetest1", employee1.getUsername(), "Employee username should match");
        assertEquals("test", employee1.getFirstName(), "Employee first name should match");
        assertEquals("test", employee1.getLastName(), "Employee last name should match");
        assertEquals(LocalDate.now(), employee1.getBirthDate(), "Employee birth date should match");
        assertEquals(Employee.Position.DOCTOR, employee1.getPosition(), "Employee position should match");

        userRepository.delete(employee1);
    }


}