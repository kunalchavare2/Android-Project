package com.example.collageapp.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collageapp.R;
import com.example.collageapp.ui.faculty.Faculty;
import com.example.collageapp.ui.faculty.FacultyDetails;

import java.util.List;


public class EventsAdaptor extends RecyclerView.Adapter<EventsAdaptor.EventViewAdaptor> {
    private List<String> eventsList;
    private Context context;

    public EventsAdaptor(List<String> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;

    }

    @NonNull
    @Override
    public EventsAdaptor.EventViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new EventViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdaptor.EventViewAdaptor holder, int position) {
        String  eventTitle = eventsList.get(holder.getAdapterPosition());

        holder.eventTitle.setText(eventTitle);

        holder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventGallery.class);
                intent.putExtra("eventTitle", eventTitle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class EventViewAdaptor extends RecyclerView.ViewHolder {
TextView eventTitle;
CardView eventCard;

        public EventViewAdaptor(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventCard = itemView.findViewById(R.id.eventCard);
        }
    }
}
