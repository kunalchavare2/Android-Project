package com.example.collegeadminapp.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeadminapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteNotice extends AppCompatActivity {
    private RecyclerView deleteNoticeRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> noticeDataList;
    private NoticeAdaptor noticeAdaptor;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        deleteNoticeRecyclerView = findViewById(R.id.deleteNoticeRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notice");
        deleteNoticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deleteNoticeRecyclerView.setHasFixedSize(true);

        getNotice();

    }

    private void getNotice() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeDataList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData noticeData = snapshot.getValue(NoticeData.class);
                    noticeDataList.add(noticeData);
                }

                noticeAdaptor =     new NoticeAdaptor(DeleteNotice.this,noticeDataList);
            noticeAdaptor.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            deleteNoticeRecyclerView.setAdapter(noticeAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeleteNotice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}