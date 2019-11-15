package com.Dryft.models;

import java.time.LocalDateTime;

public class Ride {

    private final Location source;
    private final Location destination;
    private final Driver driver;
    private final User user;
    private final LocalDateTime startTime;
    private final int distance;
    private final int duration;
    private final int cost;
    private final int driverTime;

    public Ride(Location source, Location destination, Driver driver, User user) {
        int distance = calculateDistance(source, destination);
        int duration = calculateDuration(distance, driver);
        int cost = calculateCost(distance, driver);
        int driverTime = calculateDriverTime(source, driver);
        this.source = source;
        this.destination = destination;
        this.driver = driver;
        this.user = user;
        this.startTime = LocalDateTime.now();
        this.distance = distance;
        this.duration = duration;
        this.driverTime = driverTime;
        this.cost = cost;
    }

    public Ride(Location source, Location destination, Driver driver, User user, LocalDateTime startTime, int distance,
            int duration, int driverTime, int cost) {
        this.source = source;
        this.destination = destination;
        this.driver = driver;
        this.user = user;
        this.startTime = startTime;
        this.distance = distance;
        this.duration = duration;
        this.driverTime = driverTime;
        this.cost = cost;
    }

    // Calculates the Manhattan Distance
    public static int calculateDistance(Location source, Location destination) {
        return Math.abs(destination.getX() - source.getX()) + Math.abs(destination.getY() - source.getY());
    }

    public static int calculateDuration(int distance, Driver driver) {
        return (distance * 100) / driver.getCar().getSpeed();
    }

    public static int calculateDriverTime(Location source, Driver driver) {
        int distance = calculateDistance(driver.getLocation(), source);
        return distance / driver.getCar().getSpeed();
    }

    public static int calculateCost(int distance, Driver driver) {
        return distance * driver.getCar().getCostPerKm();
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public Driver getDriver() {
        return driver;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public int getDriverTime() {
        return driverTime;
    }

    public int getCost() {
        return cost;
    }
}
