package com.example.collegeadminapp.notice;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {
    private final int REQ = 1;
    private CardView addImage;
    private ImageView noticeImageView;
    private EditText noticeTitle, noticeDescription;
    private Button uploadNoticeBtn;
    private ProgressDialog progressDialog;
    private LinearLayout uploadIconWidget;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private ArrayList<Bitmap> bitmapImageList = new ArrayList<Bitmap>();
    private ArrayList<String> imageDownloadUriList = new ArrayList<String>();


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
                } else if (bitmapImageList.isEmpty()) {
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


        if (imageDownloadUriList.isEmpty()) {
            imageDownloadUriList.add("");
        }

        NoticeData noticeData = new NoticeData(title, description, imageDownloadUriList, date, time, uniqueKey);


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

        for (int i = 0; i < bitmapImageList.size(); i++) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapImageList.get(i).compress(Bitmap.CompressFormat.JPEG, 50, baos);
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

                                        imageDownloadUriList.add(String.valueOf(uri));
                                        // Toast.makeText(UploadNotice.this, String.valueOf(imageDownloadUriList.size()), Toast.LENGTH_SHORT).show();

                                        if (imageDownloadUriList.size() == bitmapImageList.size()) {
                                            uploadData();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UploadNotice.this, "Unable to upload image! Please try again later.", Toast.LENGTH_SHORT).show();
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


                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            bitmapImageList.add(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //  Toast.makeText(this, String.valueOf(bitmapImageList.size()), Toast.LENGTH_SHORT).show();

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
                    // Toast.makeText(this, imagePath.toString(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }

}