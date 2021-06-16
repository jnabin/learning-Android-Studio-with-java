package com.example.stickynotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.stickynotes.model.Note;

@Database(entities = Note.class, version = 1)
public abstract class NotesDB extends RoomDatabase {
    public abstract NotesDao notesDao();

    public static final String DATABASE_NAME = "noteDb";
;    private static NotesDB instances;

    public static NotesDB getInstance(Context context) {
        if(instances==null){
            instances = Room.databaseBuilder(context, NotesDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();

        }
        return instances;
    }
}
