package com.example.projektcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText mName, mSurname, mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       mName    = findViewById(R.id.regName);
       mSurname = findViewById(R.id.regSurname);
       mPhone   = findViewById(R.id.regPhone);
       mEmail   = findViewById(R.id.regEmail);
       mPassword= findViewById(R.id.regPassword);
       mRegisterBtn = findViewById(R.id.btnRegister);
       mLoginBtn    = findViewById(R.id.loginButton);

       fAuth        = FirebaseAuth.getInstance();
       fStore   =FirebaseFirestore.getInstance();
       progressBar  = findViewById(R.id.progressBar);

       //Check that user is logged
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

       mRegisterBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String email = mEmail.getText().toString().trim();
               String password = mPassword.getText().toString().trim();
               final String name = mName.getText().toString();
               final String phone = mPhone.getText().toString();
               final String surname = mSurname.getText().toString();

               //register validation
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

               //Set progressbar visible
               progressBar.setVisibility(View.VISIBLE);

                //Register the user
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Pracownik dodany", Toast.LENGTH_SHORT).show();


                                //Save user data in collection "users"
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference =fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("mName", name);
                                user.put("mSurname",surname );
                                user.put("email", email);
                                user.put("phone", phone);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "Sukces! Profil został stworzony dla użytkownika "+userID);
                                    }
                                });



                            startActivity(new Intent(getApplicationContext(), LoggedBoss.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Wystąpił błąd" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

           }
           });
    }
    public void back(View v){
        startActivity(new Intent(getApplicationContext(), LoggedBoss.class));
        finish();
    }
}
