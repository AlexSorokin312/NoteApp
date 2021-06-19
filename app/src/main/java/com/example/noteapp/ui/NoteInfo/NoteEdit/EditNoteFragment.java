package com.example.noteapp.ui.NoteInfo.NoteEdit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.ui.NoteInfo.NoteInfoFragment;
import com.example.noteapp.ui.NoteList.NotesFragment;


public class EditNoteFragment extends Fragment {

    private OnSaveClickListener onSaveClickListener;

    public interface OnSaveClickListener {
        public void onSaveClickListener(Note note, String name, String description);
    }

    public static final String ARG_NOTE = "ARG_NOTE";

    public EditNoteFragment() {
        // Required empty public constructor
    }

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment f = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditNoteFragment.OnSaveClickListener)
            onSaveClickListener = (EditNoteFragment.OnSaveClickListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Note note = getArguments().getParcelable(ARG_NOTE);

        EditText editTitle = view.findViewById(R.id.editName);
        EditText editDescription = view.findViewById(R.id.editDescription);
        Button btn_save = view.findViewById(R.id.button_save);

        editTitle.setHint(note.getName());
        editDescription.setHint(note.getDescription());



        btn_save.setOnClickListener(v -> {
            if (onSaveClickListener != null) {
                String noteName = editTitle.getText().toString();
                String noteDescription = editDescription.getText().toString();
                onSaveClickListener.onSaveClickListener(note, noteName, noteDescription);
            }
        });
    }
}