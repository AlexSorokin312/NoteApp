package com.example.noteapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.example.noteapp.ui.NoteInfo.NoteEdit.EditNoteFragment;
import com.example.noteapp.ui.NoteInfo.NoteInfoFragment;
import com.example.noteapp.ui.NoteList.NotesFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        NotesFragment.OnNoteClicked,
        NotesFragment.onEditNoteClicked,
        NotesFragment.onDateEditClick,
        EditNoteFragment.OnSaveClickListener{

    private DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes, new NotesFragment(), "MainFragment")
                .commit();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.edit_profile)
                Toast.makeText(getApplicationContext(), "Редактировать профиль", Toast.LENGTH_SHORT).show();
            if (item.getItemId() == R.id.settings)
                Toast.makeText(getApplicationContext(), "Настройки", Toast.LENGTH_SHORT).show();
            if (item.getItemId() == R.id.about_app)
                Toast.makeText(getApplicationContext(), "О приложении", Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    //Открытие заметки по нажатию
    @Override
    public void onNoteClicked(Note note) {
        boolean isLandscaped = getResources().getBoolean(R.bool.isLandscape);
        if (isLandscaped) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_details, NoteInfoFragment.newInstance(note))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes, NoteInfoFragment.newInstance(note), "NOTE_LIST")
                    .addToBackStack(null)
                    .commit();
        }
    }

    //Нажатие на кнопку редактирования даты
    @Override
    public void onDateEditClick(Note note, TextView dateTx) {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this
                , android.R.style.Theme_Holo_Light_Dialog
                , setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    //Нажатие на кнопку редактирования заметки
    @Override
    public void onEditNoteClicked(Note note, View v, AppCompatImageView imageWidget, RecyclerView noteList, View itemView) {
        PopupMenu menu = new PopupMenu(getApplicationContext(), v);
        getMenuInflater().inflate(R.menu.menu_edit, menu.getMenu());

        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.edit) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.notes, new EditNoteFragment().newInstance(note))
                        .addToBackStack(null)
                        .commit();
            }

            if (item.getItemId() == R.id.makeCompleted) {
                note.setCompleted(true);
                imageWidget.setImageResource(R.drawable.trophy_icon);
            }
            if (item.getItemId() == R.id.makeNotCompleted) {
                note.setCompleted(false);
                imageWidget.setImageResource(R.drawable.hourglass_icon);
            }
            if (item.getItemId() == R.id.delete) {
                Toast.makeText(MainActivity.this, String.format(" Удалить %s", note.getName().toString()), Toast.LENGTH_SHORT).show();
                noteList.removeView(itemView);
            }
            return true;
        });
        menu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.select_all) {
            Toast.makeText(getApplicationContext(), "Выбрать все заметки", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.show_completed) {
            Toast.makeText(getApplicationContext(), "Показать выполненные", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.show_uncompleted) {
            Toast.makeText(getApplicationContext(), "Показать невыполненные", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveClickListener(Note note, String name, String description) {
        note.setName(name);
        note.setDescription(description);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes, new NotesFragment(), "MainFragment")
                .commit();
    }
}