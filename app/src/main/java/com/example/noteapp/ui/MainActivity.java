package com.example.noteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;

public class MainActivity extends AppCompatActivity implements NoteFragment.OnNoteClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNoteClicked(Note note) {
        boolean isLandscaped = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscaped) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_details, NoteInfoFragment.newInstance(note))
                    .commit();
        } else {
            Intent intent = new Intent(this, NoteInfoActivity.class);
            intent.putExtra(NoteInfoActivity.ARG_NOTE, note);
            startActivity(intent);
        }
    }
}