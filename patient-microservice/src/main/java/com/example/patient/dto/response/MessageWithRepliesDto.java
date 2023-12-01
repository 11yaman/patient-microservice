package com.example.patient.dto.response;

import com.example.patient.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public record MessageWithRepliesDto(Long id, String content, LocalDateTime dateTime,
                                    UserDto sender, Message.Status status, List<ReplyDto> replies) {
}
