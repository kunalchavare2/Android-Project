package com.example.collegeadminapp.faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeadminapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity implements View.OnClickListener {
    private CardView csDept, itDept, machDept, civilDept, entcDept, electricDept, autoDept;
    private FloatingActionButton fab;
    Toolbar updateFacultyToolbar;
   // private RecyclerView csDept, itDept, machDept, civilDept, entcDept, electricDept, autoDept;
    private LinearLayout csDeptNoData, itDeptNoData, autoDeptNoData, machDeptNoData, civilDeptNoData, entcDeptNoData, electricDeptNoData;
    private final List<Faculty> itDeptList = new ArrayList<>();
    private final List<Faculty> machDeptList = new ArrayList<>();
    private final List<Faculty> autoDeptList = new ArrayList<>();
    private final List<Faculty> civilDeptList = new ArrayList<>();
    private final List<Faculty> entcDeptList = new ArrayList<>();
    private final List<Faculty> electricDeptList = new ArrayList<>();
    private final List<Faculty> csDeptList = new ArrayList<>();

    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference, dbRef;
    //   private FacultyAdaptor facultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        progressDialog = new ProgressDialog(this);

        updateFacultyToolbar =findViewById(R.id.topAppBar);
        setSupportActionBar(updateFacultyToolbar);

        fab = findViewById(R.id.addFacultyFab);

        csDept = findViewById(R.id.csDeptCard);
        electricDept = findViewById(R.id.electricDeptCard);
        entcDept = findViewById(R.id.entcDeptCard);
        civilDept = findViewById(R.id.civilDeptCard);
        itDept = findViewById(R.id.itDeptCard);
        machDept = findViewById(R.id.machDeptCard);
        autoDept = findViewById(R.id.autoDeptCard);

        csDept.setOnClickListener(this);
        electricDept.setOnClickListener(this);
        entcDept.setOnClickListener(this);
        civilDept.setOnClickListener(this);
        itDept.setOnClickListener(this);
        machDept.setOnClickListener(this);
        autoDept.setOnClickListener(this);
//
//
//        csDeptNoData = findViewById(R.id.csNoData);
//        electricDeptNoData = findViewById(R.id.electricalDeptNoData);
//        entcDeptNoData = findViewById(R.id.entcDeptNoData);
//        civilDeptNoData = findViewById(R.id.civilDeptNoData);
//        itDeptNoData = findViewById(R.id.itDeptNoData);
//        machDeptNoData = findViewById(R.id.machDeptNoData);
//        autoDeptNoData = findViewById(R.id.autoDeptNoData);


//
//        setDeptRecyclerView("Computer", csDeptList, csDept, csDeptNoData);
//        setDeptRecyclerView("Electronics & Telecommunication", entcDeptList, entcDept, entcDeptNoData);
//        setDeptRecyclerView("Mechanical", machDeptList, machDept, machDeptNoData);
//        setDeptRecyclerView("Electrical", electricDeptList, electricDept, electricDeptNoData);
//        setDeptRecyclerView("Information Technology", itDeptList, itDept, itDeptNoData);
//        setDeptRecyclerView("Civil", civilDeptList, civilDept, civilDeptNoData);
//        setDeptRecyclerView("Auto Mobile", autoDeptList, autoDept, autoDeptNoData);
//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateFaculty.this, AddFaculty.class);
                startActivity(intent);
            }
        });
    }


    private void setDeptRecyclerView(String departmentName, List<Faculty> deptList, RecyclerView deptRecyclerView, LinearLayout noData) {
        AsyncTaskRunner runner = new AsyncTaskRunner(departmentName,deptList,deptRecyclerView,noData);

        runner.execute();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(this, FacultyList.class);
        switch (v.getId()) {
            case R.id.csDeptCard:
                intent.putExtra("department", "Computer");
                break;
            case R.id.itDeptCard:
                intent.putExtra("department", "Information Technology");

                break;
            case R.id.machDeptCard:
                intent.putExtra("department", "Mechanical");

                break;
            case R.id.autoDeptCard:
                intent.putExtra("department", "Auto Mobile");
                break;
            case R.id.civilDeptCard:
                intent.putExtra("department", "Civil");
                break;
            case R.id.electricDeptCard:
                intent.putExtra("department", "Electrical");
                break;
            case R.id.entcDeptCard:
                intent.putExtra("department", "Electronic and Telecommunication");
                break;
            default:
                Toast.makeText(this, "Invalid Navigation!", Toast.LENGTH_SHORT).show();
                return ;
        }
        startActivity(intent);
    }


    private  class AsyncTaskRunner extends AsyncTask<String, String, String> {

        String departmentName;
        List<Faculty> deptList;
        RecyclerView deptRecyclerView;
        LinearLayout noData;
        ProgressDialog progressDialog;

        public AsyncTaskRunner(String departmentName, List<Faculty> deptList, RecyclerView deptRecyclerView, LinearLayout noData) {
            this.departmentName = departmentName;
            this.deptList = deptList;
            this.deptRecyclerView = deptRecyclerView;
            this.noData = noData;
        }



        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(UpdateFaculty.this,
                    "ProgressDialog",
                    "Please Wait for moments...");

            DatabaseReference dbRef = databaseReference.child(departmentName);

            Toast.makeText(UpdateFaculty.this, departmentName, Toast.LENGTH_SHORT).show();

                progressDialog.show();
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            noData.setVisibility(View.VISIBLE);
                            deptRecyclerView.setVisibility(View.GONE);
                        } else {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Faculty faculty = dataSnapshot.getValue(Faculty.class);
                                Toast.makeText(UpdateFaculty.this, faculty.getDepartment(), Toast.LENGTH_SHORT).show();

                                deptList.add(faculty);

                            }
                            deptRecyclerView.setHasFixedSize(true);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateFaculty.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            deptRecyclerView.setLayoutManager(linearLayoutManager);
                            FacultyAdaptor facultyAdapter = new FacultyAdaptor(deptList, UpdateFaculty.this);
                            deptRecyclerView.setAdapter(facultyAdapter);

                            noData.setVisibility(View.GONE);
                            deptRecyclerView.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UpdateFaculty.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }
}


