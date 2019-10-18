package com.example.nrip.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class pastPlaces extends AppCompatActivity {
    private RecyclerView pastRecylerView;
    private RecyclerView.Adapter pastAdapter;
    private RecyclerView.LayoutManager pastLayoutManager;

    ArrayList<String> beentL = new ArrayList<String>();
 //   ArrayList<String> wishtL= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_places);




        SharedPreferences sharedPreferences = getSharedPreferences("mypreffile", MODE_PRIVATE);
        int beenToSize = sharedPreferences.getInt("beenTolistSize", 0);

        if (beenToSize > 0) {

            for (int i = 0; i < beenToSize; i++) {
                LatLng mLocation;
                double been_lat = (double) sharedPreferences.getFloat("been_lat" + i, 0);
                double been_long = (double) sharedPreferences.getFloat("been_long" + i, 0);
                String title = sharedPreferences.getString("been_title" + i, "NULL");
                mLocation = new LatLng(been_lat, been_long);

                beentL.add(title);
            }
        }



        pastRecylerView = findViewById(R.id.pastRecyclerV);
        pastRecylerView.setHasFixedSize(true);
        pastLayoutManager = new LinearLayoutManager(this);
        pastAdapter = new RecyclerAdapter(beentL);

        pastRecylerView.setLayoutManager(pastLayoutManager);
        pastRecylerView.setAdapter(pastAdapter);
    }
}
