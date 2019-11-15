package com.Dryft;

import com.Dryft.DAOs.CarDAO;
import com.Dryft.DAOs.LocationDAO;
import com.Dryft.gui.SignIn;
import com.Dryft.models.Car;
import com.Dryft.models.Location;
import com.Dryft.models.Ride;
import com.Dryft.utils.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class DriverThread extends Thread {

    private int id;
    private Location origin;
    private Location homeLocation;
    private Car car;

    DriverThread(int id, Location origin, Location homeLocation, Car car) {
        this.id = id;
        this.origin = origin;
        this.homeLocation = homeLocation;
        this.car = car;
    }

    public void run() {
        while (true) {
            try {
                Connection conn = DBConn.getConn();
                PreparedStatement st = conn.prepareStatement("Update drivers set onRoad = (?) where id = (?) ;");
                st.setBoolean(1, true);
                st.setInt(2, id);
                st.executeUpdate();
                int time = Ride.calculateDistance(origin, homeLocation) / car.getSpeed();
                Thread.sleep(time);
                PreparedStatement stm = conn
                        .prepareStatement("Update drivers set onRoad = (?) and location = homeLocation where id = (?) ;");
                stm.setBoolean(1, false);
                stm.setInt(2, id);
                stm.executeUpdate();
                DBConn.closeConn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DistributionThread extends Thread {

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Starting Driver Redistribution");
                Connection conn = DBConn.getConn();
                PreparedStatement st = conn.prepareStatement("Select id, location, homeLocation, carNumber from drivers Where onRoad = (?) ;");
                st.setBoolean(1, false);
                ResultSet result = st.executeQuery();
                while (result.next()) {
                    DriverThread th = new DriverThread(result.getInt("id"),
                            LocationDAO.getLocation(result.getString("location")),
                            LocationDAO.getLocation(result.getString("homeLocation")),
                            CarDAO.getCar(result.getString("carNumber")));
                    th.start();
                }
                DBConn.closeConn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Launch the application.
 */

public class Main {

    public static void main(String[] args) {
        DistributionThread distribute = new DistributionThread();
        distribute.start();
        SignIn.main(new String[]{});
    }
}