package com.example.noteapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String name;
    private String description;
    private String dateCreation;
    private boolean isCompleted;

    public Note(String name, String description, String dateCreation, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.dateCreation = dateCreation;
        this.isCompleted = isCompleted;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateCreation = in.readString();
        isCompleted = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDate(String newDate) {
        this.dateCreation = newDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dateCreation);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
    }
}