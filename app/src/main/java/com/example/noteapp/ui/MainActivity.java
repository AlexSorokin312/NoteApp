package com.example.noteapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.noteapp.R;
import com.example.noteapp.domain.Note;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NotesFragment.OnNoteClicked,
        NotesFragment.onEditNoteClicked,
        NotesFragment.onDateEditClick {

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
                .replace(R.id.notes, new NotesFragment())
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.edit_profile)
                    Toast.makeText(getApplicationContext(), "Редактировать профиль", Toast.LENGTH_SHORT).show();
                if (item.getItemId() == R.id.settings)
                    Toast.makeText(getApplicationContext(), "Настройки", Toast.LENGTH_SHORT).show();
                if (item.getItemId() == R.id.about_app)
                    Toast.makeText(getApplicationContext(), "О приложении", Toast.LENGTH_SHORT).show();
                return false;
            }
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
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes, NoteInfoFragment.newInstance(note))
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onDateEditClick(Note note, View v, AppCompatImageView imageWidget, LinearLayout noteList, View itemView) {

    }

    //Нажатие на кнопку редактирования заметки
    @Override
    public void onEditNoteClick(Note note, View v, AppCompatImageView imageWidget, LinearLayout noteList, View itemView) {
        PopupMenu menu = new PopupMenu(getApplicationContext(), v);
        getMenuInflater().inflate(R.menu.menu_edit, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit)
                    Toast.makeText(getApplicationContext(), "Редактировать", Toast.LENGTH_SHORT).show();
                if (item.getItemId() == R.id.makeCompleted) {
                    note.setCompleted(true);
                    imageWidget.setImageResource(R.drawable.trophy_icon);
                }
                if (item.getItemId() == R.id.makeNotCompleted) {
                    note.setCompleted(false);
                    imageWidget.setImageResource(R.drawable.trophy_icon);
                }
                if (item.getItemId() == R.id.delete) {
                    noteList.removeView(itemView);
                }
                return false;
            }
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
        if (itemId == R.id.option_one) {
            Toast.makeText(getApplicationContext(), "Option_one", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}