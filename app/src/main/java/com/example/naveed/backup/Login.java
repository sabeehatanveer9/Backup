package com.example.naveed.backup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private FirebaseUser currentUser;
    private Button loginButton, phoneLoginButton;
    private EditText email, password;
    private TextView newAccountLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeFields();
        newAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        });
    }

    private void InitializeFields() {
        loginButton = findViewById(R.id.btnLogin);
        phoneLoginButton= findViewById(R.id.btnPhoneLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        newAccountLink = findViewById(R.id.tvRegister);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null){
            sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity() {

        Intent loginIntent = new Intent(Login.this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void sendUserToRegisterActivity() {

        Intent registerIntent = new Intent(Login.this, Register.class);
        startActivity(registerIntent);
    }
}
