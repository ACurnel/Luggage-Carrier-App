package com.example.luggagecarrier;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng coordinate;
    private String[] words;
    private ArrayList<String> Lat;
    private ArrayList<String> Lon;
    private Double dWordsLat;
    private Double dWordsLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //type casting for btn
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

       /* //generate coordinate
        double latCoordinate1 = getRandomNumberInRange(-90, 90);
        double lonCoordinate1 = getRandomNumberInRange(-180, 180);
        LatLng coordinate1 = new LatLng(latCoordinate1, lonCoordinate1);

        // Add a marker at new coordinate and move the camera
        mMap.addMarker(new MarkerOptions().position(coordinate1).title("Some device info could be here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate1)); */

    }

    //on button click: clear map, generate new point, map it and move camera
    View.OnClickListener btnNewPointGenerator = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //clear
            mMap.clear();
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            //generate
            /*latCoordinate = getRandomNumberInRange(-90,90);
            lonCoordinate = getRandomNumberInRange(-180, 180);*/
            readFile("test.txt");

            String tempLat;
            String tempLon;
            for (int i = 0; i < Lat.size(); i++){
                mMap.clear();

                //takes in data conversion for points
                tempLat = Lat.get(i);
                tempLon = Lon.get(i);
                dWordsLat = Double.parseDouble(tempLat);
                dWordsLon = Double.parseDouble(tempLon);

                //make point
                coordinate = new LatLng(dWordsLat, dWordsLon);

                //plot and move
                mMap.addMarker(new MarkerOptions().position(coordinate));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
                System.out.println("coordinate just plotted: " + coordinate);

                //wait


            }
        }
    };


    //get random numbers
    private static double getRandomNumberInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    /* private void readFile2(String fileName) throws IOException {
       ArrayList<String> first = null;
       Scanner console = new Scanner(getAssets().open(fileName));
       int i = 0;
       while (console.hasNextLine()) {
           String z = console.nextLine();
           String[] zip = z.split(" ");
           first.add(zip[i]);
           i = i + 1;
       }

       Toast.makeText(MapsActivity.this, "Text: " + first, Toast.LENGTH_LONG).show();
       System.out.print(first + "\n");
    } */

    //read the data v2
    private void readFile(String fileName) {
        String text = "";
        try {
            InputStream is = getAssets().open(fileName);
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            //reads in data and adds it to list
            Lat = new ArrayList<String>();
            Lon = new ArrayList<String>();
            while ((text = br.readLine()) != null) {
                words = text.split(" ");
                Lat.add(words[0]);
                Lon.add(words[1]);
            }
            System.out.println("Lat: " + Lat + "\nLon: " + Lon);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

            //reading in data using a buffer that is dynamically sized
            /*int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);*/

    }
}
