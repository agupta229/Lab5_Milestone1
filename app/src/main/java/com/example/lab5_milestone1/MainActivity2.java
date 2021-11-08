package com.example.lab5_milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView welcome_mess;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcome_mess = (TextView) findViewById(R.id.welcome_message);
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("usernameKey", "");
        welcome_mess.setText("Hello " + str + "!");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(str);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("nodeid", position);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.signout) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}