package com.example.nrip.locationtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.localtionViewHolder> {


    private ArrayList<String> locationList;
    public static class localtionViewHolder extends  RecyclerView.ViewHolder{
        private TextView textView;
        public localtionViewHolder(View itemView) {
            super(itemView);
           // LinearLayout ll = (LinearLayout) itemView;
            textView = (TextView)itemView.findViewById(R.id.test3);

        }
    }

    public RecyclerAdapter(ArrayList<String> locList) {
        locationList = locList;
    }

    @NonNull
    @Override
    public localtionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test3, parent, false);
        localtionViewHolder evh = new localtionViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull localtionViewHolder holder, int position) {
        holder.textView.setText((locationList.get(position)+"\n").toString());

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


}
