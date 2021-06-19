package com.example.noteapp.domain;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImp implements NoteRepository {

    ArrayList<Note> result;

    public NoteRepositoryImp(ArrayList<Note> result) {
        this.result = result;
    }


    public ArrayList<Note> getNotes() {
        return result;
    }

    @Override
    public void deleteElement(Note note) {
        result.remove(note);
    }

    @Override
    public void deleteAll(Note note) {
        result.clear();
    }

    @Override
    public void addElement(Note note) {

    }


}
