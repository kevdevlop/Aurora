package com.example.kevdev.aurora.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevdev.aurora.R;

/**
 * Created by KevDev on 03/12/16.
 */
public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName=(EditText)findViewById(R.id.txtUsernameLogin);
        editTextPassword=(EditText)findViewById(R.id.editTextPassLogin);
        btnLogIn=(Button)findViewById(R.id.buttonLogin);
        btnToSignUp = (Button)findViewById(R.id.btn_toSignUp);

        conexionDB = new ConexionDB(this);
        conexionDB = conexionDB.open();

    }
}
