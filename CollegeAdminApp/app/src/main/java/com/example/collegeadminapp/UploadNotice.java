package com.example.collegeadminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {
    private CardView addImage;
    private ImageView noticeImageView;
    private EditText noticeTitle, noticeDescription;
    private Button uploadNoticeBtn;
    private ProgressDialog progressDialog;
    private LinearLayout uploadIconWidget;


    private final int REQ = 1;
    private Bitmap bitmapImage;
    private String imageDownloadUrl = "";
    private DatabaseReference reference;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        progressDialog = new ProgressDialog(this);

        addImage = findViewById(R.id.addImage);
        noticeImageView = findViewById(R.id.noticeImageView);
        noticeTitle = findViewById(R.id.noticeTitle);
        noticeDescription = findViewById(R.id.noticeDescription);
        uploadNoticeBtn = findViewById(R.id.uploadNoticeBtn);
        uploadIconWidget = findViewById(R.id.uploadIconWidget);


        addImage.setOnClickListener(v -> openGallery());

        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Uploading Notice");


                if (noticeTitle.getText().toString().isEmpty()) {
                    noticeTitle.setError("Please enter title of your notice.");
                    noticeTitle.requestFocus();
                } else if (bitmapImage == null) {
                    progressDialog.show();
                    uploadData();
                } else {
                    progressDialog.show();
                    uploadImage();
                }
            }
        });
    }

    private void uploadData() {
        reference = reference.child("Notice");

        final String uniqueKey = reference.push().getKey();

        String title = noticeTitle.getText().toString();
        String description = noticeDescription.getText().toString();

        Calendar calDate = Calendar.getInstance();
        String date = new SimpleDateFormat("dd-MM-yy").format(calDate.getTime());

        Calendar calTime = Calendar.getInstance();
        String time = new SimpleDateFormat("hh:mm a").format(calTime.getTime());

        NoticeData noticeData = new NoticeData(title, description, imageDownloadUrl, date, time, uniqueKey);


        // Toast.makeText(this, title+"\n"+imageDownloadUrl+"\n"+date+"\n"+time+"\n"+uniqueKey, Toast.LENGTH_LONG).show();

        reference.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(UploadNotice.this, "Notice uploaded successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadNotice.this, "Failed to upload the notice!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child("Notice").child(finalImage + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);

        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageDownloadUrl = String.valueOf(uri);
                                    Toast.makeText(UploadNotice.this, imageDownloadUrl, Toast.LENGTH_SHORT).show();
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UploadNotice.this, "Unable to uplaod image! Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK && null != data) {
            //to make upload icon and text invisible
            uploadIconWidget.setVisibility(View.INVISIBLE);

            Uri imageUri = data.getData();

            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            noticeImageView.setImageBitmap(bitmapImage);

        }
    }

}