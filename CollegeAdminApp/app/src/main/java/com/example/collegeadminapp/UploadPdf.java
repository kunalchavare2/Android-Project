package com.example.collegeadminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class UploadPdf extends AppCompatActivity {

    private CardView selectpdf;
    private Button uploadPdfBtn;
    private ProgressDialog progressDialog;
    private TextView setFileName;
    private EditText pdfTitle;

    private final int REQ = 1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri pdfData;
    private String pdfName, title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        selectpdf = findViewById(R.id.addPdf);
        uploadPdfBtn = findViewById(R.id.uploadPdfBtn);
        setFileName = findViewById(R.id.fileName);
        pdfTitle = findViewById(R.id.pdfTitle);


        selectpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileManager();
            }
        });

        uploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = pdfTitle.getText().toString();

                if (pdfData == null) {
                    Toast.makeText(UploadPdf.this, "Please select pdf.", Toast.LENGTH_SHORT).show();
                } else if (title.isEmpty()) {
                    pdfTitle.setError("Please enter title of pdf");
                } else {
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();
                    uploadPdf();
                }
            }
        });
    }

    private void uploadPdf() {
        StorageReference reference = storageReference.child("pdf/" + pdfName + "-" + System.currentTimeMillis() + ".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadPdf.this, "Failed to upload pdf.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("pdf").push().getKey();

        java.util.Calendar calDate = java.util.Calendar.getInstance();
        String date = new SimpleDateFormat("dd-MM-yy").format(calDate.getTime());

        java.util.Calendar calTime = java.util.Calendar.getInstance();
        String time = new SimpleDateFormat("hh:mm a").format(calTime.getTime());

        HashMap hashMap = new HashMap();
        hashMap.put("pdftitle", title);
        hashMap.put("url", downloadUrl);
        hashMap.put("date", date);
        hashMap.put("time", time);

        databaseReference.child("pdf").child(uniqueKey).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(UploadPdf.this, "Pdf uploaded successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadPdf.this, "Failed to upload pdf.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileManager() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {

            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadPdf.this.getContentResolver().query(pdfData, null, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }

            String[] arrayList;

            arrayList = pdfData.toString().split("/");


            setFileName.setText(arrayList[arrayList.length - 1]);
        }
    }


}


