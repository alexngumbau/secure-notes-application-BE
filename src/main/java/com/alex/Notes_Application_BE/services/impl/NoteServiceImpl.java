package com.alex.Notes_Application_BE.services.impl;

import com.alex.Notes_Application_BE.models.Note;
import com.alex.Notes_Application_BE.repositories.NoteRepository;
import com.alex.Notes_Application_BE.services.AuditLogService;
import com.alex.Notes_Application_BE.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public Note createNoteForUser(String username, String content)  {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        auditLogService.logNoteCreation(username, note);
        return noteRepository.save(note);
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setContent(content);
        auditLogService.logNoteUpdate(username, note);
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new RuntimeException("Note not found"));
        auditLogService.logNoteDeletion(username, noteId);
        noteRepository.delete(note);
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        return noteRepository.findByOwnerUsername(username);
    }
    
}
