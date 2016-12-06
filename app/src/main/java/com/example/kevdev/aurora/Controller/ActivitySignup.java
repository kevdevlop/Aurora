package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;

/**
 * Created by KevDev on 03/12/16.
 */
public class ActivitySignup extends AppCompatActivity {
    EditText editTName, editTEmailUser, editTPassword, editTPasswordConfirm;
    Button btnSignUp, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTName = (EditText) findViewById(R.id.editTxtName);
        editTEmailUser = (EditText) findViewById(R.id.EditTxtEmailUser);
        editTPassword = (EditText) findViewById(R.id.editTextPassword);
        editTPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        btnSignUp = (Button) findViewById(R.id.btn_toSignUp);
        btnCancel = (Button) findViewById(R.id.btn_toCancel);

    }

    public void toSignUp(View v) {
        Toast.makeText(this, "Felicidades: Registro Exitoso", Toast.LENGTH_LONG).show();
        Intent i = new Intent(ActivitySignup.this, ActivityLogin.class);
        startActivity(i);
    }

    public void btn_toCancel(View v){
        Intent i = new Intent(ActivitySignup.this, MainActivity.class);
        startActivity(i);
    }
}
