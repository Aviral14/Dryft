package com.Dryft.models;

import com.Dryft.DAOs.UserDAO;
import com.Dryft.DAOs.DriverDAO;
import com.Dryft.DAOs.RideDAO;
import com.Dryft.exceptions.UserSideException;

import java.sql.SQLException;
import java.lang.Math;

public class User {

    private final String fullname;
    private final String email;
    private final char sex;
    private String password;
    private int balance;

    public User(String fullname, String email, String password, char sex, int balance) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.balance = balance;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public char getSex() {
        return sex;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void incrementBalance(int increment) throws SQLException {
        if (increment < 0) {
            throw new IllegalArgumentException("Increment Less than 0");
        }
        try {
            balance = Math.addExact(balance, increment);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Invalid Increment Value");
        }
        UserDAO.incrementBalance(email, balance);
    }

    public Ride bookRide(Driver driver, Location source, Location destination) throws SQLException {
        DriverDAO.checkDriverAvailibility(driver);
        Ride ride = new Ride(source, destination, driver, this);
        if (balance < ride.getCost()) {
            throw new UserSideException(UserSideException.ErrorCode.BalanceNotEnough);
        }
        RideDAO.startRide(ride);
        return ride;
    }

    public void endRide(Ride ride) throws SQLException {
        DriverDAO.freeDriver(ride.getDriver().getId());
        balance -= ride.getCost();
        UserDAO.incrementBalance(this.email, balance);
    }
}
