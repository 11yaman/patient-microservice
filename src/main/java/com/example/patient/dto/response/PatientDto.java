package com.example.patient.dto.response;

import java.time.LocalDate;

public record PatientDto(Long id, String email, String firstName, String lastName, LocalDate birthDate) {
}
