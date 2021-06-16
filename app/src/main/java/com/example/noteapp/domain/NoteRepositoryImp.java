package com.example.noteapp.domain;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImp implements NoteRepository {


    public ArrayList<Note> getNotes() {
        ArrayList<Note> result = new ArrayList<>();
        //Пока без базы данных добавляем непосредственно здесь
        result.add(new Note("Сделать уборку", "Убрать свою комнату", "Дата создания: 15.01.1996", true));
        result.add(new Note("Сделать домашку", "Сделать задание №1", "Дата создания: 16.01.1996", false));
        result.add(new Note("Заплатить за интернет", "По счету №34543234", "Дата создания: 17.01.1996", true));
        result.add(new Note("Починить велосипед", "Починить велик!", "Дата создания: 17.01.1996", true));
        result.add(new Note("Купить тетрадки", "Купить тетради для лекций", "Дата создания: 17.01.1996", false));
        return result;
    }

    @Override
    public void deleteElement(Note note) {
        List l = getNotes();
        l.remove(note);
    }

    @Override
    public void addElement(Note note) {

    }


}
