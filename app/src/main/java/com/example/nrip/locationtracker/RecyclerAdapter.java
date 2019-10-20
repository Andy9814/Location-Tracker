package com.example.nrip.locationtracker;

import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.localtionViewHolder> {


    private ArrayList<ItemList> locationList;
    public static class localtionViewHolder extends  RecyclerView.ViewHolder{
      //  private TextView textView;
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public localtionViewHolder(View itemView) {
            super(itemView);
           // LinearLayout ll = (LinearLayout) itemView;
            //textView = (TextView)itemView.findViewById(R.id.test3);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

        }
    }

    public RecyclerAdapter(ArrayList<ItemList> locList) {
        locationList = locList;
    }

    @NonNull
    @Override
    public localtionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_view, parent, false);
        localtionViewHolder evh = new localtionViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull localtionViewHolder holder, int position) {
        ItemList currentItem = locationList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //holder .setBackgroundColor(currentColor);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
      //  holder.textView.setText((locationList.get(position)+"\n"));

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


}
