package com.example.projektcrm;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class LoadTask extends AppCompatActivity {

    private ListView mListView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference zadaniaRef = db.collection("zadania");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_task);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mListView = findViewById(R.id.listView);


        // Initializing a new String Array
        String[] tasks = new String[] {

        };

        //List of task
        final List<String> task_list = new ArrayList<String>(Arrays.asList(tasks));

        // Create an ArrayAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, task_list);

        mListView.setAdapter(arrayAdapter);


        zadaniaRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                int i=0;

                //Array initialized for 300 elements;
                final String[] tasksIdArray = new String[300];
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Task task = documentSnapshot.toObject(Task.class);
                    task.setDocumentID(documentSnapshot.getId());

                    final String documentID = task.getDocumentID();
                    final String description = task.getDescription();

                    tasksIdArray[i]=documentID;
                    i++;

                    task_list.add(description);
                    arrayAdapter.notifyDataSetChanged();

                }

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            final Intent intent = new Intent(LoadTask.this, TaskDetails.class);
                            int pos = parent.getPositionForView(view);

                            String taskID = tasksIdArray[pos];
                            intent.putExtra("taskID", ""+taskID);
                            startActivity(intent);
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
