package com.example.collageapp.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.collageapp.R;

import java.util.ArrayList;

public class BranchAdaptor extends PagerAdapter {
private Context context;
private ArrayList<BranchModal> branchModals ;

    public BranchAdaptor(Context context, ArrayList<BranchModal> branchModals) {
        this.context = context;
        this.branchModals = branchModals;
    }


    @Override
    public int getCount() {
        return branchModals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
View view = LayoutInflater.from(context).inflate(R.layout.branch_item,container,false);
        ImageView brIcon;
        TextView brTitle,brDescription;

        brIcon = view.findViewById(R.id.branchIcon);
        brTitle = view.findViewById(R.id.branchTitle);
        brDescription = view.findViewById(R.id.branchDesc);

        brIcon.setImageResource(branchModals.get(position).getImg());
        brTitle.setText(branchModals.get(position).getTitle());
        brDescription.setText(branchModals.get(position).getDescription());

        container.addView(view,0);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
     container.removeView((View)object);
    }
}
