package com.example.collageapp.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collageapp.R;
import com.example.collageapp.ui.notice.NoticeAdaptor;
import com.example.collageapp.ui.notice.NoticeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {
    private RecyclerView ebookRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<EbookModal> ebookModalArrayList;
    private EbookAdaptor ebookAdaptor;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        ebookRecyclerView = findViewById(R.id.ebookRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("pdf");
        progressBar = findViewById(R.id.progressBar);
        ebookRecyclerView.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
        ebookRecyclerView.setHasFixedSize(true);

        getEbook();
    }

    private void getEbook() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ebookModalArrayList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EbookModal ebookModal = snapshot.getValue(EbookModal.class);
                    ebookModalArrayList.add(ebookModal);
                }

                ebookAdaptor = new EbookAdaptor(EbookActivity.this, ebookModalArrayList);
                ebookAdaptor.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                ebookRecyclerView.setAdapter(ebookAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EbookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}