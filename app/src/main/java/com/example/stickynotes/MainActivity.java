package com.example.stickynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.stickynotes.adapters.NotesAdapter;
import com.example.stickynotes.callbacks.NoteEventListener;
import com.example.stickynotes.database.NotesDB;
import com.example.stickynotes.database.NotesDao;
import com.example.stickynotes.model.Note;
import com.example.stickynotes.utils.NoteUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynotes.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.stickynotes.EditNoteActivity.Note_Extra_Key;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 13/05/2019 add new note
                onAddNewNode();
            }


        });
        dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes();
        this.notes.addAll(list);
        this.adapter = new NotesAdapter(this, notes);
        this.adapter.setListener(this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void onAddNewNode() {
        //TODO: start Edit
        startActivity(new Intent(this, EditNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Log.d(TAG,"onNoteClick "+ note.getId());
        Intent edit = new Intent(this, EditNoteActivity.class);
        edit.putExtra(Note_Extra_Key, note.getId());
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(Note note) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: delete note..
                dao.deleteNote(note);
                loadNotes();
            }
        }).setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: share note..
                Intent share = new Intent(Intent.ACTION_SEND);
                String text = note.getNoteText()+"\nCreate on :"+
                        NoteUtils.dateFromLong(note.getNoteDate());


                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(share);

            }
        }).create().show();

    }
}