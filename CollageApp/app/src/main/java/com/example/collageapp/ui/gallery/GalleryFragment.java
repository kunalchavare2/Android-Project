package com.example.collageapp.ui.gallery;

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

public class GalleryFragment extends Fragment {
    private RecyclerView eventRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<String> eventTitleList;
    private EventsAdaptor eventsAdaptor;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Event");

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerView.setHasFixedSize(true);

        getNotice();
        return view;
    }

    private void getNotice() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTitleList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    eventTitleList.add(snapshot.getKey());
                }

                eventsAdaptor = new EventsAdaptor(eventTitleList, getContext());
                eventsAdaptor.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                eventRecyclerView.setAdapter(eventsAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}