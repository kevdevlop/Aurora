package com.example.kevdev.aurora.Controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kevdev.aurora.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

/**
 * Created by KevDev on 01/04/17.
 */
//Vista para restablecer el password
public class ActivityResetPassword extends AppCompatActivity {
    private EditText editTextEmail;
    private Button btnReset;
    private Button btnCancel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail = (EditText) findViewById(R.id.editTxtEmail);
        btnCancel = (Button) findViewById(R.id.buttonCancel);
        btnReset = (Button) findViewById(R.id.buttonReset);

        firebaseAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Por favor ingresa el email:"+ email
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("Email",email);

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ActivityResetPassword.this
                                            , "Te hemos enviado las intrucciones " +
                                                    "para recuperar tu password"
                                            , Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ActivityResetPassword.this
                                            , "Error al enviar email!"
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
