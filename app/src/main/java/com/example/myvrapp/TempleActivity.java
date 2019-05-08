package com.example.myvrapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TempleActivity extends AppCompatActivity {

    private ListView templeList;
    List<Temple> temples= new ArrayList<Temple>();
    private FirebaseListAdapter adapter;
    private DatabaseReference databaseReference,databaseReference2;
    private String userLocation  ;
    public String userReligion  ;
    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple);
        templeList = (ListView) findViewById(R.id.templeList);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null || firebaseAuth.getCurrentUser().getDisplayName() == null) {
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null && user.getEmail().startsWith("doc"))
            startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
//        Query query = FirebaseDatabase.getInstance().getReference().child("Temple");
//        FirebaseListOptions<Temple> options = new FirebaseListOptions.Builder<Temple>()
//                .setLayout(R.layout.templerow_list)
//                .setLifecycleOwner(TempleActivity.this)
//                .setQuery(query, Temple.class)
//                .build();
//        adapter = new FirebaseListAdapter(options) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
//                TextView name = v.findViewById(R.id.TempleName);
//                TextView city = v.findViewById(R.id.TempleCity);
//                ImageView image = v.findViewById(R.id.TempleImage);
//
//                Temple temple = (Temple)model;
//                name.setText(temple.getName());
//                city.setText(temple.getCity());
//                Picasso.with(TempleActivity.this).load(temple.getImage().toString()).into(image);
//            }
//
//        };
//        templeList.setAdapter(adapter);
//
        templeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Temple temple = temples.get(position);
                Intent intent;
                if(temple.getUrl()!=null)
                {
                    intent = new Intent(getApplicationContext(), PostDataCollectionActivity.class);
                    intent.putExtra("temple", temple);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(getApplicationContext(), TempleImageActivity.class);
                    intent.putExtra("temple", temple);
                    startActivity(intent);
                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Temple");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid());


    }



    @Override
    protected void onStart() {
        super.onStart();

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                userLocation = user.getLocation();
                userReligion = user.getReligion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temples.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren() )
                {
                    Temple temple = dataSnapshot1.getValue(Temple.class);
                    temples.add(temple);
                }
                temples = sortByDistance(temples);
                CustomListAdapter templeListAdapter = new CustomListAdapter(TempleActivity.this, temples);
                templeList.setAdapter(templeListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private List<Temple> sortByDistance(List<Temple> templesList)
    {
        try {
            Geocoder gc = new Geocoder(this);
            List<Address> addresses = gc.getFromLocationName(userLocation, 1);
            Address address = addresses.get(0);
            Location from = new Location(LocationManager.GPS_PROVIDER);
            from.setLatitude(address.getLatitude());
            from.setLongitude(address.getLongitude());
            for(Temple temple: templesList)
            {
                String toCity = temple.getCity();
                addresses = gc.getFromLocationName(toCity, 1);
                address = addresses.get(0);
                Location to = new Location(LocationManager.GPS_PROVIDER);
                to.setLatitude(address.getLatitude());
                to.setLongitude(address.getLongitude());
                float dist = from.distanceTo(to);
                temple.setDist(Math.round(dist));
            }
            Collections.sort(templesList, new SortByDist());
            ArrayList<Temple> others = new ArrayList<>();
            ArrayList<Temple> rel = new ArrayList<>();
            for(Temple temple: templesList)
            {
                if(temple.getReligion().equals(userReligion))
                    rel.add(temple);
                else
                    others.add(temple);
            }
            templesList.clear();
            templesList.addAll(rel);
            templesList.addAll(others);

        }
        catch (Exception e)
        {
            Log.e("error {}", e.getMessage());
        }
        return templesList;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {

    }

}
