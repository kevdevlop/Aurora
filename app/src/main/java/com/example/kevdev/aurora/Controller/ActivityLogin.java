package com.example.kevdev.aurora.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * Created by KevDev on 03/12/16.
 */
public class ActivityLogin extends AppCompatActivity {

    private static final int RC_LOGIN = 9001;
    private static final String TAG = "ActivityLogin";
    private EditText userEmailT, passT;
    private Button btnLog, btnCan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Metodo para la creacion de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se establece el contenido de la activity, haciendo referencia al XML
        setContentView(R.layout.activity_login);

        //Se obtiene la instancia del usuario autenticado
        firebaseAuth = FirebaseAuth.getInstance();

        // Se inicializa la dialogo de progreso
        progressDialog = new ProgressDialog(this);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //Se inicializan los elementos de la Vista(Activity)
        userEmailT = (EditText) findViewById(R.id.txtUsernameLogin);
        passT = (EditText) findViewById(R.id.editTextPassLogin);
        btnLog = (Button) findViewById(R.id.buttonLogin);
        btnCan = (Button) findViewById(R.id.btn_cancel);
    }

    // Metodo en el cual se hace click en el boton login
    public void btnToLogin(View v){

        //Se obtienen los datos ingresados por el usuario
        String email = userEmailT.getText().toString().trim();
        String password = passT.getText().toString().trim();

        //Se verifica que no sean vacios
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingresa el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingresa el password", Toast.LENGTH_SHORT).show();
            return;
        }
        //Se notifica el progreso
        progressDialog.setMessage("Iniciando Sesión...");
        progressDialog.show();

        //Se inicia la identificacion con el email y el password
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //Cuando se completa el proceso
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        //Verificamos el resultado de la identificacion
                        if (task.isSuccessful()) {
                            //Si se logro identificar el usuario se inicia la vista principal
                            Toast.makeText(ActivityLogin.this, "Bienvenido", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ActivityLogin.this, ActivityPrincipal.class);
                            startActivity(i);
                            finish();
                        }else {
                            //En caso de no identificarse, se manejan las excepciones
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

    //Click en boton ResetPassword
    public void btnResetPass(View v){
        Intent i = new Intent(this, ActivityResetPassword.class);
        startActivity(i);
    }
    //clck en boton cancelar
    public void btnToCancel(View v){
        finish();
    }

}
