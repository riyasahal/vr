package com.example.myvrapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {

    TextView userName, psychBefore, psychAfter, vrInvolvement, commSupp, healthBefore, healthAfter, timeBalBefore, timeBalAfter, HIbefore, HIafter;
    private Button logout;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private BarChart barChart;
    private UserInformation userBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        userBefore = (UserInformation) getIntent().getSerializableExtra("user");
        firebaseAuth = FirebaseAuth.getInstance();
        barChart = (BarChart) findViewById(R.id.chart);
        userName = (TextView) findViewById(R.id.userName3);
        userName.setText(userBefore.getName().toUpperCase());
        float HIb = (Float.parseFloat(userBefore.getCommunitySupport()) + Float.parseFloat(userBefore.getPsychologicalWellBeing()) + Float.parseFloat(userBefore.getHealthAndEnergyLevel()) + Float.parseFloat(userBefore.getTimeBalance()))/4;
        HIbefore = (TextView) findViewById(R.id.HappInd);
        HIbefore.setText(Float.toString(HIb));
        databaseReference = FirebaseDatabase.getInstance().getReference("PostUseInfo").child(userBefore.getUserId());
        HIafter = (TextView) findViewById(R.id.HappIndAfter);

        logout = (Button) findViewById(R.id.logoutBtn3);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                ArrayList barGroup1 = new ArrayList();
                barGroup1.add(new BarEntry(Integer.parseInt(userBefore.getCommunitySupport() == null ? "0" : userBefore.getCommunitySupport()), 0));
                barGroup1.add(new BarEntry(Integer.parseInt(userBefore.getHealthAndEnergyLevel() == null ? "0" : userBefore.getHealthAndEnergyLevel()), 1));
                barGroup1.add(new BarEntry(Integer.parseInt(userBefore.getPsychologicalWellBeing() == null ? "0" : userBefore.getPsychologicalWellBeing()), 2));
                barGroup1.add(new BarEntry(Integer.parseInt(userBefore.getTimeBalance() == null ? "0" : userBefore.getTimeBalance()), 3));
                barGroup1.add(new BarEntry(0, 4));


                ArrayList barGroup2 = new ArrayList();
                barGroup2.add(new BarEntry(0, 0));
                barGroup2.add(new BarEntry(Integer.parseInt(user.getHealthAndEnergyLevel() == null ? "0" : user.getHealthAndEnergyLevel()), 1));
                barGroup2.add(new BarEntry(Integer.parseInt(user.getPsychologicalWellBeing() == null ? "0" : user.getPsychologicalWellBeing()), 2));
                barGroup2.add(new BarEntry(Integer.parseInt(user.getTimeBalance() == null ? "0" : user.getTimeBalance()), 3));
                barGroup2.add(new BarEntry(Integer.parseInt(user.getVrInvolvement() == null ? "0" : user.getVrInvolvement()), 4));

                BarDataSet barDataSet1 = new BarDataSet(barGroup1, "Before");
                barDataSet1.setColor(Color.rgb(159, 217, 242));
                BarDataSet barDataSet2 = new BarDataSet(barGroup2, "After");
                barDataSet2.setColor(Color.rgb(100, 229, 125));

                final ArrayList<String> labels = new ArrayList();
                labels.add("CS");
                labels.add("H&E");
                labels.add("Psych");
                labels.add("Time");
                labels.add("VR");

                ArrayList dataSets = new ArrayList();
                dataSets.add(barDataSet1);
                dataSets.add(barDataSet2);

                BarData data = new BarData(labels, dataSets);
                barChart.setData(data);
                barChart.setDescription("User details");
                barChart.animateXY(2000, 2000);
                barChart.invalidate();
                float HIa = (Float.parseFloat(user.getVrInvolvement()) + Float.parseFloat(user.getPsychologicalWellBeing()) + Float.parseFloat(user.getHealthAndEnergyLevel()) + Float.parseFloat(user.getTimeBalance()))/4;
                HIafter.setText(Float.toString(HIa));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
