package com.example.projektcrm;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class TaskDetailsForEmployee extends AppCompatActivity {
    TextView mTextView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference zadaniaRef = db.collection("zadania");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_for_employee);
        mTextView = findViewById(R.id.taskDetailsForEmp);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        final String id = getIntent().getStringExtra("taskID");
        if(id!= null) {
            zadaniaRef.document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e == null) {
                        if (documentSnapshot.exists()) {
                            Object object = documentSnapshot.getData();

                            final ObjectMapper mapper = new ObjectMapper();
                            final Task task = mapper.convertValue(object, Task.class);

                            String company = task.getCompany();
                            String address = task.getAddress();
                            String phone = task.getPhone();
                            String description = task.getDescription();
                            String date = task.getDate();
                            String status = task.getStatus();


                            String data = "Opis: " + description + "\n\nNazwa firmy: " + company + "\nAdres: " + address + "\nTelefon: " + phone + "\nData dodania: " + date + "\nStatus: " + status;
                            mTextView.setText(data);
                        }
                    }

                }
            });
        }
    }

    public void delete(View v){
        final String id = getIntent().getStringExtra("taskID");
        zadaniaRef.document(id).delete();

        Toast.makeText(this, "Zlecenie usunięte!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoadTask.class));
        finish();
    }


    public void done(View v){
        final String id = getIntent().getStringExtra("taskID");
        zadaniaRef.document(id).update("status","wykonane");
        Toast.makeText(this, "Zmieniono status na: WYKONANE", Toast.LENGTH_SHORT).show();

    }

    public void undone(View v){
        final String id = getIntent().getStringExtra("taskID");
        zadaniaRef.document(id).update("status","niewykonane");
        Toast.makeText(this, "Zmieniono status na: NIEWYKONANE", Toast.LENGTH_SHORT).show();

    }

    public void back(View v){
        startActivity(new Intent(getApplicationContext(), LoadTaskForEmployee.class));
        finish();
    }

}
