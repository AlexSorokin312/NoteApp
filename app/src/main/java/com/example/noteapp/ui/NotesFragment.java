package com.example.noteapp.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.domain.NoteRepository;
import com.example.noteapp.domain.NoteRepositoryImp;

import java.util.Calendar;
import java.util.List;

public class NotesFragment extends Fragment {

    private NoteRepositoryImp noteRepository;
    private OnNoteClicked onNoteClicked;
    private onEditNoteClicked onEditNoteClicked;
    private onDateEditClick onDateEditClick;

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }


    public interface onEditNoteClicked {
        void onEditNoteClicked(Note note, View v, AppCompatImageView imageWidget, LinearLayout noteList, View itemView);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNoteClicked = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteRepository = new NoteRepositoryImp();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout noteList = view.findViewById(R.id.notes_list_container);
        noteList.removeAllViews();
        List<Note> notes = noteRepository.getNotes();
        for (Note note : notes) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.note_item, noteList, false);
            TextView tx = itemView.findViewById(R.id.task);
            TextView dateTx = itemView.findViewById(R.id.date);
            AppCompatImageView imageIsCompleted = itemView.findViewById(R.id.task_complete);
            tx.setText(note.getName());
            dateTx.setText(note.getDateCreation());
            if (note.isCompleted()) imageIsCompleted.setImageResource(R.drawable.trophy_icon);
            else imageIsCompleted.setImageResource(R.drawable.hourglass_icon);
            AppCompatImageView editNote = itemView.findViewById(R.id.editClick);


            tx.setOnClickListener(v -> {
                if (onNoteClicked != null) {
                    onNoteClicked.onNoteClicked(note);
                }
            });

            dateTx.setOnClickListener(v -> {
                if (onDateEditClick != null) {
                    onDateEditClick.onDateEditClick(note, dateTx);
                }
            });

            editNote.setOnClickListener(v -> {
                if (onEditNoteClicked != null) {
                    onEditNoteClicked.onEditNoteClicked(note, v, imageIsCompleted, noteList,
                            itemView);
                }
            });
            noteList.addView(itemView);
        }
    }
}
