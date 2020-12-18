package com.example.luggagecarrier;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

public class Connect extends AppCompatActivity{

    //Ui elements
    private Button btnSettings;
    private Button btnTrack;
    private int REQUEST_ENABLE_BT = 1;
    //private UUID mUUID = UUID.fromString("A62EC0B1-4524-538F-9C5A-E93EB4F9CACD");
    //UUID for bluetooth RFCOMM channel



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //casting buttons
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnTrack = (Button) findViewById(R.id.btnTrack);

        //test
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);

        //turn on bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //take me to the bluetooth settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        /* btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(bluetoothAdapter.getBondedDevices());
            }
        }); */

        btnTrack.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //go to map,
                if (!bluetoothAdapter.getBondedDevices().isEmpty()) {
                    /*-- this is what i was doing during testing
                    BluetoothDevice btDevice = bluetoothAdapter.getRemoteDevice("A0:99:9B:13:0F:26");
                    System.out.println("UUID: " + mUUID);
                    System.out.println("bonded device: " + bluetoothAdapter.getBondedDevices());
                    System.out.println("bonded device: " + btDevice);
                    System.out.println("get address: " + bluetoothAdapter.getAddress());
                    System.out.println("phone name: " + bluetoothAdapter.getName());
                    System.out.println("computer name: " + btDevice.getName());

                    /* -- This is what would have triggered the connection
                    ConnectThread thread1 = new ConnectThread();
                    thread1.connect(btDevice, mUUID);
                    startActivity(new Intent(Connect.this, MapsActivity.class)); */

                    BluetoothDevice btDevice = bluetoothAdapter.getRemoteDevice("A0:99:9B:13:0F:26"); //MAC address of laptop

                    Toast.makeText(Connect.this, "Device " + bluetoothAdapter.getName() + " is connected to "
                            + btDevice.getName(), Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Connect.this, MapsActivity.class));
                }else{
                    Toast.makeText(Connect.this, "Not connected to Bluetooth", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}