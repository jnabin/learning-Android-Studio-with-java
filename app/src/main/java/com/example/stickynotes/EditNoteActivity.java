package com.example.stickynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.stickynotes.database.NotesDB;
import com.example.stickynotes.database.NotesDao;
import com.example.stickynotes.model.Note;

import java.time.temporal.Temporal;
import java.util.Date;

public class  EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private  Note temp;
    public static final String Note_Extra_Key = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).notesDao();
        if(getIntent().getExtras() != null){
            int id = getIntent().getExtras().getInt(Note_Extra_Key, 0);
            temp = dao.getNoteByid(id);
            inputNote.setText(temp.getNoteText());
        }else{
            temp = new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save_note){
            onSaveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        //TODO: Save Note
        String text = inputNote.getText().toString();
        if(!text.isEmpty()){
            long date = new Date().getTime();

            temp.setNoteDate(date);
            temp.setNoteText(text);

            if(temp.getId() == -1){
                dao.insertNote(temp);
            }else dao.updateNote(temp);

            finish();
        }
    }
}