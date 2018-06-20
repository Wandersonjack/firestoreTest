package com.firebaseapp.wanderson_jackson.firestoretestapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private String TAG= "Firestore";
    private UserListAdapter userListAdapter;
    private List<Users> usersList;
    private RecyclerView rvMainList;
    private EditText name, job;
    private Button button_add;

    private    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.editText_name);
        job = findViewById(R.id.editText_job);

        button_add = findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerJob();
            }
        });


        usersList = new ArrayList<>();
        userListAdapter = new UserListAdapter(usersList);

        rvMainList  = findViewById(R.id.main_list);
        rvMainList.setHasFixedSize(true);
        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setAdapter(userListAdapter);

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                try{
                    if(e != null){
                        Log.d(TAG, "Error: " + e.getMessage());
                    }
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){                  //this makes the modification on changes added

                        if(doc.getType() == DocumentChange.Type.ADDED){
                            Users users = doc.getDocument().toObject(Users.class);
                            usersList.add(users);
                            userListAdapter.notifyDataSetChanged();

                        }

                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

    }




    /**method that register data*/
    public void registerJob(){
        Map<String, Object> user = new HashMap<>();
        user.put("name","jose");
        user.put("job", "ui desin");

        mFirestore.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

    }


    /**getting data from form

    private Users getUserFromForm(){
        String nome = name.getText().toString();
        String trabalho = job.getText().toString();
        Users usersObject = new Users(nome , trabalho);
        return usersObject;
    }
     */





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
