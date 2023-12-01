package com.example.employee.dto.request;

import java.time.LocalDate;

public record CreateEmployeeRequest(String email, String password,
                                    String firstName, String lastName,
                                    LocalDate birthDate, String position) {
}