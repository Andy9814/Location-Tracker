package com.example.nrip.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mapBtn, pastPlacesBtn, wannaGoBtn, aboutBtn;
    RecyclerView pastRV, wishRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapBtn = findViewById(R.id.mapBtn);
        pastPlacesBtn = findViewById(R.id.pastPlacesBtn);
        wannaGoBtn = findViewById(R.id.wannaGoPlacesBtn);
        aboutBtn = findViewById(R.id.aboutBtn);
        pastRV = findViewById(R.id.pastRecyclerV);
        wishRV= findViewById(R.id.wishRecyclerV);

    }

    public void onClickBtn(View view) {
        if (view.getId() == mapBtn.getId()) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (view.getId() == pastPlacesBtn.getId()) {
            Intent intent = new Intent(this, pastPlaces.class);
            startActivity(intent);
        }
        else if (view.getId() == wannaGoBtn.getId()) {
            Intent intent = new Intent(this, wishPlaces.class);
            startActivity(intent);
        }
        else if (view.getId() == aboutBtn.getId()) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
    }
}