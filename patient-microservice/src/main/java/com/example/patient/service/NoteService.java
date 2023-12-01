package com.example.patient.service;

import com.example.patient.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note getNoteById(Long noteId);
    Note createNote(Note note);
    List<Note> getAllNotesForPatient(Long patientId);
}