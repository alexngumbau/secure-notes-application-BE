package com.alex.Notes_Application_BE.services;

import com.alex.Notes_Application_BE.models.AuditLog;
import com.alex.Notes_Application_BE.models.Note;

import java.util.List;

public interface AuditLogService {
    void logNoteCreation(String username, Note note);

    void logNoteUpdate(String username, Note note);

    void logNoteDeletion(String username, Long noteId);

    List<AuditLog> getAllAuditLogs();

    List<AuditLog> getAuditLogsForNodeId(Long id);
}
