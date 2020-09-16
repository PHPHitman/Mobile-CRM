package com.example.projektcrm;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class LoggedBoss extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_boss);
    }
    public void logout(View view){

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }

    public void AddTask(View view){
        startActivity(new Intent(getApplicationContext(), SaveTask.class));
        finish();
    }

    public void LoadTask(View view){
        startActivity(new Intent(getApplicationContext(), LoadTask.class));
        finish();
    }
    public void AddEmployee(View view){
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }


}
