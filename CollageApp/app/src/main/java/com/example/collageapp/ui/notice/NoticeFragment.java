package com.example.collageapp.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collageapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {
    private RecyclerView deleteNoticeRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> noticeDataList;
    private NoticeAdaptor noticeAdaptor;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);


        deleteNoticeRecyclerView = view.findViewById(R.id.deleteNoticeRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notice");
        deleteNoticeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deleteNoticeRecyclerView.setHasFixedSize(true);

        getNotice();

        return view;
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

                noticeAdaptor = new NoticeAdaptor(getContext(), noticeDataList);
                noticeAdaptor.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                deleteNoticeRecyclerView.setAdapter(noticeAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}