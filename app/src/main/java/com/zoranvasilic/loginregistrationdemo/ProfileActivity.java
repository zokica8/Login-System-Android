package com.zoranvasilic.loginregistrationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

// activity which shows that the user is logged in!
public class ProfileActivity extends AppCompatActivity {

    // add logout button
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        firebaseAuth = FirebaseAuth.getInstance();

        logoutButton.setOnClickListener((view) -> {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
