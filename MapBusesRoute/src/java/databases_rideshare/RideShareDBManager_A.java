/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases_rideshare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RideShareDBManager_A {
    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/rideshare";
    private static final String DBUser = "root";
    private static final String DBPasswd = "admin";

    static {
        try {
            Class.forName(DBDriver);
        } catch (Exception ex) {
            System.out.println("error in connecton");
        }
    }

    private static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPasswd);
        return con;
    }
    
    public int saveRideDetail(String gender,String name, String source,String destination,String timestamp,String userid,String type,String schedule,String sourcell,String destinationll,String genderpre)
    {
        //GetLocationCoordinates glc=new GetLocationCoordinates();
        //String sourceCoor=glc.getLocCoor(source);
        //String destCoor=glc.getLocCoor(destination);
        //System.out.println(sourceCoor+"  "+destCoor);
        int i=0;
        try
        {
            Connection con=getConnection();
            Double ts=Double.parseDouble(timestamp);
            PreparedStatement displaySta = con.prepareStatement("insert into ridedata_a(timestamp,source,destination,userid,type,schedule,sourcell,destinationll,pref,done,gender,name)values(?,?,?,?,?,?,?,?,?,?,?,?)");
            displaySta.setString(1, timestamp );
            displaySta.setString(2, source );
            displaySta.setString(3, destination );
            displaySta.setString(4, userid );
            displaySta.setString(5, type );
            displaySta.setString(6, schedule );
            displaySta.setString(7, sourcell );
            displaySta.setString(8, destinationll );
            displaySta.setString(9,genderpre);
            displaySta.setString(10,"No");
            displaySta.setString(11, gender);
            displaySta.setString(12, name);
            i=displaySta.executeUpdate();
            displaySta.close();
            con.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        
        return i;
    }
    
}
