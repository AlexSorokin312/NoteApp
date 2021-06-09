package com.example.noteapp.domain;

import java.util.ArrayList;

public interface NoteRepository {

    ArrayList<Note> getNotes();

    String changeDate();
}
