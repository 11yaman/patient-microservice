package com.example.patient.dto.response;

import java.time.LocalDateTime;

public record NoteDto(Long id, String text, LocalDateTime dateTimeCreated,
                      UserDto employee, UserDto patient,
                      EncounterDto encounter) {
}
