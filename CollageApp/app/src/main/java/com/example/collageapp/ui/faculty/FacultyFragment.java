package com.example.collageapp.ui.faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collageapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment implements View.OnClickListener {
    private CardView csDept, itDept, machDept, civilDept, entcDept, electricDept, autoDept;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_faculty, container, false);



        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        progressDialog = new ProgressDialog(getContext());

        updateFacultyToolbar =view.findViewById(R.id.topAppBar);




        csDept = view.findViewById(R.id.csDeptCard);
        electricDept = view.findViewById(R.id.electricDeptCard);
        entcDept = view.findViewById(R.id.entcDeptCard);
        civilDept = view.findViewById(R.id.civilDeptCard);
        itDept = view.findViewById(R.id.itDeptCard);
        machDept = view.findViewById(R.id.machDeptCard);
        autoDept = view.findViewById(R.id.autoDeptCard);

        csDept.setOnClickListener(this);
        electricDept.setOnClickListener(this);
        entcDept.setOnClickListener(this);
        civilDept.setOnClickListener(this);
        itDept.setOnClickListener(this);
        machDept.setOnClickListener(this);
        autoDept.setOnClickListener(this);


        return view;
//
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(getContext(), FacultyList.class);
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
                Toast.makeText(getContext(), "Invalid Navigation!", Toast.LENGTH_SHORT).show();
                return ;
        }
        startActivity(intent);
    }
}