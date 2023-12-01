package com.example.patient.dto.response;

import java.time.LocalDateTime;

public record MessageDto(Long id, String content, LocalDateTime dateTime,
                         UserDto sender, com.example.patient.model.Message.Status status) {
}
