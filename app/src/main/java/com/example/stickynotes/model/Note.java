package com.example.stickynotes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id = -1;

    @ColumnInfo(name = "text")
    private String noteText;

    @ColumnInfo(name = "date")
    private long noteDate;

    public Note() {
    }

    public Note(String noteText, long noteDate) {
        this.noteText = noteText;
        this.noteDate = noteDate;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public long getNoteDate() {
        return noteDate;
    }
}
