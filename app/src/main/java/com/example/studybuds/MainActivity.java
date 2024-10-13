package com.example.studybuds;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Get variables
    ImageButton homeButton, mapButton, classesButton;

    FirebaseFirestore db;
    EditText className, numPeople, typeOfStudy,sessionDesc;
    Spinner location;

    Button submitBtn;
    ScrollView homePage, mapPage, classesPage, newSessionForm;
    Context context;
    int lightPurple, darkPurple;
    LinearLayout layout;
    Button goToNewSessionButton;
    List<StudyLocation> locations;

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

        db = FirebaseFirestore.getInstance();

        // Assign all Android views to Java variables
        homeButton = findViewById(R.id.homeButton);
        mapButton = findViewById(R.id.mapButton);
        classesButton = findViewById(R.id.classesButton);
        homePage = findViewById(R.id.homePage);
        mapPage = findViewById(R.id.mapPage);
        classesPage = findViewById(R.id.classesPage);
        layout = findViewById(R.id.classesContainer);
        layout.setGravity(Gravity.START);
        goToNewSessionButton = findViewById(R.id.newSessionButton);
        newSessionForm = findViewById(R.id.addSessionPage);
        locations = new ArrayList<>();

        //create study session data
        className = findViewById(R.id.classNameEntry);
        location = findViewById(R.id.locationSpinner);
        numPeople = findViewById(R.id.numberOfPeople);
        typeOfStudy = findViewById(R.id.typeOfStudy);
        submitBtn = findViewById(R.id.inputInformationButton);

        submitBtn.setOnClickListener(v -> {


            String ClassName = className.getText().toString();
            String Location = location.getSelectedItem().toString();
            String NumPeople = numPeople.getText().toString();
            String TypeofStudy = typeOfStudy.getText().toString();

            if (ClassName.isEmpty() || Location.isEmpty() || NumPeople.isEmpty() || TypeofStudy.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;

            }

            Map<String, Object> classData = new HashMap<>();
            classData.put("className", ClassName);
            classData.put("location", Location);
            classData.put("numPeople", NumPeople);
            classData.put("typeOfStudy", TypeofStudy);
            classData.put("currPeople","0");


            db.collection("classes")
                .add((classData))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MainActivity.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Session Added " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error adding class", Toast.LENGTH_SHORT).show();
                    Log.w("Firestore", "Error adding document", e);
                });
            newSessionForm.setVisibility(View.GONE);
            classesPage.setVisibility(View.VISIBLE);

            //reload
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

        });

        @SuppressLint("CutPasteId") View classesContainer = findViewById(R.id.classesContainer);


        // Retrieve all documents from the "classes" collection
        db.collection("classes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Get the list of documents
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                // Retrieve the data
                                String documentId = document.getId();  // Get the document ID
                                String className = document.getString("className");  // Replace with your field names
                                String location = document.getString("location");
                                int numberOfPeople = Integer.parseInt(document.getString("numPeople"));
                                String studyType = document.getString("typeOfStudy");
                                int currPeople = Integer.parseInt(document.getString("currPeople"));
                                StudyLocation temp = new StudyLocation(className, studyType, currPeople,
                                        numberOfPeople,location,documentId);
                                locations.add(temp);
                            }
                            displayAllSessions(locations);
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                            mapFragment.getMapAsync(googleMap -> {
                                for(StudyLocation location: locations) {
                                    LatLng current = location.locationCoordinates;
                                    googleMap.addMarker(new MarkerOptions().position(current).title(location.locationName));
                                }
                            });
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        // Use ContextCompat to get color resources
        lightPurple = ContextCompat.getColor(this, R.color.lightUwPurple);
        darkPurple = ContextCompat.getColor(this, R.color.uwPurple);
        context = getApplicationContext();


        // Switch to home page
        homeButton.setOnClickListener(v -> {
            homePage.setVisibility(View.VISIBLE);
            homeButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
            mapPage.setVisibility(View.GONE);
            mapButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
            classesPage.setVisibility(View.GONE);
            classesButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
            newSessionForm.setVisibility(View.GONE);
            goToNewSessionButton.setBackground(createOvalDrawable(lightPurple));
        });

        // Switch to map page
        mapButton.setOnClickListener(v -> {
            homePage.setVisibility(View.GONE);
            homeButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            mapPage.setVisibility(View.VISIBLE);
            mapButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
            classesPage.setVisibility(View.GONE);
            classesButton.setBackground(createOvalDrawable(darkPurple)); // Reset others
            newSessionForm.setVisibility(View.GONE);
            goToNewSessionButton.setBackground(createOvalDrawable(lightPurple));
        });
        // Switch to classes Page
        classesButton.setOnClickListener(v -> {
            homePage.setVisibility(View.GONE);
            homeButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            mapPage.setVisibility(View.GONE);
            mapButton.setBackground(createOvalDrawable(darkPurple)); // Reset
            classesPage.setVisibility(View.VISIBLE);
            classesButton.setBackground(createOvalDrawable(lightPurple)); // Set oval background
            newSessionForm.setVisibility(View.GONE);
            goToNewSessionButton.setBackground(createOvalDrawable(lightPurple));
        });

        //switch to new session form
        goToNewSessionButton.setOnClickListener(v -> {
            newSessionForm.setVisibility(View.VISIBLE);
            classesPage.setVisibility(View.GONE);
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
        for(StudyLocation location: locations) {
            LatLng current = location.locationCoordinates;
            googleMap.addMarker(new MarkerOptions().position(current).title(location.locationName));
        }
    }

    private void displayAllSessions(List<StudyLocation> locations) {
        for (StudyLocation location: locations) {
            layout.addView(createLayout(location));
        }
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
                if (location.amountOfPeople == location.limitOfPeople) {
                    Toast.makeText(getApplicationContext(), "Session Full", Toast.LENGTH_SHORT).show();
                } else {
                    int newCurr = location.amountOfPeople + 1;
                    db.collection("classes").document(location.documentID).delete();
                    Map<String, Object> data = new HashMap<>();
                    data.put("className", location.className);
                    data.put("location", location.locationName);
                    data.put("numPeople", String.valueOf(location.limitOfPeople));
                    data.put("typeOfStudy", location.studyType);
                    data.put("currPeople", String.valueOf(newCurr));
                    db.collection("classes").add(data);
                    //reload
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
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
