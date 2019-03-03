package com.example.naveed.backup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private Button createAccount;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog LoadingBar;
    private DatabaseReference rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFields();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });

        mAuth= FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
    }

    private void createNewAccount() {
        String UserEmail= email.getText().toString();
        String UserPassword= password.getText().toString();

        if(TextUtils.isEmpty(UserEmail)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT);
        }
        if(TextUtils.isEmpty(UserPassword)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT);
        }
        else{
            LoadingBar.setTitle("Creating New Account...");
            LoadingBar.setMessage("Please wait");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();
            mAuth.createUserWithEmailAndPassword(UserEmail,UserPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                rootRef.child("Users").child(currentUserID).setValue("");
                                sendUserToMainActivity();
                                Toast.makeText(Register.this, "Account Created Successfully...", Toast.LENGTH_SHORT);
                                LoadingBar.dismiss();
                            }
                            else{
                                String message= task.getException().toString();
                                Toast.makeText(Register.this, "ERROR :" + message, Toast.LENGTH_SHORT);
                                LoadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(Register.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(Register.this, Login.class);
        startActivity(loginIntent);
    }

    private void InitializeFields() {

        createAccount = findViewById(R.id.btnRegister);
        email= findViewById(R.id.registerEmail);
        password= findViewById(R.id.registerPassword);
        LoadingBar = new ProgressDialog(this);

    }
}
