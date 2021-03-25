package com.example.collageapp.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collageapp.R;

import java.util.ArrayList;

public class EventGalleryAdaptor extends RecyclerView.Adapter<EventGalleryAdaptor.EventGalleryViewAdaptor> {

    private ArrayList<String> eventsImagesList;
    private Context context;

    public EventGalleryAdaptor(ArrayList<String> eventsImagesList, Context context) {
        this.eventsImagesList = eventsImagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventGalleryViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_gallery_item, parent, false);

        return new EventGalleryViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventGalleryViewAdaptor holder, int position) {
        String eventImageUrl = eventsImagesList.get(holder.getAdapterPosition());
        Toast.makeText(context, "" + eventImageUrl, Toast.LENGTH_SHORT).show();

        try {
            Glide.with(context).load(eventImageUrl).error(R.drawable.cs_dept).into(holder.eventImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventsImagesList.size();
    }

    public class EventGalleryViewAdaptor extends RecyclerView.ViewHolder {
        private ImageView eventImage;

        public EventGalleryViewAdaptor(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventGalleryImageView);
        }
    }
}
