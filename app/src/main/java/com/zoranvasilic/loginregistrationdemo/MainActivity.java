package com.zoranvasilic.loginregistrationdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// for registering the user
public class MainActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button signInButton;
    private EditText email;
    private EditText password;
    private TextView textView;
    private TextView userVerified;

    private FirebaseAuth firebaseAuth;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        textView = (TextView) findViewById(R.id.textView);
        userVerified = (TextView) findViewById(R.id.userVerified);

        signUpButton.setOnClickListener((view) -> {
           registerUser();
        });

        signInButton.setOnClickListener((view) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

    }

    public void registerUser() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if(TextUtils.isEmpty(emailText)) {
            // email can't be empty! Email is empty!
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            // here we should stop registering the user!
            return;
        }

        if(TextUtils.isEmpty(passwordText)) {
            // password can't be empty! Password is empty!
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            // here we should stop registering the user!
            return;
        }



        if(user.isEmailVerified()) {
            userVerified.setText("Email Verified");
        }
        else {
            userVerified.setText("Email Not Verified. Confirm your email." +
                    "Click on the email link.");

            // this works, but not on the first try, on the second try it works!
            user.sendEmailVerification().addOnCompleteListener(this, (task) -> {

                findViewById(R.id.verify_email_button).setEnabled(true);

                if(task.isSuccessful()) {
                    Toast.makeText(this, "Verification email sent to: " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener((task) -> {
                Log.e("EmailNotSent", task.getMessage());
                Toast.makeText(this, "Failed to send verification email to: " + user.getEmail(),
                        Toast.LENGTH_SHORT).show();
            });

            // this method creates the user and already signs the user!
            // this is a problem when the app is running, the user is in session and he is deleted!
            // Where this code needs to go?
            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText);
        }

    }
}
