package com.example.stickynotes.callbacks;

import com.example.stickynotes.model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
