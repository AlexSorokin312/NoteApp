package com.example.noteapp.ui.NoteAdd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.domain.NoteRepositoryImp;
import com.example.noteapp.ui.NoteInfo.NoteInfoFragment;
import com.example.noteapp.ui.NoteList.NotesAdapter;

import java.util.List;

public class NoteAdd extends Fragment {

    private NoteRepositoryImp noteRepository = NoteRepositoryImp.INSTANCE;

    public NoteAdd() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText note_name = view.findViewById(R.id.note_name_from_user);
        EditText desc_text = view.findViewById(R.id.desc_from_user);
        Button btn_add_new_note = view.findViewById(R.id.button_add_new_note);




        btn_add_new_note.setOnClickListener(v -> {

            noteRepository.addNote(note_name.getText().toString(),  desc_text.getText().toString(), "05.02.1996", true);
            List<Note>notes = noteRepository.getNotes();
        });
    }


}