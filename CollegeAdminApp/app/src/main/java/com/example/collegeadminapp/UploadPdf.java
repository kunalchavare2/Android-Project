package com.example.collegeadminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadPdf extends AppCompatActivity {

    private final int REQ = 1;
    private CardView selectpdf;
    private Button uploadPdf;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri pdfData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        selectpdf = findViewById(R.id.addPdf);
        uploadPdf = findViewById(R.id.uploadPdfBtn);


        selectpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileManager();
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
            if (data.getClipData() != null) {
                pdfData = data.getData();

                Toast.makeText(this, "" + pdfData, Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {
                Uri imagePath = data.getData();

            }

        }
    }


}