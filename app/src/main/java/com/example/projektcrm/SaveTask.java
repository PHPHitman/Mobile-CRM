package com.example.projektcrm;
import java.text.SimpleDateFormat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Date;
import java.util.Locale;

public class SaveTask extends AppCompatActivity {


    private EditText editTextCompany;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextDescription;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference zadaniaRef = db.collection("zadania");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_task);

        editTextCompany =findViewById(R.id.edit_text_company);
        editTextPhone =findViewById(R.id.edit_text_phone);
        editTextAddress =findViewById(R.id.edit_text_adress);
        editTextDescription =findViewById(R.id.edit_text_description);
    }

    public void saveJob(View v){
        String company = editTextCompany.getText().toString();
        String address = editTextAddress.getText().toString();
        String phone = editTextPhone.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
        String status="niewykonane";

        if (TextUtils.isEmpty(company)){
            editTextCompany.setError("Wymagana nazwa firmy!");
            return;
        }
        if (TextUtils.isEmpty(address)){
            editTextAddress.setError("Wymagany adres!");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            editTextPhone.setError("Wymagany telefon!");
            return;
        }
        if (TextUtils.isEmpty(description)){
            editTextDescription.setError("Wymagany opis zlecenia!");
            return;
        }

        Task task =  new Task(company, address, phone, description, date,status);

        zadaniaRef.add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SaveTask.this, "Zlecenie zostało dodane", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoadTask.class));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SaveTask.this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void back(View v){
        startActivity(new Intent(getApplicationContext(), LoggedBoss.class));
        finish();
    }

}
