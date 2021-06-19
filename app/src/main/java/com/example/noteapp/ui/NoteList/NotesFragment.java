package com.example.noteapp.ui.NoteList;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.domain.NoteRepositoryImp;

import java.util.ArrayList;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNoteClicked = null;
        onEditNoteClicked = null;
        onDateEditClick = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Note> date = new ArrayList<Note>();
        date.add(new Note("Сделать уборку", "Убрать свою комнату", "Дата создания: 15.01.1996", true));
        date.add(new Note("Сделать домашку", "Сделать задание №1", "Дата создания: 16.01.1996", false));
        date.add(new Note("Заплатить за интернет", "По счету №34543234", "Дата создания: 17.01.1996", true));
        date.add(new Note("Починить велосипед", "Починить велик!", "Дата создания: 17.01.1996", true));
        date.add(new Note("Купить тетрадки", "Купить тетради для лекций", "Дата создания: 17.01.1996", false));
        noteRepository = new NoteRepositoryImp(date);
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
        NotesAdapter notesAdapter = new NotesAdapter();
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

}
