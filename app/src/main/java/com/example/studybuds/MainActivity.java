package com.example.studybuds;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
    LinearLayout layout;

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
        layout = findViewById(R.id.classesContainer);
        layout.setGravity(Gravity.START);


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
        //TODO: add pointers to the map
        LatLng uw = new LatLng(47.6567, -122.3066);
        googleMap.addMarker(new MarkerOptions()
                .position(uw)
                .title("University Of Washington"));
    }

    //populate final page
    public LinearLayout createLayout(StudyLocation location) {
        LinearLayout result = new LinearLayout(this);
        result.setOrientation(LinearLayout.VERTICAL);
        result.setGravity(Gravity.END);

        TextView title = new TextView(this);
        title.setTextSize(40);
        title.setText(location.className);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        result.addView(title);

        TextView studyType = new TextView(this);
        studyType.setTextSize(25);
        studyType.setText(location.studyType);
        result.addView(studyType);

        LinearLayout horizontalLayout = new LinearLayout(this);
        Button button = new Button(this);
        String setText = "Join Now";
        button.setText(setText);
        button.setBackgroundColor(lightPurple);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundColor(darkPurple);
            }
        });


        TextView people = new TextView(this);
        people.setTextSize(20);
        people.setPadding(0,0,300,0);
        String peopleText = location.amountOfPeople + " people";
        people.setText(peopleText);

        horizontalLayout.addView(people);
        horizontalLayout.addView(button);
        result.addView(horizontalLayout);

        TextView locationName = new TextView(this);
        locationName.setTextSize(20);
        locationName.setText(location.locationName);
        result.addView(locationName);

        result.setPadding(50, 50, 100,50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        result.setLayoutParams(params);
        result.setPadding(50,50,0,50);

        return result;
    }
    
}
