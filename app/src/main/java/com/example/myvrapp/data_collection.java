package com.example.myvrapp;

import android.app.Application;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class data_collection extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    Button logout, display;
    TextView userName;
    private Spinner templev, slept, appetite;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");


        userName = (TextView) findViewById(R.id.userName);
        logout = (Button)findViewById(R.id.logoutBtn);
        display = (Button)findViewById(R.id.displayBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        userName.setText("Hello, " + user.getDisplayName());

        templev = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.choices));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        templev.setAdapter(myadapter);
        slept = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> myadapter2 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.hours));
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slept.setAdapter(myadapter2);
        appetite = (Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> myadapter3 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.appetite));
        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appetite.setAdapter(myadapter3);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://scapic.com/@riyasahal/temple_1-k0zZp"));
//                startActivity(browserIntent);
            }
        });

    }

    private void saveUserInformation()
    {
        String visitTemple = templev.getSelectedItem().toString();
        String hoursSlept =  slept.getSelectedItem().toString();
        String appetiteStr = appetite.getSelectedItem().toString();
        UserInformation userInformation = new UserInformation();
        userInformation.setVisitTemple(visitTemple);
        userInformation.setAppetite(appetiteStr);
        userInformation.setNumberOfHoursSlept(hoursSlept);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(), "info saved", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), TempleActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 200);
    }
}
