package com.example.patient.dto.request;

import java.time.LocalDate;

public record RegisterRequest(String email, String password, String firstName, String lastName, LocalDate birthDate) {
}
