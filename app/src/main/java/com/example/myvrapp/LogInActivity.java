package com.example.myvrapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    EditText password;
    EditText email;
    Button logIn, signUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = (EditText)findViewById(R.id.emailLogIn);
        password = (EditText)findViewById(R.id.password);
        logIn = (Button)findViewById(R.id.logInBtn);
        signUp = (Button)findViewById(R.id.signUpButton);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null && user.getEmail().startsWith("doc"))
        {
            startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
        }
        else if(user!=null)
        {
            startActivity(new Intent(getApplicationContext(), data_collection.class));
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void userLogin() {
        final String emailStr = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(emailStr)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(emailStr, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(emailStr.startsWith("doc"))
                                startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
                            else
                                startActivity(new Intent(getApplicationContext(), data_collection.class));
                        }
                        else
                        {
                            Log.d("Error in logging in ", task.getException().getMessage());
                            Toast.makeText(LogInActivity.this, "Log In failed, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
