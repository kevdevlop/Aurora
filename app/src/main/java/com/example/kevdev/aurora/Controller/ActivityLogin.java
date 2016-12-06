package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;

/**
 * Created by KevDev on 03/12/16.
 */
public class ActivityLogin extends AppCompatActivity {
    EditText userEmailT, passT;
    Button btnLog, btnCan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailT = (EditText) findViewById(R.id.txtUsernameLogin);
        passT = (EditText) findViewById(R.id.editTextPassLogin);
        btnLog = (Button) findViewById(R.id.buttonLogin);
        btnCan = (Button) findViewById(R.id.btn_cancel);
    }

    public void btnToLogin(View v){
        Intent i = new Intent(ActivityLogin.this, ActivityPrincipal.class);
        startActivity(i);
    }
    public void btnToCancel(View v){
        Intent i = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(i);
    }
}
