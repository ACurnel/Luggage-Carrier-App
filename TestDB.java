package com.example.luggagecarrier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestDB extends AppCompatActivity {

    /* Define the UI elements */
    private Button btnStart;
    private TextView tvOut;
    private FirebaseDatabase firebaseCord;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_d_b);

        //database
        firebaseCord = FirebaseDatabase.getInstance();
        DatabaseReference Ref = firebaseCord.getReference();

        /* Bind the XML views to Java Code Elements */
        btnStart = (Button)findViewById(R.id.btnStartTrack);
        tvOut = (TextView)findViewById(R.id.tvTestOut);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read from database and display in text view
                Ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            lat= snapshot.child("Lat").getValue().toString();
                            lon= snapshot.child("Lon").getValue().toString();

                            System.out.println(lat + ", " + lon);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}