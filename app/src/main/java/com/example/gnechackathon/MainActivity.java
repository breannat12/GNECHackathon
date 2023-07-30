package com.example.gnechackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button childHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        childHome = (Button) findViewById(R.id.ChildButton);
        childHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChildHome();
            }
        });
    }

    public void openChildHome() {
        Intent intent = new Intent(this, child_home.class);
        startActivity(intent);
    }
}