package com.example.patient.security;

import com.example.patient.model.Message;
import com.example.patient.model.Note;
import com.example.patient.model.Patient;
import com.example.patient.service.MessageService;
import com.example.patient.service.NoteService;
import com.example.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityExpressionMethods {
    @Autowired
    PatientService patientService;

    @Autowired
    MessageService messageService;

    @Autowired
    NoteService noteService;

    public boolean isPatient(Authentication authentication, Long patientId) {
        return isResourceOwner(authentication, patientId);
    }

    public boolean isMessageOwner(Authentication authentication, Long messageId) {
        Message message = messageService.getMessageWithRepliesById(messageId);
        return isResourceOwner(authentication, message.getSender().getId());
    }

    public boolean isNoteOwner(Authentication authentication, Long noteId) {
        Note note = noteService.getNoteById(noteId);
        return isResourceOwner(authentication, note.getPatient().getId());
    }

    private boolean isResourceOwner(Authentication authentication, Long patientId) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        Patient patient;
        try {
            patient = patientService.getPatientById((Long) patientId);
        } catch (Exception e) {
            return false;
        }
        return username.equals(patient.getUsername());
    }
}
