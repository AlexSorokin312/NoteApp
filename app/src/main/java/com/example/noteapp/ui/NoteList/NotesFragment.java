package com.example.noteapp.ui.NoteList;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.domain.NoteRepository;
import com.example.noteapp.domain.NoteRepositoryImp;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private NoteRepositoryImp noteRepository = NoteRepositoryImp.INSTANCE;
    private OnNoteClicked onNoteClicked;
    private onEditNoteClicked onEditNoteClicked;
    private onDateEditClick onDateEditClick;
    private OnAddNote onAddNote;
    private NotesAdapter notesAdapter = new NotesAdapter();

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public interface OnAddNote {
        void onAddNote(NoteRepositoryImp noteRepository, NotesAdapter notesAdapter);
    }

    public interface onEditNoteClicked {
        void onEditNoteClicked(Note note, View v, AppCompatImageView imageWidget, RecyclerView noteList, View itemView);
    }

    public interface onDateEditClick {
        void onDateEditClick(Note note, TextView dateTx);
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteClicked)
            onNoteClicked = (OnNoteClicked) context;
        if (context instanceof onEditNoteClicked)
            onEditNoteClicked = (onEditNoteClicked) context;
        if (context instanceof onDateEditClick)
            onDateEditClick = (onDateEditClick) context;
        if (context instanceof OnAddNote)
            onAddNote = (OnAddNote) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNoteClicked = null;
        onEditNoteClicked = null;
        onDateEditClick = null;
        onAddNote = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        RecyclerView noteList = view.findViewById(R.id.notes_list_container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        noteList.setLayoutManager(linearLayoutManager);
        List<Note> notes = noteRepository.getNotes();

        notesAdapter.setData(notes);
        noteList.setAdapter(notesAdapter);

        notesAdapter.setOnNoteClickedListener(note -> {
            if (onNoteClicked != null) {
                onNoteClicked.onNoteClicked(note);
            }
        });
        notesAdapter.setOnEditNoteClicked((note, v, imageWidget, itemView) -> onEditNoteClicked.onEditNoteClicked(note, v, imageWidget, noteList,
                itemView));

        notesAdapter.setOnDateEditClick((note, dateTx) -> {
            if (onDateEditClick != null) {
                onDateEditClick.onDateEditClick(note, dateTx);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_note) {

            if (onAddNote != null) {
                onAddNote.onAddNote(noteRepository, notesAdapter);
            }
        }
        return true;
    }
}
