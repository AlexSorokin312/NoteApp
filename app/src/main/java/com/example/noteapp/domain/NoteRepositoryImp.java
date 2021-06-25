package com.example.noteapp.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImp implements NoteRepository, Parcelable {

    public static final NoteRepositoryImp INSTANCE = new NoteRepositoryImp();
    public ArrayList<Note> result = new ArrayList<>();

    public NoteRepositoryImp() {
        result.clear();
        result.add(new Note("Сделать уборку", "Убрать свою комнату", "Дата создания: 15.01.1996", true));
        result.add(new Note("Сделать домашку", "Сделать задание №1", "Дата создания: 16.01.1996", false));
        result.add(new Note("Заплатить за интернет", "По счету №34543234", "Дата создания: 17.01.1996", true));
        result.add(new Note("Починить велосипед", "Починить велик!", "Дата создания: 17.01.1996", true));
        result.add(new Note("Купить тетрадки", "Купить тетради для лекций", "Дата создания: 17.01.1996", false));
    }


    protected NoteRepositoryImp(Parcel in) {
        result = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NoteRepositoryImp> CREATOR = new Creator<NoteRepositoryImp>() {
        @Override
        public NoteRepositoryImp createFromParcel(Parcel in) {
            return new NoteRepositoryImp(in);
        }

        @Override
        public NoteRepositoryImp[] newArray(int size) {
            return new NoteRepositoryImp[size];
        }
    };

    public ArrayList<Note> getNotes() {
        return result;
    }

    @Override
    public void deleteElement(Note note) {
        result.remove(note);
    }

    @Override
    public void deleteAll() {
        result.clear();
    }

    @Override
    public Note addNote(String name, String description, String dateCreation, boolean isCompleted) {
        Note note = new Note(name, description, dateCreation, true);
        result.add(note);
        return note;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(result);
    }
}
