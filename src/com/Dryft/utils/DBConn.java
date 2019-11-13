package com.Dryft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static Connection conn = null;
    private static int numConns = 0;

    private static void ensureDriverExists() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite Driver error.");
            System.exit(1);
        }
    }

    public static Connection getConn() {
        ensureDriverExists();
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:sqlite:dryft.sqlite3");
            }
        } catch (SQLException e) {
            System.err.println("Error in connecting to DB");
            System.exit(1);
        }
        numConns++;
        return conn;
    }

    public static void closeConn() {
        try {
            if (conn != null && !conn.isClosed() && numConns == 1) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing DB");
            System.exit(1);
        }
        numConns--;
    }
}
