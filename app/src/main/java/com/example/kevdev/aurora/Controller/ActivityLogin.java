package com.example.kevdev.aurora.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

/**
 * Created by KevDev on 03/12/16.
 */
public class ActivityLogin extends AppCompatActivity {
    private EditText userEmailT, passT;
    private Button btnLog, btnCan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        userEmailT = (EditText) findViewById(R.id.txtUsernameLogin);
        passT = (EditText) findViewById(R.id.editTextPassLogin);
        btnLog = (Button) findViewById(R.id.buttonLogin);
        btnCan = (Button) findViewById(R.id.btn_cancel);
    }

    public void btnToLogin(View v){

        String email = userEmailT.getText().toString().trim();
        String password = passT.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingresa el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingresa el password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Iniciando Sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this, "Bienvenido", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ActivityLogin.this, ActivityPrincipal.class);
                            startActivity(i);
                            finish();
                        }else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                if (e.getErrorCode().toString().equals("ERROR_WRONG_PASSWORD")
                                        || e.getErrorCode().toString().equals("ERROR_WEAK_PASSWORD")
                                        || e.getErrorCode().toString().equals("ERROR_USER_NOT_FOUND")) {
                                    Log.e("ERROR", "Error: " + e.getErrorCode().toString());
                                    Toast.makeText(ActivityLogin.this
                                            , getString(R.string.error_invalid_login)
                                            , Toast.LENGTH_SHORT).show();

                                } else if (e.getErrorCode().toString().equals("ERROR_INVALID_EMAIL")) {
                                    Log.e("ERROR", "Error: " + e.getErrorCode().toString());
                                    Toast.makeText(ActivityLogin.this
                                            , getString(R.string.error_invalid_email)
                                            , Toast.LENGTH_SHORT).show();

                                }


                            } catch(FirebaseAuthInvalidUserException e) {
                                Log.e("ERROR", "Error: " + e.getMessage().toString());
                                Toast.makeText(ActivityLogin.this
                                        , getString(R.string.error_invalid_login)
                                        , Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Log.e("Error e", e.getMessage());
                            }

                            if (progressDialog.isShowing()) {
                                progressDialog.cancel();
                            }
                        }
                    }
                });

    }
    public void btnToCancel(View v){
        Intent i = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(i);
    }
}
