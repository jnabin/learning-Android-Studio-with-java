package com.example.stickynotes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.stickynotes.model.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Query("SELECT * FROM notes WHERE id LIKE :noteID")
    Note getNoteByid(int noteID);

    @Query("DELETE FROM notes WHERE id = :noteID")
    void deleteNoteByid(int noteID);
}
