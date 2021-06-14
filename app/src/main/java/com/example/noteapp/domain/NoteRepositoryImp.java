package com.example.noteapp.domain;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NoteRepositoryImp {

    public ArrayList<Note> getNotes() {

        ArrayList<Note> result = new ArrayList<>();
        //Пока без базы данных добавляем непосредственно здесь
        result.add(new Note("Сделать уборку", "Убрать свою комнату", "15.01.1996", true));
        result.add(new Note("Сделать домашку", "Сделать задание №1", "16.01.1996", false));
        result.add(new Note("Заплатить за интернет", "По счету №34543234", "17.01.1996", true));
        result.add(new Note("Починить велосипед", "Починить велик!", "17.01.1996", true));
        result.add(new Note("Купить тетрадки", "Купить тетради для лекций", "17.01.1996", false));
        return result;
    }

}
