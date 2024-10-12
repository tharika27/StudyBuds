package com.example.studybuds;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //get variables
    ImageButton homeButton, mapButton, classesButton;
    ScrollView homePage, mapPage, classesPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //margins of the screen
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //assign all android views to java variables
        homeButton = findViewById(R.id.homeButton);
        mapButton = findViewById(R.id.mapButton);
        classesButton = findViewById(R.id.classesButton);
        homePage = findViewById(R.id.homePage);
        mapPage = findViewById(R.id.mapPage);
        classesPage = findViewById(R.id.classesPage);

        //switch pages on click
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}