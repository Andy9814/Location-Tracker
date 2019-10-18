package com.example.nrip.locationtracker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener
{
    private GoogleMap mMap;
    Marker beenToMarker;
    Marker wishToMarker;
    private List<Marker> beenToMarkerList;
    private List<Marker> wishToMarkerList;
    private LatLng CurrentLocation;
    private final String TAG ="MyMaps";
    FusedLocationProviderClient fusedLocationClient ;
//    SharedPreferences prefBeenTo= getSharedPreferences("mypreffile",MODE_PRIVATE);
//    SharedPreferences.Editor editor = prefBeenTo.edit();
   // SharedPreferences prefWishTo = getSharedPreferences("mypreffile",MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Checks if ACCESS_FINE_LOCATION permission is granted
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Initialize the fusedLocationClient to get current loc
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(beenToMarkerList == null && wishToMarkerList == null) {
            beenToMarkerList = new ArrayList<Marker>();
            wishToMarkerList = new ArrayList<Marker>();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("sdf" ,"inside the onResume");
        getLocation();
        if(mMap!= null)
            LoadPreferences();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        getLocation();
        if(mMap!= null)
            LoadPreferences();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng)
            {
                Log.d(TAG, "onMapClick: inside onclick map");
                wishToMarker =    mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(getCurrAddress(latLng)).snippet("Your location Lat:"+latLng.latitude+",Lng:"+latLng.longitude));
                wishToMarkerList.add(wishToMarker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

    }
    public void getLocation(){
        if(mMap != null) {
          //  mMap.clear(); we can use thiss option or the other one
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location lastLocation) {
                            // Got last known location. In some rare situations this can be null.
                            if (lastLocation != null) {
                                double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                                CurrentLocation = new LatLng(lat, lon);

                                beenToMarker =                 mMap.addMarker(new MarkerOptions().position(CurrentLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                        .title(getCurrAddress(CurrentLocation)).snippet("Your location Lat:"+CurrentLocation.latitude+",Lng:"+CurrentLocation.longitude));
                                beenToMarkerList.add(beenToMarker);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation));
                            }
                        }
                    })
                .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MapDemoActivity", "Error trying to get last GPS location");
                    e.printStackTrace();
                }
            });
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SavePreferences();
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("mypreffile",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("beenTolistSize", beenToMarkerList.size());
        editor.putInt("wishTolistSize", wishToMarkerList.size());

        if(beenToMarkerList != null || beenToMarkerList.size() > 0){
            for(int i = 0; i <beenToMarkerList.size(); i++){
                editor.putFloat("been_lat"+i, (float)  beenToMarkerList.get(i).getPosition().latitude);
                editor.putFloat("been_long"+i, (float) beenToMarkerList.get(i).getPosition().longitude);
                editor.putString("been_title"+i,       beenToMarkerList.get(i).getTitle());
            }
        }
        if(wishToMarkerList != null || wishToMarkerList.size() > 0){
            for(int i = 0; i <wishToMarkerList.size(); i++){
                editor.putFloat("wish_lat"+i, (float)  wishToMarkerList.get(i).getPosition().latitude);
                editor.putFloat("wish_long"+i, (float) wishToMarkerList.get(i).getPosition().longitude);
                editor.putString("wish_title"+i,       wishToMarkerList.get(i).getTitle());
            }
        }
//        Set<Marker> set = new HashSet<Marker>();
//        set.addAll(beenToMarkerList.);
//        editor.putStringSet("DATE_LIST", set);
        editor.commit();
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("mypreffile",MODE_PRIVATE);

        int beenToSize = sharedPreferences.getInt("beenTolistSize", 0);
        int wishToSize = sharedPreferences.getInt("wishTolistSize", 0);
        if(beenToSize > 0) {

            for (int i = 0; i < beenToSize; i++) {
                LatLng mLocation;
                double been_lat = (double) sharedPreferences.getFloat("been_lat" + i, 0);
                double been_long = (double) sharedPreferences.getFloat("been_long" + i, 0);
                // String title = sharedPreferences.getString("been_title"+i,"NULL");
                mLocation = new LatLng(been_lat, been_long);
                mMap.addMarker(new MarkerOptions().position(mLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(getCurrAddress(mLocation)).snippet("Your location Lat:" + mLocation.latitude + ",Lng:" + mLocation.longitude));

                // markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(title)));
            }
        }
        if(wishToSize > 0) {

            for (int i = 0; i < wishToSize; i++) {
                LatLng mLocation;
                double wish_lat = (double) sharedPreferences.getFloat("wish_lat" + i, 0);
                double wish_long = (double) sharedPreferences.getFloat("wish_long" + i, 0);
                String title = sharedPreferences.getString("wish_title" + i, "NULL");
                mLocation = new LatLng(wish_lat, wish_long);

                mMap.addMarker(new MarkerOptions().position(mLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(getCurrAddress(mLocation)).snippet("Your location Lat:" + mLocation.latitude + ",Lng:" + mLocation.longitude));

                // markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(title)));
            }
        }
    }

    private String getCurrAddress(LatLng location)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";
        String currAddress = "";  // string to return with address

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.latitude +
                    ", Longitude = " +
                    location.longitude, illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            return currAddress;
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and return as String.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            return TextUtils.join(System.getProperty("line.separator"),
                    addressFragments);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}
