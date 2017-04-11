package com.example.kevdev.aurora.Controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.UserModel;
import com.example.kevdev.aurora.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by KevDev on 03/12/16.
 */
public class ActivitySignup extends AppCompatActivity {
    private EditText  editTEmailUser;
    private EditText   editTPassword;
    private EditText editTPasswordConfirm;
    private EditText editUserName;
    private Button btnSignUp;
    private Button btnCancel;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        editUserName =  (EditText) findViewById((R.id.editTxtName));
        editTEmailUser = (EditText) findViewById(R.id.EditTxtEmailUser);
        editTPassword = (EditText) findViewById(R.id.editTextPassword);
        editTPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        btnSignUp = (Button) findViewById(R.id.btn_toSignUp);
        btnCancel = (Button) findViewById(R.id.btn_toCancel);

    }

    public void toSignUp(View v) {

        final String userName = editUserName.getText().toString().trim();
        final String email = editTEmailUser.getText().toString().trim();
        String password = editTPassword.getText().toString().trim();
        String confirmPass = editTPasswordConfirm.getText().toString().trim();

        //Validamos que los campos ingresados no esten vacios
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingresa el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingresa el password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Las passwords no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        //Mostrando el progreso
        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.show();

        //Se crea un nuevo usuario con el email y password ingresados
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        //Verificamos que no exista algun error
                        if (task.isSuccessful()) {
                            //Se notifica que se hizo satisfactoriamente
                            Toast.makeText(ActivitySignup.this
                                    , "Usuario registrado correctamente"
                                    , Toast.LENGTH_SHORT).show();
                            //Se crea el nuevo usuario en la BD
                            grabarUsuario(userName, email);
                            //Se redirecciona a la vista de login
                            Intent i = new Intent(ActivitySignup.this, ActivityLogin.class);
                            startActivity(i);
                            finish();

                        } else {
                            //Si existen errores informamos al usuario con las excepciones
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(ActivitySignup.this
                                        , getString(R.string.error_invalid_password)
                                        , Toast.LENGTH_SHORT).show();

                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(ActivitySignup.this
                                        , getString(R.string.error_invalid_email)
                                        , Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(ActivitySignup.this
                                        , getString(R.string.error_email_exist)
                                        , Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Log.e("Error e", e.getMessage());
                            }
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(ActivitySignup.this
                                    , "Error: "+task.isSuccessful()
                                    , Toast.LENGTH_SHORT).show();

                            if (progressDialog.isShowing()) {
                                progressDialog.cancel();
                            }
                        }
                    }
                });
    }

    private void grabarUsuario(String userName, String email) {

        //Se obtiene la instancia del usuario
        FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
        //Creamos un nuevo usuario
        UserModel userM = new UserModel(userName, email);
        //obtenemos la referencia de la tabla users
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        //Obtenemos el ID del usuario
        String userId = userF.getUid();
        //Se escribe el nuevo usuario en la BD
        ref.child(userId).setValue(userM);
    }


    public void btn_toCancel(View v){
        //Se finaliza la vista actual al presionar cancelar
        finish();
    }
}
