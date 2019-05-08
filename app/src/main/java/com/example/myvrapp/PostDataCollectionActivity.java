package com.example.myvrapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostDataCollectionActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    Button logout, display;
    TextView userName;
    private Spinner vrInv, health, psych, time_bal;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data_collection);

        Temple temple = (Temple)getIntent().getSerializableExtra("temple");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(temple.getUrl()));
        startActivity(browserIntent);
        databaseReference = FirebaseDatabase.getInstance().getReference("PostUseInfo");


        userName = (TextView) findViewById(R.id.userName2);
        logout = (Button)findViewById(R.id.logoutBtn2);
        display = (Button)findViewById(R.id.displayBtn2);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null || firebaseAuth.getCurrentUser().getDisplayName() == null) {
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null && user.getEmail().startsWith("doc"))
            startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
        if(user != null)
            userName.setText("Hello, " + user.getDisplayName().toUpperCase() + "\nRate your experience");

        vrInv = (Spinner)findViewById(R.id.spinner42);
        ArrayAdapter<String> myadapter4 = new ArrayAdapter<>(PostDataCollectionActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vrInv.setAdapter(myadapter4);
        health = (Spinner)findViewById(R.id.spinner52);
        ArrayAdapter<String> myadapter5 = new ArrayAdapter<>(PostDataCollectionActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        health.setAdapter(myadapter5);
        psych = (Spinner)findViewById(R.id.spinner62);
        ArrayAdapter<String> myadapter6 = new ArrayAdapter<>(PostDataCollectionActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psych.setAdapter(myadapter6);
        time_bal = (Spinner)findViewById(R.id.spinner72);
        ArrayAdapter<String> myadapter7 = new ArrayAdapter<>(PostDataCollectionActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_bal.setAdapter(myadapter7);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
                startActivity(new Intent(getApplicationContext(), TempleActivity.class));
            }
        });

    }

    private void saveUserInformation()
    {
        String vrInvStr = vrInv.getSelectedItem().toString();
        String healthStr = health.getSelectedItem().toString();
        String psychStr = psych.getSelectedItem().toString();
        String timeBalStr = time_bal.getSelectedItem().toString();
        UserInformation userInformation = new UserInformation();
        userInformation.setVrInvolvement(vrInvStr);
        userInformation.setHealthAndEnergyLevel(healthStr);
        userInformation.setPsychologicalWellBeing(psychStr);
        userInformation.setTimeBalance(timeBalStr);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userInformation.setName(user.getDisplayName());
        userInformation.setEmail(user.getEmail());
        userInformation.setUserId(user.getUid());
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(), "Info saved", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), TempleActivity.class));
    }

    @Override
    public void onBackPressed() {

    }
}
