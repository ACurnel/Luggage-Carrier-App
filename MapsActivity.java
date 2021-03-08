package com.example.luggagecarrier;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latCoordinate;
    private double lonCoordinate;
    private LatLng coordinate;

    private FirebaseDatabase firebaseCord;
    private String lat;
    private String lon;
    private DatabaseReference Ref;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //database
        firebaseCord = FirebaseDatabase.getInstance();
        Ref = firebaseCord.getReference();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Bind the XML views to Java Code Elements */
        Button newPoint = (Button) findViewById(R.id.btnNewPoint);
        newPoint.setOnClickListener(btnNewPointGenerator);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        /*
        //generate coordinate
        double latCoordinate1 = Double.parseDouble(lat);
        double lonCoordinate1 = Double.parseDouble(lon);
        LatLng coordinate1 = new LatLng(latCoordinate1, lonCoordinate1);

        // Add a marker at new coordinate and move the camera
        mMap.addMarker(new MarkerOptions().position(coordinate1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate1));
        */
    }

    //on button click: clear map, generate new point, map it and move camera
    View.OnClickListener btnNewPointGenerator = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            //read from database and generate point
            Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && (count % 2 == 0)){
                        System.out.println("top Count: " + count);
                        System.out.println("top Count check: " + (count % 2));
                        count = count + 1;

                    }

                    else if (snapshot.exists() && (count % 2 != 0)){

                        lat= snapshot.child("Lat").getValue().toString();
                        lon= snapshot.child("Lon").getValue().toString();

                        System.out.println("Lat: " + lat + ", Lon: " + lon);

                        //clear
                        mMap.clear();

                        //generate
                        latCoordinate = Double.parseDouble(lat);
                        lonCoordinate = Double.parseDouble(lon);
                        coordinate = new LatLng(latCoordinate, lonCoordinate);

                        //plot
                        mMap.addMarker(new MarkerOptions().position(coordinate));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

                        System.out.println("bottom Count: " + count);
                        System.out.println("bottom Count check: " + (count % 2));
                        count = count + 1;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    };

    /*
    //get random numbers
    private static double getRandomNumberInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
    */
}