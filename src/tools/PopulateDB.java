package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PopulateDB {
    private static void makeTables(Connection conn) {
        makeUserTable(conn);
        makeDriverTable(conn);
        makeCarTable(conn);
        makeLocationTable(conn);
        makeRideTable(conn);
    }

    private static void makeUserTable(Connection conn) {
        String createUserTable = "CREATE TABLE users (" +
                "email TEXT PRIMARY KEY," +
                "fullname TEXT," +
                "password TEXT," +
                "salt TEXT," +
                "sex CHAR(1)," +
                "balance INT" +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(createUserTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeDriverTable(Connection conn) {
        String createDriverTable = "CREATE TABLE drivers (" +
                "id INT PRIMARY KEY," +
                "name TEXT," +
                "carNumber INT," +
                "sex CHAR(1)," +
                "location TEXT," +
                "rating REAL," +
                "reviews TEXT," +
                "FOREIGN KEY(carNumber) REFERENCES cars(licenseNumber)," +                
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(createDriverTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeCarTable(Connection conn) {
        String createCarTable = "CREATE TABLE cars (" +
                "licenseNumber TEXT PRIMARY KEY," +
                "model TEXT," +
                "costPerKm TEXT," +
                "CarType TEXT," +
                "speed INT" +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(createCarTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeLocationTable(Connection conn) {
        String createLocationTable = "CREATE TABLE locations (" +
                "name TEXT PRIMARY KEY," +
                "x INT," +
                "y INT," +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(createLocationTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeRideTable(Connection conn) {
        String createRideTable = "CREATE TABLE rides (" +
                "id INT PRIMARY KEY," +
                "userEmail TEXT ," +
                "driver TEXT," +
                "source TEXT," +
                "destination TEXT," +
                "car TEXT," +
                "startTime TEXT," +
                "duration INT," +
                "distance INT," +
                "cost INT," +
                "FOREIGN KEY(userEmail) REFERENCES users(email)," +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(createRideTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn;
        {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:dryft.sqlite3");
                makeTables(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
