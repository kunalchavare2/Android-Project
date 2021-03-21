package com.example.collegeadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.collegeadminapp.faculty.UpdateFaculty;
import com.example.collegeadminapp.notice.DeleteNotice;
import com.example.collegeadminapp.notice.UploadNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView addNotice, addImage, addEbook,updateFaculty,deleteNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addNotice = findViewById(R.id.addNotice);
        addImage = findViewById(R.id.addImage);
        addEbook = findViewById(R.id.addEbook);
        updateFaculty = findViewById(R.id.updateFaculty);
        deleteNotice = findViewById(R.id.deleteNotice);


        addNotice.setOnClickListener(this);
        addImage.setOnClickListener(this);
        addEbook.setOnClickListener(this);
        updateFaculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.addNotice:
                intent = new Intent(this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.addImage:
                intent = new Intent(this, UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addEbook:
                intent = new Intent(this, UploadPdf.class);
                startActivity(intent);
                break;
            case R.id.updateFaculty:
                intent = new Intent(this, UpdateFaculty.class);
                startActivity(intent);
                break;
            case R.id.deleteNotice:
                intent = new Intent(this, DeleteNotice.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Invalid Navigation!", Toast.LENGTH_SHORT).show();
        }

    }
}