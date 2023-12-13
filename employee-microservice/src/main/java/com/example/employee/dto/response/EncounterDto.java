package com.example.employee.dto.response;

import java.time.LocalDateTime;

public record EncounterDto(Long id, UserDto patient, UserDto employee, LocalDateTime dateTime){
}
