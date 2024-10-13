package com.example.studybuds;

public class ClassData {
    private String className;
    private String location;
    private Long numPeople;
    private String typeOfStudy;

    public ClassData(String className, String location, Long numPeople, String typeOfStudy) {
        this.className = className;
        this.location = location;
        this.numPeople = numPeople;
        this.typeOfStudy = typeOfStudy;
    }

    // Getters
    public String getClassName() { return className; }
    public String getLocation() { return location; }
    public Long getNumPeople() { return numPeople; }
    public String getTypeOfStudy() { return typeOfStudy; }
}
