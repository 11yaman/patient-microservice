package com.example.patient.service;

import com.example.patient.model.Message;
import com.example.patient.model.Reply;

import java.util.List;

public interface MessageService {
    Message createMessage(Message message);

    Message getMessageWithRepliesById(Long messageId);

    Message replyToMessage(Message parentMessage, Reply reply);

    List<Message> getAllMessagesByPatient(Long patientId);

    List<Message> getActiveMessagesForEmployees();
}
