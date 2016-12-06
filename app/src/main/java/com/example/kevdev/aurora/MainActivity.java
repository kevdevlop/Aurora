package com.example.kevdev.aurora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevdev.aurora.Controller.ActivityLogin;
import com.example.kevdev.aurora.Controller.ActivitySignup;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.buttonLogIn);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);
    }

    public void SignUp(View v) {
        Intent i = new Intent(MainActivity.this, ActivitySignup.class);
        startActivity(i);
    }

    public void LogIn(View v){
        Intent i = new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(i);
    }
}
