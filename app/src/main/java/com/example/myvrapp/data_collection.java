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
import android.widget.EditText;
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
    private Spinner templev, slept, appetite, comm_support, health, psych, time_bal, religion;
    private EditText location;
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
        if (firebaseAuth.getCurrentUser() == null || firebaseAuth.getCurrentUser().getDisplayName() == null) {
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null && user.getEmail().startsWith("doc"))
            startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
        if(user != null)
            userName.setText("Hello, " + user.getDisplayName().toUpperCase());

        religion = (Spinner) findViewById(R.id.religionSpinner);
        ArrayAdapter<String> myadapter8 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.religion));
        myadapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        religion.setAdapter(myadapter8);
        location = (EditText) findViewById(R.id.cityText);
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

        comm_support = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> myadapter4 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comm_support.setAdapter(myadapter4);
        health = (Spinner)findViewById(R.id.spinner5);
        ArrayAdapter<String> myadapter5 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        health.setAdapter(myadapter5);
        psych = (Spinner)findViewById(R.id.spinner6);
        ArrayAdapter<String> myadapter6 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psych.setAdapter(myadapter6);
        time_bal = (Spinner)findViewById(R.id.spinner7);
        ArrayAdapter<String> myadapter7 = new ArrayAdapter<>(data_collection.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.range));
        myadapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_bal.setAdapter(myadapter7);

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
        String commSuppStr = comm_support.getSelectedItem().toString();
        String healthStr = health.getSelectedItem().toString();
        String psychStr = psych.getSelectedItem().toString();
        String timeBalStr = time_bal.getSelectedItem().toString();
        String locationStr = location.getText().toString();
        String religionStr = religion.getSelectedItem().toString();
        UserInformation userInformation = new UserInformation();
        userInformation.setReligion(religionStr);
        userInformation.setLocation(locationStr);
        userInformation.setVisitTemple(visitTemple);
        userInformation.setAppetite(appetiteStr);
        userInformation.setNumberOfHoursSlept(hoursSlept);
        userInformation.setCommunitySupport(commSuppStr);
        userInformation.setHealthAndEnergyLevel(healthStr);
        userInformation.setPsychologicalWellBeing(psychStr);
        userInformation.setTimeBalance(timeBalStr);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userInformation.setName(user.getDisplayName());
        userInformation.setEmail(user.getEmail());
        userInformation.setUserId(user.getUid());
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(), "info saved", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), TempleActivity.class));
    }

    @Override
    public void onBackPressed() {

    }
}
