package com.example.noteapp.ui.NoteList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> notes = new ArrayList<>();

    public void setData(List<Note> toSet) {
        notes.addAll(toSet);
    }

    //Нажатие на кнопку перехода в заметку

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked onNoteClickedListener;

    public OnNoteClicked getOnNoteClickedListener() {
        return onNoteClickedListener;
    }

    public void setOnNoteClickedListener(OnNoteClicked onNoteClickedListener) {
        this.onNoteClickedListener = onNoteClickedListener;
    }


    //Нажатие на кнопку редактирования заметки
    private onEditNoteClicked onEditNoteClicked;

    public interface onEditNoteClicked {
        void onEditNoteClicked(Note note, View v, AppCompatImageView imageWidget, View itemView);
    }


    public onEditNoteClicked getOnEditNoteClicked() {
        return onEditNoteClicked;
    }

    public void setOnEditNoteClicked(onEditNoteClicked onEditNoteClicked) {
        this.onEditNoteClicked = onEditNoteClicked;
    }


    //Нажатие на кнопку редактирования даты
    private OnDateEditClick onDateEditClick;

    public interface OnDateEditClick {
        void onDateEditClick(Note note, TextView dateTx);
    }

    public OnDateEditClick getOnDateEditClick() {
        return onDateEditClick;
    }

    public void setOnDateEditClick(OnDateEditClick onDateEditClick) {
        this.onDateEditClick = onDateEditClick;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteViewHolder holder, int position) {

        Note note = notes.get(position);

        holder.tx.setText(note.getName());
        holder.dateTx.setText(note.getDateCreation());
        if (note.isCompleted()) holder.imageIsCompleted.setImageResource(R.drawable.trophy_icon);
        else holder.imageIsCompleted.setImageResource(R.drawable.hourglass_icon);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tx;
        TextView dateTx;
        AppCompatImageView imageIsCompleted;
        AppCompatImageView editNote;

        public NoteViewHolder(@NonNull View itemView) {

            super(itemView);
            tx = itemView.findViewById(R.id.task);
            dateTx = itemView.findViewById(R.id.date);
            imageIsCompleted = itemView.findViewById(R.id.task_complete);
            editNote = itemView.findViewById(R.id.editClick);
            itemView.findViewById(R.id.editClick);

            tx.setOnClickListener(v -> {
                if (getOnNoteClickedListener() != null) {
                    getOnNoteClickedListener().onNoteClicked(notes.get(getAdapterPosition()));
                }
            });

            editNote.setOnClickListener(v -> {
                if (getOnEditNoteClicked() != null) {
                    getOnEditNoteClicked().onEditNoteClicked(notes.get(getAdapterPosition()), v, imageIsCompleted, itemView);
                }
            });

            dateTx.setOnClickListener(v -> {
                if (getOnDateEditClick() != null) {
                    getOnDateEditClick().onDateEditClick(notes.get(getAdapterPosition()), dateTx);
                }
            });

        }
    }
}
