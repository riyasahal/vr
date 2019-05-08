package com.example.myvrapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class TempleImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button back;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_image);

        Temple temple = (Temple)getIntent().getSerializableExtra("temple");
        imageView = (ImageView)findViewById(R.id.templeDisplayImage);
        back = (Button) findViewById(R.id.backBtn1);
        Picasso.with(getApplicationContext()).load(temple.getImage().toString()).into(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TempleActivity.class));
            }
        });
    }


}
