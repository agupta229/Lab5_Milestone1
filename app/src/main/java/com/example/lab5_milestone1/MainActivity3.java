package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editView = (EditText) findViewById(R.id.writeNote);
        Intent intent = getIntent();
        int invalue = intent.getIntExtra("noteid", -1);

        noteid = invalue;
        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editView.setText(noteContent);
        }
    }

    public void saveMethod(View view) {
        EditText editView = (EditText) findViewById(R.id.writeNote);
        String noteText = editView.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usernameKey", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, noteText, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.saveNotes(username, title, noteText, date);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}