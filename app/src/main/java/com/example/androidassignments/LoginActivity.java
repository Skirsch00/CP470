package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(ACTIVITY_NAME, "In onCreate()");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activateSharedPrefs();

    }

    public void activateSharedPrefs()
    {
        Button loginButton = (Button) findViewById(R.id.loginButton);
        EditText emailText = (EditText) findViewById(R.id.loginEmail);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        loginButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                edit.putString("DefaultEmail", email);
                edit.commit();
                mainActivity();
            }
        });

        String email = sharedPreferences.getString("DefaultEmail", " ");
        emailText.setText(email);
    }


    public void mainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }
}