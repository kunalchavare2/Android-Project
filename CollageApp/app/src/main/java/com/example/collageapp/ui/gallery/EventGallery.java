package com.example.collageapp.ui.gallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collageapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventGallery extends AppCompatActivity {
    private RecyclerView eventGalleryRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<String> eventImageUrlsList;
    private EventGalleryAdaptor eventGalleryAdaptor;
    private DatabaseReference databaseReference;
    private String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_gallery);
        eventTitle = getIntent().getStringExtra("eventTitle");
        progressBar = findViewById(R.id.progressBar);
        eventGalleryRecyclerView = findViewById(R.id.eventGalleryRecyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Event").child(eventTitle);

        eventGalleryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventGalleryRecyclerView.setHasFixedSize(true);

        getNotice();

    }

    private void getNotice() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventImageUrlsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventModal eventModal = snapshot.getValue(EventModal.class);
                   eventImageUrlsList.addAll(eventModal.getImageUrls());
                }

                eventGalleryAdaptor = new EventGalleryAdaptor(eventImageUrlsList, EventGallery.this);
                eventGalleryAdaptor.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                eventGalleryRecyclerView.setAdapter(eventGalleryAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EventGallery.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}