package com.example.collageapp.ui.faculty;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collageapp.R;


import java.util.List;

public class FacultyAdaptor extends RecyclerView.Adapter<FacultyAdaptor.FacultyViewAdaptor> {
    private  List<Faculty> facultyList;
    private  Context context;

    public FacultyAdaptor(List<Faculty> facultyList, Context context) {
        this.facultyList = facultyList;
        this.context = context;

    }

    @NonNull
    @Override
    public FacultyAdaptor.FacultyViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item, parent, false);
        return new FacultyViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAdaptor.FacultyViewAdaptor holder, int position) {
        Faculty faculty = facultyList.get(holder.getAdapterPosition());
        holder.name.setText(faculty.getName());
        holder.department.setText(faculty.getDepartment());
        holder.email.setText(faculty.getEmail());
        Glide.with(context).load(faculty.getImageUrl()).into(holder.facultyImage);

        holder.facultyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new  Intent(context, FacultyDetails.class);
                intent.putExtra("name",faculty.getName());
                intent.putExtra("department",faculty.getDepartment());
                intent.putExtra("description",faculty.getDescription());
                intent.putExtra("email",faculty.getEmail());
                intent.putExtra("imageUrl",faculty.getImageUrl());
                intent.putExtra("key",faculty.getKey());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public class FacultyViewAdaptor extends RecyclerView.ViewHolder {
        private TextView name, department, email;
        private CardView facultyCard;
        private ImageView facultyImage;

        public FacultyViewAdaptor(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.facultyName);
            department = itemView.findViewById(R.id.facultyDepartment);
            email = itemView.findViewById(R.id.facultyEmail);
            facultyImage = itemView.findViewById(R.id.facultyImage);
            facultyCard = itemView.findViewById(R.id.facultyCard);


        }
    }
}

