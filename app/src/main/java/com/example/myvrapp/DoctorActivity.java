package com.example.myvrapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity{

    private ListView userList;
    private DatabaseReference databaseReference;
    private ArrayList<UserInformation> userInformationList = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        userList = (ListView) findViewById(R.id.userListView);
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInformation userInformation = userInformationList.get(position);
                Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
                intent.putExtra("user", userInformation);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInformationList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UserInformation userInformation = dataSnapshot1.getValue(UserInformation.class);
                    userInformationList.add(userInformation);
                }
                UserListAdapter adapter = new UserListAdapter(DoctorActivity.this, userInformationList);
                userList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}


