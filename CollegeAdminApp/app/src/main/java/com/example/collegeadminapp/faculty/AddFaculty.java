package com.example.collegeadminapp.faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddFaculty extends AppCompatActivity {
    private ImageView addFacultyImage;
    private EditText addFacultyName, addFacultyDescription, addFacultyEmail;
    private Spinner addFacultyDepartment;
    private Button addFacultybtn;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private final int REQ = 1;
    private String name, email, description, department, downloadUrl = "";
    private Bitmap facultyImage = null;
    private String facultyName, facultyDepartment, facultyEmail, facultyImageUrl, facultyDescription, facultyKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        //Get Data to update faculty
        facultyDepartment = getIntent().getStringExtra("department");
        facultyName = getIntent().getStringExtra("name");
        facultyEmail = getIntent().getStringExtra("email");
        facultyImageUrl = getIntent().getStringExtra("imageUrl");
        facultyDescription = getIntent().getStringExtra("description");
        facultyKey = getIntent().getStringExtra("key");


        addFacultyImage = findViewById(R.id.addFacultyImage);
        addFacultyName = findViewById(R.id.addFacultyName);
        addFacultyEmail = findViewById(R.id.addFacultyEmail);
        addFacultyDescription = findViewById(R.id.addFacultyDescription);
        addFacultyDepartment = findViewById(R.id.facultyCategory);
        addFacultybtn = findViewById(R.id.addFacultyBtn);


        List<String> items = new ArrayList<>();
        items.add("Select Category");
        items.add("Computer");
        items.add("Electronics & Telecommunication");
        items.add("Mechanical");
        items.add("Auto Mobile");
        items.add("Electrical");
        items.add("Information Technology");
        items.add("Civil");

        addFacultyDepartment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        addFacultyDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //to avoid inserting "Select category" in department variable
                department = position > 0 ? addFacultyDepartment.getSelectedItem().toString() : "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (facultyKey != null) {
            addFacultybtn.setText("Update Faculty");
            Glide.with(this).load(facultyImageUrl).into(addFacultyImage);
            addFacultyName.setText(facultyName);
            addFacultyDescription.setText(facultyDescription);
            addFacultyDepartment.setSelection(items.indexOf(facultyDepartment));
            addFacultyEmail.setText(facultyEmail);
        }


        addFacultyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addFacultybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //this is used to check whether faculty image is selected from local
                    //Storage if yes then upload image to firebase
                    if(facultyImage != null){
                        uploadImage();
                    }else{

                        uploadData();
                    }

                }
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ) {
            if (resultCode == RESULT_OK) {
                if (data.getData() != null) {
                    Uri imagePath = data.getData();
                    try {
                        facultyImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                        addFacultyImage.setImageBitmap(facultyImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, imagePath.toString(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }


    private boolean validate() {
        String errorString = "";
        name = addFacultyName.getText().toString();
        email = addFacultyEmail.getText().toString();
        description = addFacultyDescription.getText().toString();
        if (!name.isEmpty() && !email.isEmpty() && !description.isEmpty() && !department.isEmpty() && ( facultyImage != null || facultyImageUrl != null)) {
            return true;
        }

        if (facultyImage == null && facultyImageUrl ==null) {
            errorString = "Image ";
        }

        if (name.isEmpty()) {
            addFacultyName.setError("Please Enter faculty Name.");

        }
        if (email.isEmpty()) {
            addFacultyEmail.setError("Please Enter faculty Email.");

        }
        if (description.isEmpty()) {
            addFacultyDescription.setError("Please Enter faculty Description.");
        }
        if (department.isEmpty()) {
            if (facultyImage == null) {
                errorString += "& Department";
            } else {
                errorString = "Department";
            }
        }

        Toast.makeText(this, "Please Select " + errorString + ".", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void uploadData() {
        progressDialog.setMessage("Uploading Data...");
        databaseReference = databaseReference.child("Faculty");
        final String uniqueKey;

        if(facultyKey != null){
            if(downloadUrl.isEmpty()){
                // set download url to previous image url get from update data
                downloadUrl = facultyImageUrl;
            }
            uniqueKey = facultyKey;
        }else{
            uniqueKey = databaseReference.push().getKey();
        }
if(!facultyDepartment.equals(department)){
    databaseReference.child(facultyDepartment).child(facultyKey).removeValue();
}

        Faculty faculty = new Faculty(name, email, description, department, downloadUrl, uniqueKey);


        databaseReference.child(department).child(uniqueKey).setValue(faculty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(AddFaculty.this, "Faculty added Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddFaculty.this, "Failed to add the Faculty!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void uploadImage() {

        progressDialog.setTitle("Adding faculty to database.");
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        facultyImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child("Faculty").child(finalImage + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);

        uploadTask.addOnCompleteListener(AddFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);

                                    // Toast.makeText(AddFaculty.this, String.valueOf(uri), Toast.LENGTH_SHORT).show();
                                    uploadData();

                                }
                            });
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddFaculty.this, "Unable to upload image! Please try again later.", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

}
