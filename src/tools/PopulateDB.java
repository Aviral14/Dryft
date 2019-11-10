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
        String deleteExistingTable = "DROP TABLE IF EXISTS users;";
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
            stmt.execute(deleteExistingTable);
            stmt.execute(createUserTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeDriverTable(Connection conn) {
        String deleteExistingTable = "DROP TABLE IF EXISTS drivers;";
        String createDriverTable = "CREATE TABLE drivers (" +
                "id INT PRIMARY KEY," +
                "name TEXT," +
                "carNumber INT," +
                "sex CHAR(1)," +
                "location TEXT," +
                "rating REAL," +
                "reviews INT," +
                "FOREIGN KEY(carNumber) REFERENCES cars(licenseNumber)," + 
                "FOREIGN KEY(location) REFERENCES locations(name)," +             
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(deleteExistingTable);
            stmt.execute(createDriverTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeCarTable(Connection conn) {
        String deleteExistingTable = "DROP TABLE IF EXISTS cars;";
        String createCarTable = "CREATE TABLE cars (" +
                "licenseNumber TEXT PRIMARY KEY," +
                "model TEXT," +
                "costPerKm INT," +
                "CarType TEXT," +
                "speed INT" +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(deleteExistingTable);
            stmt.execute(createCarTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeLocationTable(Connection conn) {
        String deleteExistingTable = "DROP TABLE IF EXISTS locations;";
        String createLocationTable = "CREATE TABLE locations (" +
                "name TEXT PRIMARY KEY," +
                "x INT," +
                "y INT," +
                ");";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(deleteExistingTable);
            stmt.execute(createLocationTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeRideTable(Connection conn) {
        String deleteExistingTable = "DROP TABLE IF EXISTS rides;";
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
            stmt.execute(deleteExistingTable);
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
