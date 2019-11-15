package com.Dryft.models;

public class Driver {

    private final int id;
    private final String name;
    private final Car car;
    private final char sex;
    private Location location;
    private double rating;
    private int reviews;
    private boolean onRoad;

    public Driver(int id, String name, Car car, Location location, char sex, double rating, int reviews, boolean onRoad) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.location = location;
        this.sex = sex;
        this.rating = rating;
        this.reviews = reviews;
        this.onRoad = onRoad;
    }

    public int getId() {
        return id;
    }

    public boolean isOnRoad() {
        return onRoad;
    }

    public void setOnRoad(boolean onRoad) {
        this.onRoad = onRoad;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public char getSex() {
        return sex;
    }

    public double getRating() {
        return rating;
    }
    
    public int getReviews() {
        return reviews;
    }

    public double updateRating(int review) {
        double total_rating = ((this.rating * this.reviews) + review);
        this.reviews++;
        this.rating = total_rating / this.reviews;
        return rating;
    }
}
