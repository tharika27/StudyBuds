package com.example.studybuds;

import com.google.android.gms.maps.model.LatLng;

public class StudyLocation {
    String className;
    String studyType;
    int amountOfPeople;
    int limitOfPeople;
    String locationName;
    LatLng locationCoordinates;
    String documentID;

    public StudyLocation(String className, String studyType,
                         int amountOfPeople, int limitOfPeople, String locationName, String documentID) {
        this.className = className;
        this.studyType = studyType;
        this.amountOfPeople = amountOfPeople;
        this.limitOfPeople = limitOfPeople;
        this.locationName = locationName;
        this.documentID = documentID;

        switch (this.locationName) {
            case "Suzzalo":
                this.locationCoordinates = new LatLng(47.6558, -122.3078);
                break;
            case "Odegaard":
                this.locationCoordinates = new LatLng(47.6565, -122.3104);
                break;
            case "CSE 1":
                this.locationCoordinates = new LatLng(47.6534, -122.3061);
                break;
            case "Paccar":
                this.locationCoordinates = new LatLng(47.6594, -122.3090);
                break;
            case "CSE 2":
            case "Microsoft Cafe":
                this.locationCoordinates = new LatLng(47.65309, -122.30440);
                break;
            case "Loew Hall":
                this.locationCoordinates = new LatLng(47.65440, -122.30443);
                break;
            case "HUB":
                this.locationCoordinates = new LatLng(47.65540, -122.30507);
                break;
            default:
                this.locationCoordinates = new LatLng(47.6567, -122.3066);
                break;
        }
    }
}
