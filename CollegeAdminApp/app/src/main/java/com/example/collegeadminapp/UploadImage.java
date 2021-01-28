package com.example.collegeadminapp;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UploadImage extends AppCompatActivity {
    private Spinner imageCategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView eventImageView;
    private LinearLayout uploadIconWidget;
    private ProgressDialog progressDialog;

    private ArrayList<Bitmap> bitmapImageList = new ArrayList<Bitmap>();
    private String category;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private final int REQ = 1;
    private ArrayList<String> imageDownloadUriList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        imageCategory = findViewById(R.id.imageCategory);
        selectImage = findViewById(R.id.addImage);
        uploadImage = findViewById(R.id.uploadImageBtn);
        eventImageView = findViewById(R.id.eventImageView);
        uploadIconWidget = findViewById(R.id.uploadIconWidget);


        String[] items = new String[]{
                "Select Category", "Convocation", "Independence Day", "Other Events"
        };
        imageCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = imageCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapImageList.isEmpty()) {
                    Toast.makeText(UploadImage.this, "Please select Image.", Toast.LENGTH_SHORT).show();
                } else if (category.contains("Select Category")) {
                    Toast.makeText(UploadImage.this, "Please select category.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();
                    uploadImage();
                }
            }
        });
    }


    private void uploadData() {
        databaseReference = databaseReference.child("Event");

        final String uniqueKey = databaseReference.push().getKey();

        Calendar calDate = Calendar.getInstance();
        String date = new SimpleDateFormat("dd-MM-yy").format(calDate.getTime());

        Calendar calTime = Calendar.getInstance();
        String time = new SimpleDateFormat("hh:mm a").format(calTime.getTime());

        EventData eventData = new EventData(category, date, time, uniqueKey, imageDownloadUriList);


        databaseReference.child(category).child(uniqueKey).setValue(eventData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(UploadImage.this, "Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadImage.this, "Failed to upload the Image!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void uploadImage() {

        for (int i = 0; i < bitmapImageList.size(); i++) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapImageList.get(i).compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] finalImage = baos.toByteArray();

            final StorageReference filePath;
            filePath = storageReference.child("Event").child(finalImage + "jpg");
            final UploadTask uploadTask = filePath.putBytes(finalImage);

            uploadTask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageDownloadUriList.add(String.valueOf(uri));

                                        //Toast.makeText(UploadImage.this, String.valueOf(uri), Toast.LENGTH_SHORT).show();

                                        if (imageDownloadUriList.size() == bitmapImageList.size()) {
                                            uploadData();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UploadImage.this, "Unable to upload image! Please try again later.", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.

                    //    Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            bitmapImageList.add(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(this, imageUri.getPath().toString(), Toast.LENGTH_SHORT).show();

                    }

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                } else if (data.getData() != null) {
                    Uri imagePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                        bitmapImageList.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //   Toast.makeText(this, imagePath.toString(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }
}