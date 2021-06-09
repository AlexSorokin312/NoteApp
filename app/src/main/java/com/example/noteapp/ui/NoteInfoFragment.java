package com.example.noteapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;


public class NoteInfoFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";

    public NoteInfoFragment() {
        // Required empty public constructor
    }

    public static NoteInfoFragment newInstance(Note note) {
        NoteInfoFragment f = new NoteInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Note note = getArguments().getParcelable(ARG_NOTE);
        super.onViewCreated(view, savedInstanceState);

        TextView heading = view.findViewById(R.id.task_name);
        TextView description = view.findViewById(R.id.task_description);

        heading.setText(note.getName());
        description.setText(note.getDescription());
    }
}