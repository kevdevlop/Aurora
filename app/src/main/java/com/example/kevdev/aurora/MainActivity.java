package com.example.kevdev.aurora;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevdev.aurora.Controller.ActivityLogin;
import com.example.kevdev.aurora.Controller.ActivityPrincipal;
import com.example.kevdev.aurora.Controller.ActivitySignup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!= null) {
                    Intent i = new Intent(MainActivity.this, ActivityPrincipal.class);
                    startActivity(i);
                    finish();
                }else {
                    setContentView(R.layout.activity_main);
                    btnLogin = (Button) findViewById(R.id.buttonLogIn);
                    btnSignUp = (Button) findViewById(R.id.buttonSignUP);
                }
            }
        };


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
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
