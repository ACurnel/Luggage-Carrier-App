package com.example.luggagecarrier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eUsername;
    private EditText ePassword;
    private Button eLogin;
    private Button userRegistration;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bind the XML views to Java Code Elements */
        eUsername = (EditText)findViewById(R.id.etUsername);
        ePassword = (EditText)findViewById(R.id.etPassword);
        eLogin = (Button)findViewById(R.id.btnLogin);
        userRegistration = (Button)findViewById(R.id.btnRegister);
        firebaseAuth = FirebaseAuth.getInstance(); //initializes DB

        //need to make sure someone isn't already logged in and if so
        //go to tracking page
       /*--------- FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, Tracking.class));
        }

        /* Describe the logic when the login button is clicked */
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Obtain user inputs */
                String userName = eUsername.getText().toString();
                String userPassword = ePassword.getText().toString();

                /* Check if the user inputs are empty */
                if(userName.isEmpty() || userPassword.isEmpty()) {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(MainActivity.this, "Please enter username and password!", Toast.LENGTH_LONG).show();

                }else {
                    /* Validate the user inputs */
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    //validate(userName, userPassword); ******* UNCOMMENT THIS IS EASIER FOR TESTING

                }
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }

    /* Validate the credentials */
    private void validate(String userName, String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user.isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TestDB.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}