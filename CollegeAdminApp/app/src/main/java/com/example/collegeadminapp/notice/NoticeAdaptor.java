package com.example.collegeadminapp.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeadminapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoticeAdaptor extends RecyclerView.Adapter<NoticeAdaptor.NoticeViewAdaptor> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdaptor(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdaptor holder, int position) {
        NoticeData noticeItem = list.get(position);
        ArrayList<String> imageUrls = new ArrayList<>();

        imageUrls = noticeItem.getImageUrls();
        holder.noticeTitle.setText(noticeItem.getTitle());
        holder.noticeDescription.setText(noticeItem.getDescription());
        try {
            if (!imageUrls.isEmpty()) {
                Glide.with(context).load(imageUrls.get(0)).into(holder.noticeImage);
            }else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notice");
                reference.child(noticeItem.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Something Went wrong..", Toast.LENGTH_SHORT).show();
                    }
                });
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdaptor extends RecyclerView.ViewHolder {
        private ImageView noticeImage;
        private Button deleteNotice;
        private TextView noticeTitle, noticeDescription;

        public NoticeViewAdaptor(@NonNull View itemView) {
            super(itemView);
            noticeImage = itemView.findViewById(R.id.noticeImageView);
            deleteNotice = itemView.findViewById(R.id.deleteNoticeBtn);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeDescription = itemView.findViewById(R.id.noticeDescription);

        }
    }

}
