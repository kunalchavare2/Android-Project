package com.example.collegeadminapp.faculty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.collegeadminapp.R;

public class FacultyDetails extends AppCompatActivity {
    private  String  facultyNameData ,facultyDepatmentData,facultyEmailData, facultyImageUrl, facultyDescriptionData, facultyKey;
private TextView  facultyEmail,facultyName,facultyDepartmentTitle,facultyDescription,facultyDepatment;
private ImageView facultyImage;
private Button backBtn,editFaculty,deleteFaculty;
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
        deleteFaculty = findViewById(R.id.deleteFaculty);
        backBtn=findViewById(R.id.preScreenBtn);
        editFaculty = findViewById(R.id.editFaculty);

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

        editFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FacultyDetails.this,AddFaculty.class);
                intent.putExtra("name",facultyNameData);
                intent.putExtra("department",facultyDepatmentData);
                intent.putExtra("description",facultyDescriptionData);
                intent.putExtra("email",facultyEmailData);
                intent.putExtra("imageUrl",facultyImageUrl);
                intent.putExtra("key",facultyKey);
                startActivity(intent);
            }
        });



        deleteFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FacultyDetails.this)
                        .setTitle("Delete Faculty")
                        .setMessage("Are you sure you want to delete this faculty?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_delete)
                        .show();

            }
        });
    }
}