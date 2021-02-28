package com.example.collegeadminapp.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.collegeadminapp.R;

public class FacultyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        String extra = getIntent().getStringExtra("extra");


        Toast.makeText(this, extra, Toast.LENGTH_SHORT).show();
    }
}