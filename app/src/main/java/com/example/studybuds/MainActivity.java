package com.example.studybuds;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Get variables
    ImageButton homeButton, mapButton, classesButton;
    ScrollView homePage, mapPage, classesPage;
    Context context;
    int lightPurple, darkPurple;

    ListView classesListView1, classesListView2, classesListView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Margins of the screen
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Assign all Android views to Java variables
        homeButton = findViewById(R.id.homeButton);
        mapButton = findViewById(R.id.mapButton);
        classesButton = findViewById(R.id.classesButton);
        homePage = findViewById(R.id.homePage);
        mapPage = findViewById(R.id.mapPage);
        classesPage = findViewById(R.id.classesPage);

        // Use ContextCompat to get color resources
        lightPurple = ContextCompat.getColor(this, R.color.lightUwPurple);
        darkPurple = ContextCompat.getColor(this, R.color.uwPurple);
        context = getApplicationContext();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Switch to home page
        homeButton.setOnClickListener(v -> {
            homePage.setVisibility(View.VISIBLE);
            homeButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
            mapPage.setVisibility(View.GONE);
            mapButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
            classesPage.setVisibility(View.GONE);
            classesButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
        });

        // Switch to map page
        mapButton.setOnClickListener(v -> {
            homePage.setVisibility(View.GONE);
            homeButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            mapPage.setVisibility(View.VISIBLE);
            mapButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
            classesPage.setVisibility(View.GONE);
            classesButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
        });

        // Switch to classes Page
        classesButton.setOnClickListener(v -> {
            homePage.setVisibility(View.GONE);
            homeButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            mapPage.setVisibility(View.GONE);
            mapButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            classesPage.setVisibility(View.VISIBLE);
            classesButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
        });

        classesListView1 = findViewById(R.id.classesListView1);
        classesListView2 = findViewById(R.id.classesListView2);
        classesListView3 = findViewById(R.id.classesListView3);

        String[] classes1 = {"CSE 351"};
        String[] classes2 = {"MATH 125"};
        String[] classes3 = {"CLAS 430"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, classes1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, classes2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, classes3);

        classesListView1.setAdapter(adapter1);
        classesListView2.setAdapter(adapter2);
        classesListView3.setAdapter(adapter3);
    }

    // Method to create an oval shape drawable
    private GradientDrawable createOvalDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        return drawable;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng uw = new LatLng(47.6567, -122.3066);
        googleMap.addMarker(new MarkerOptions()
                .position(uw)
                .title("University Of Washington"));
    }
}
