package com.example.nrip.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class wishPlaces extends AppCompatActivity {
    private RecyclerView wishRecylerView;
    private RecyclerView.Adapter wishAdapter;
    private RecyclerView.LayoutManager wishLayoutManager;
    //ArrayList<String> beentL = new ArrayList<String>();
    ArrayList<String> wishtL= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_places);
        ArrayList<ItemList> itList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("mypreffile", MODE_PRIVATE);
        int wishToSize = sharedPreferences.getInt("wishTolistSize", 0);
        if (wishToSize > 0) {

            for (int i = 0; i < wishToSize; i++) {
                LatLng mLocation;
                double wish_lat = (double) sharedPreferences.getFloat("wish_lat" + i, 0);
                double wish_long = (double) sharedPreferences.getFloat("wish_long" + i, 0);
                String title = sharedPreferences.getString("wish_title" + i, "NULL");
                mLocation = new LatLng(wish_lat, wish_long);
                itList.add(new ItemList(
                        R.drawable.ic_launcher_background,title,
                        Double.toString(wish_lat)+"--"+Double.toString(wish_long)));
                wishtL.add(title);

            }
        }
        wishRecylerView = findViewById(R.id.wishRecyclerV);
        wishRecylerView.setHasFixedSize(true);
        wishLayoutManager = new LinearLayoutManager(this);
        wishAdapter = new RecyclerAdapter(itList);

        wishRecylerView.setLayoutManager(wishLayoutManager);
        wishRecylerView.setAdapter(wishAdapter);
    }
}
