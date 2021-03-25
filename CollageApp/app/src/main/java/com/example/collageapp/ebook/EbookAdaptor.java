package com.example.collageapp.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collageapp.R;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;

import static android.provider.Settings.System.getString;

public class EbookAdaptor extends RecyclerView.Adapter<EbookAdaptor.EbookViewAdaptor> {
    private Context context;
    private ArrayList<EbookModal> ebookModalArrayList;

    public EbookAdaptor(Context context, ArrayList<EbookModal> ebookModalArrayList) {
        this.context = context;
        this.ebookModalArrayList = ebookModalArrayList;
    }

    @NonNull
    @Override
    public EbookViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item, parent, false);
        return new EbookViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewAdaptor holder, int position) {
        EbookModal ebookModal = ebookModalArrayList.get(position);

        holder.ebookTitle.setText(ebookModal.getPdfTitle());

        holder.ebookDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ebookModal.getUrl()));
                context.startActivity(intent);
            }
        });

        holder.ebookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(ebookModal.getUrl()), "application/pdf");

                Intent intent = Intent.createChooser(browserIntent, ebookModal.getPdfTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional

               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ebookModalArrayList.size();
    }

    public class EbookViewAdaptor extends RecyclerView.ViewHolder {
        private CardView ebookCard;
        private TextView ebookTitle;
        private Button ebookDownloadBtn;

        public EbookViewAdaptor(@NonNull View itemView) {
            super(itemView);
            ebookCard = itemView.findViewById(R.id.ebookCard);
            ebookTitle = itemView.findViewById(R.id.ebookTitle);
            ebookDownloadBtn = itemView.findViewById(R.id.ebookDownloadBtn);
        }
    }
}
