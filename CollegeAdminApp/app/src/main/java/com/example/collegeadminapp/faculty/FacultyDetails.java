package com.example.collegeadminapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collegeadminapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FacultyDetails extends AppCompatActivity {
    private String facultyNameData, facultyDepatmentData, facultyEmailData, facultyImageUrl, facultyDescriptionData, facultyKey;
    private TextView facultyEmail, facultyName, facultyDepartmentTitle, facultyDescription, facultyDepatment;
    private ImageView facultyImage;
    private Button backBtn, editFaculty, deleteFaculty;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");


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
        backBtn = findViewById(R.id.preScreenBtn);
        editFaculty = findViewById(R.id.editFaculty);

        if (facultyImageUrl != null) {
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
                Intent intent = new Intent(FacultyDetails.this, AddFaculty.class);
                intent.putExtra("name", facultyNameData);
                intent.putExtra("department", facultyDepatmentData);
                intent.putExtra("description", facultyDescriptionData);
                intent.putExtra("email", facultyEmailData);
                intent.putExtra("imageUrl", facultyImageUrl);
                intent.putExtra("key", facultyKey);
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
                                databaseReference.child(facultyDepatmentData).child(facultyKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
//                                        reference.child(facultyKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                Toast.makeText(FacultyDetails.this, "Faculty deleted Successfully.", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(FacultyDetails.this, "Something Went wrong..", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
                                               Toast.makeText(FacultyDetails.this, "Faculty deleted Successfully.", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                });

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