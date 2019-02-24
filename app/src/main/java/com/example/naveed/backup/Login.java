package com.example.naveed.backup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private Button loginButton, phoneLoginButton;
    private EditText email, password;
    private TextView newAccountLink;
    private ProgressDialog LoadingBar;

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
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        String UserEmail= email.getText().toString();
        String UserPassword= password.getText().toString();

        if(TextUtils.isEmpty(UserEmail)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT);
        }
        if(TextUtils.isEmpty(UserPassword)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT);
        }
        else{
            LoadingBar.setTitle("Sign-In");
            LoadingBar.setMessage("Signing!!! Please wait");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();
            mAuth.signInWithEmailAndPassword(UserEmail, UserPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUserToMainActivity();
                                LoadingBar.dismiss();
                            }
                            else {
                                String message= task.getException().toString();
                                Toast.makeText(Login.this, "ERROR :" + message, Toast.LENGTH_SHORT);
                                LoadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void InitializeFields() {
        loginButton = findViewById(R.id.btnLogin);
        phoneLoginButton= findViewById(R.id.btnPhoneLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        newAccountLink = findViewById(R.id.tvRegister);
        LoadingBar = new ProgressDialog(this);

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
