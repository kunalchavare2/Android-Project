package com.example.collegeadminapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    public CardView addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void addNotice(View view) {
        addNotice = findViewById(R.id.addNotice);

        Uri uri;
        Intent intent = new Intent(this, UploadNotice.class);
        startActivity(intent);
    }
}