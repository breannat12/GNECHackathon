package com.example.gnechackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class child_home extends AppCompatActivity {

    private Button formButton;
    private Button safeSpotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);

        formButton = (Button) findViewById(R.id.formButton);
        formButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForm();
            }
        });

        safeSpotButton = (Button) findViewById(R.id.safeSpotButton);
        safeSpotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSafeSpots();
            }
        });
    }

    public void openForm() {
        Intent intent = new Intent(this, form.class);
        startActivity(intent);
    }

    public void openSafeSpots() {
        Intent intent = new Intent(this, safeSpots.class);
        startActivity(intent);
    }
}