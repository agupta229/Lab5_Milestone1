package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static String usernameKey = "usernameKey";

    public void clickFunction(View view) {
        EditText usern = (EditText) findViewById(R.id.username);
        String str1 = usern.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        String str2 = pass.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(usernameKey, str1).apply();

        goToActivity2(str1);
    }

    public void goToActivity2(String s) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            String user = sharedPreferences.getString(usernameKey, "");
            goToActivity2(user);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }
}