package com.example.patient.service.impl;

import com.example.patient.model.Message;
import com.example.patient.model.Reply;
import com.example.patient.repository.MessageRepository;
import com.example.patient.repository.ReplyRepository;
import com.example.patient.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ReplyRepository replyRepository) {
        this.messageRepository = messageRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageWithRepliesById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
    }

    @Override
    public Message replyToMessage(Message parentMessage, Reply reply) {
        parentMessage.getReplies().add(reply);
        parentMessage.setStatus(Message.Status.ACTIVE);
        messageRepository.save(parentMessage);
        return parentMessage;
    }

    @Override
    public List<Message> getAllMessagesByPatient(Long patientId) {
        return messageRepository.findBySenderIdOrderByDateTimeDesc(patientId);
    }
    @Override
    public List<Message> getActiveMessagesForEmployees() {
        return messageRepository.findByStatusOrderByDateTimeDesc(Message.Status.ACTIVE);
    }

}
