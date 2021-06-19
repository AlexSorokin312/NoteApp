package com.example.noteapp.domain;

import java.util.ArrayList;
import java.util.List;

public interface NoteRepository {

    ArrayList<Note> getNotes();

    void deleteElement(Note note);

    void deleteAll(Note note);

    void addElement(Note note);
}
