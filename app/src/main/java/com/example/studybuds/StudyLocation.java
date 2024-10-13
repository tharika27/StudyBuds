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
                         int amountOfPeople, int limitOfPeople, String locationName,
                         LatLng locationCoordinates) {
        this.className = className;
        this.studyType = studyType;
        this.amountOfPeople = amountOfPeople;
        this.limitOfPeople = limitOfPeople;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
    }
}
