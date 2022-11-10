package edu.oregonstate.models;

public class Coordinates {

    private Long experienceId;

    private double latitude;

    private double longitude;

    public Coordinates() {

    }

    public Coordinates(Long experienceId, double latitude, double longitude) {
        this.experienceId = experienceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
