package com.example.luggagecarrier;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Connect extends AppCompatActivity {

    //Ui elements
    private Button btnConnect;
    private Button btnTrack;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private int REQUEST_ENABLE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        //casting buttons
        btnConnect = (Button)findViewById(R.id.btnConnect);
        btnTrack = (Button)findViewById(R.id.btnTrack);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do the stuff to connect to bluetooth
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to map,  TODO: IF connected to BT
                    startActivity(new Intent(Connect.this, MapsActivity.class));
            }
        });
    }
}