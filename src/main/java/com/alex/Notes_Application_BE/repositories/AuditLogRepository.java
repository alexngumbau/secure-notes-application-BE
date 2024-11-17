package com.alex.Notes_Application_BE.repositories;

import com.alex.Notes_Application_BE.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByNoteId(Long noteId);
}
