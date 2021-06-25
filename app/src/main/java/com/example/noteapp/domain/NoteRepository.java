package com.example.noteapp.domain;

import java.util.ArrayList;
import java.util.List;

public interface NoteRepository {

    ArrayList<Note> getNotes();

    void deleteElement(Note note);

    void deleteAll();

    Note addNote(String name, String description, String dateCreation, boolean isCompleted);
}
