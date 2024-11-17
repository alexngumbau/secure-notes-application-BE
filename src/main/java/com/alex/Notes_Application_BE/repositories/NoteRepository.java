package com.alex.Notes_Application_BE.repositories;

import com.alex.Notes_Application_BE.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByOwnerUsername(String ownerUsername);

}
