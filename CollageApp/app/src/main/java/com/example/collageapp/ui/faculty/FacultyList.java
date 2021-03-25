package com.example.collageapp.ui.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collageapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyList extends AppCompatActivity {
    private String departmentName;
    private ArrayList<Faculty> deptFacultyList =new ArrayList<>();
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference, dbRef;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private LinearLayout noData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        progressDialog = new ProgressDialog(this);

        departmentName = getIntent().getStringExtra("department");

        nestedScrollView = findViewById(R.id.nestedScrollView);
        recyclerView = findViewById(R.id.recycleView);

        noData = findViewById(R.id.noData);
        generateFacultyList();
    }

    private void generateFacultyList() {

        dbRef = databaseReference.child(departmentName);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    noData.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                } else {
                    noData.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Faculty faculty = dataSnapshot.getValue(Faculty.class);
                        deptFacultyList.add(faculty);

                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FacultyList.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    FacultyAdaptor facultyAdapter = new FacultyAdaptor(deptFacultyList, FacultyList.this);
                    recyclerView.setAdapter(facultyAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FacultyList.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}