package com.Dryft;

import com.Dryft.utils.DBConn;
import com.Dryft.gui.SignIn;
import com.Dryft.models.Car;
import com.Dryft.models.Ride;
import com.Dryft.models.Location;
import com.Dryft.DAOs.LocationDAO;
import com.Dryft.DAOs.CarDAO;

import java.lang.Thread;
import java.io.IOException;
import java.io.File;
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
        try{
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
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

class DistributionThread extends Thread {

    public void run() {
        try {
            Thread.sleep(300);
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

    public static void sendDriverBack() {

    }
}

/**
 * Launch the application.
 */

public class MainThread {
    public static boolean windowClosed;

    public static void main(String[] args) {
        boolean fileCreated = false;
        String[] a = {};
        SignIn.main(a);
        try {
            while (true) {
                File file = new File("/tmp/running.txt");
                if (file.createNewFile()) {
                    fileCreated = true;
                    DistributionThread distribute = new DistributionThread();
                    distribute.setDaemon(true);
                    distribute.start();
                }
                if (windowClosed) {
                    break;
                }
            }
            if (fileCreated) {
                File file = new File("/tmp/running.txt");
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
