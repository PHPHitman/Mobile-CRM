package com.example.projektcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail      = findViewById(R.id.logEmail);
        mPassword   = findViewById(R.id.logPassword);
        mLoginBtn   = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBarLogin);
        fAuth       = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //email and password validation
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Wymagany adres email!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Hasło jest wymagane!");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Hasło musi posiadać co najmniej 6 znaków!");
                    return;
                }
                //Authenticate user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String kemail= mEmail.getText().toString();
                            if(kemail.equals("boss@crm.pl")) {

                                Toast.makeText(Login.this, "Zalogowano poprawnie", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoggedBoss.class));
                            }
                            else{
                                startActivity(new Intent(getApplicationContext(), LoggedEmployee.class));
                            }

                        }
                        else{
                            Toast.makeText(Login.this, "Niepoprawny email lub hasło!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}
