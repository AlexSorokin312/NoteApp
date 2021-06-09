package com.example.noteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;

public class NoteInfoActivity extends AppCompatActivity {

    public static final String ARG_NOTE = "ARG_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);

        if (savedInstanceState == null) {

            Note note = getIntent().getParcelableExtra(ARG_NOTE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, NoteInfoFragment.newInstance(note))
                    .commit();
        }

    }
}