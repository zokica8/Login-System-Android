package com.zoranvasilic.loginregistrationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
// user login form
public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signInButton;
    private Button signUpButton;
    private TextView textView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        textView = (TextView) findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener((view) -> {
            loginUser();
        });

        signUpButton.setOnClickListener((view) -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        textView.setOnClickListener((view) -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });


    }

    public void loginUser() {
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

        firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, (task) -> {
                    if(task.isSuccessful()) {
                        // user is logged in !
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                    else {
                        Toast.makeText(this, "Login Failed! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
