package com.ngodingyuk.myfbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngodingyuk.myfbapp.adapter.RequestsAdapter;
import com.ngodingyuk.myfbapp.model.Requests;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<Requests> listData;
    private RequestsAdapter requestAdapter;
    public Requests requests;

    private RecyclerView rvData;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        database = FirebaseDatabase.getInstance().getReference();

        rvData = findViewById(R.id.rv_data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(
                ListDataActivity.this,
                null,
                "Please Wait",
                true,
                false
        );

        database.child("Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listData = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){

                    Requests requests = noteDataSnapshot.getValue(Requests.class);
                    requests.setId(noteDataSnapshot.getKey());

                    listData.add(requests);
                }

                requestAdapter = new RequestsAdapter(listData, ListDataActivity.this);
                rvData.setAdapter(requestAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+" "+error.getMessage());
                loading.dismiss();
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add_item);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDataActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.DATA, requests);
                startActivity(intent);
            }
        });

    }
}