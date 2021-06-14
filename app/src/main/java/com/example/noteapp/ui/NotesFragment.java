package com.example.noteapp.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

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
import com.example.noteapp.domain.NoteRepositoryImp;

import java.util.Calendar;
import java.util.List;

public class NotesFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener setListener;
    private NoteRepositoryImp noteRepository;
    private OnNoteClicked onNoteClicked;
    private onEditNoteClicked onEditNoteClicked;
    private onDateEditClick onDateEditClick;

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }


    public interface onEditNoteClicked {
        void onEditNoteClick(Note note, View v, AppCompatImageView imageWidget, LinearLayout noteList, View itemView);
    }

    public interface onDateEditClick {
        void onDateEditClick(Note note, View v, AppCompatImageView imageWidget, LinearLayout noteList, View itemView);
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

            editNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoteClicked != null) {
                        onEditNoteClicked.onEditNoteClick(note, v, imageIsCompleted, noteList,
                                itemView);
                    }
                }
            });

            //Открываем информацию о заметке по клику
            itemView.setOnClickListener(v -> {
                if (onNoteClicked != null) {
                    onNoteClicked.onNoteClicked(note);
                }
            });

            //Устанавливаем дату по клику
            dateTx.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                final int year = Calendar.YEAR;
                final int day = Calendar.DAY_OF_MONTH;
                final int month = Calendar.MONTH;
                setListener = (view1, year1, month1, dayOfMonth) -> {
                    String prefixDateCreation = getResources().getString(R.string.dateCreationText);
                    month1 = month1 + 1;
                    String date = String.format("%d.%d.%d", dayOfMonth, month1, year1);
                    note.setDate(date);
                    date = String.format("%s %s", prefixDateCreation, date);
                    dateTx.setText(date);

                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext()
                        , android.R.style.Theme_Holo_Light_Dialog
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            });
            noteList.addView(itemView);
        }
    }
}
