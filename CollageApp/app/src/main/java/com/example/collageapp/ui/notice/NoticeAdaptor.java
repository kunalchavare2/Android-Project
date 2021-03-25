package com.example.collageapp.ui.notice;
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

import com.example.collageapp.R;
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
        holder.noticeDate.setText(String.format("%s %s", noticeItem.getDate(), noticeItem.getTime()));
        try {
            if (!imageUrls.isEmpty()) {
                Glide.with(context).load(imageUrls.get(0)).into(holder.noticeImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdaptor extends RecyclerView.ViewHolder {
        private ImageView noticeImage;
        private TextView noticeTitle, noticeDescription,noticeDate;

        public NoticeViewAdaptor(@NonNull View itemView) {
            super(itemView);
            noticeImage = itemView.findViewById(R.id.noticeImageView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeDate = itemView.findViewById(R.id.noticeDate);
            noticeDescription = itemView.findViewById(R.id.noticeDescription);

        }
    }

}