package com.Dryft;

import com.Dryft.utils.DBConn;
import com.Dryft.gui.SignIn;

import java.lang.Thread;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

class DistributionThread extends Thread {

    public void run() {
        try {
            Thread.sleep(300);
            System.out.println("Starting Driver Redistribution");
            Connection conn = DBConn.getConn();
            PreparedStatement st = conn.prepareStatement("UPDATE drivers set location = homeLocation Where onRoad = (?) ;");
            st.setBoolean(1, false);
            st.executeUpdate();
            DBConn.closeConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Launch the application.
 */

public class MainThread {
    public static boolean windowClosed;
    public static void main(String[] args) {
        boolean fileCreated=false;
        String[] a={};
        SignIn.main(a);
        try {
            while(true){
                File file = new File("/tmp/running.txt");
                if (file.createNewFile()) {
                    fileCreated=true;
                    DistributionThread distribute = new DistributionThread();
                    distribute.setDaemon(true);
                    distribute.start();
                }
                if(windowClosed){
                    break;
                } 
            }
            if(fileCreated){
                File file = new File("/tmp/running.txt");
                file.delete(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
