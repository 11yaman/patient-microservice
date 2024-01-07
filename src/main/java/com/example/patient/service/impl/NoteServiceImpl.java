package com.example.patient.service.impl;

import com.example.patient.exception.NotFoundException;
import com.example.patient.model.Note;
import com.example.patient.repository.NoteRepository;
import com.example.patient.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException("Note not found: " + noteId));
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotesForPatient(Long patientId) {
        return noteRepository.findByPatientIdOrderByDateTimeCreatedDesc(patientId);
    }
}
