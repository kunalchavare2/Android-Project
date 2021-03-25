package com.example.collageapp.ui.faculty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.collageapp.R;

public class FacultyDetails extends AppCompatActivity {
    private  String  facultyNameData ,facultyDepatmentData,facultyEmailData, facultyImageUrl, facultyDescriptionData, facultyKey;
    private TextView facultyEmail,facultyName,facultyDepartmentTitle,facultyDescription,facultyDepatment;
    private ImageView facultyImage;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        facultyDepatmentData = getIntent().getStringExtra("department");
        facultyNameData = getIntent().getStringExtra("name");
        facultyEmailData = getIntent().getStringExtra("email");
        facultyImageUrl = getIntent().getStringExtra("imageUrl");
        facultyDescriptionData = getIntent().getStringExtra("description");
        facultyKey = getIntent().getStringExtra("key");

        facultyImage = findViewById(R.id.facultyImage);

        facultyName = findViewById(R.id.facultyName);
        facultyEmail = findViewById(R.id.facultyEmail);
        facultyDescription = findViewById(R.id.facultyDescription);
        facultyDepatment = findViewById(R.id.facultyDepartment);
        facultyDepartmentTitle = findViewById(R.id.facultyDepartmentTitle);
        backBtn=findViewById(R.id.preScreenBtn);


        if(facultyImageUrl != null) {
            Glide.with(this).load(facultyImageUrl).into(facultyImage);
            facultyName.setText(facultyNameData);
            facultyDescription.setText(facultyDescriptionData);
            facultyEmail.setText(facultyEmailData);
            facultyDepatment.setText(facultyDepatmentData);
            facultyDepartmentTitle.setText(facultyDepatmentData);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}