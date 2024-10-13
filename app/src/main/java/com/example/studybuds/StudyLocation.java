package com.example.studybuds;

import com.google.android.gms.maps.model.LatLng;

public class StudyLocation {
    String className;
    String studyType;
    int amountOfPeople;
    int limitOfPeople;
    String locationName;
    LatLng locationCoordinates;

    public StudyLocation(String className, String studyType,
                         int amountOfPeople, int limitOfPeople, String locationName) {
        this.className = className;
        this.studyType = studyType;
        this.amountOfPeople = amountOfPeople;
        this.limitOfPeople = limitOfPeople;
        this.locationName = locationName;

        if (this.locationName.equals("Suzzalo")) {
            this.locationCoordinates = new LatLng(47.6558, -122.3078);
        } else if (this.locationName.equals("Odegaard")) {
            this.locationCoordinates = new LatLng(47.6565, -122.3104);
        } else if (this.locationName.equals("CSE 1")) {
            this.locationCoordinates = new LatLng(47.6534, -122.3061);
        } else if (this.locationName.equals("Paccar")) {
            this.locationCoordinates = new LatLng(47.6594, -122.3090);
        } else if (this.locationName.equals("CSE 2") || this.locationName.equals("Microsoft Cafe")) {
            this.locationCoordinates = new LatLng(47.65309, -122.30440);
        } else if (this.locationName.equals("Loew Hall")) {
            this.locationCoordinates = new LatLng(47.65440, -122.30443);
        } else if (this.locationName.equals("HUB")){
            this.locationCoordinates = new LatLng(47.65540,-122.30507);
        } else {
            this.locationCoordinates = new LatLng(47.6567,-122.3066);
        }
    }
}
