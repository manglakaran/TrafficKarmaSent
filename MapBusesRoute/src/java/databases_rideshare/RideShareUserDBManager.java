/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases_rideshare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RideShareUserDBManager {
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
    
    public String saveUserDetail(String username,String sex,String age,String genderpreference, String password, String email, String conu)
    {
        String userid="";
        int i=0;
        try
        {
            Connection con=getConnection();
            PreparedStatement autoVal = con.prepareStatement("SELECT AUTO_INCREMENT from information_schema.TABLES WHERE TABLE_SCHEMA = 'rideshare' AND TABLE_NAME = 'userdata'");
            ResultSet rs=autoVal.executeQuery();
            rs.next();
            userid=username+""+rs.getInt(1);
            PreparedStatement displaySta = con.prepareStatement("insert into userdata(userid,username,sex,age,genderpreference,password,email,contactn)values(?,?,?,?,?,?,?,?)");
            displaySta.setString(1, userid );
            displaySta.setString(2, username );
            displaySta.setString(3, sex );
            displaySta.setString(4, age );
            displaySta.setString(5, genderpreference );
            displaySta.setString(6, password );
            displaySta.setString(7, email );
            displaySta.setString(8, conu );
            i=displaySta.executeUpdate();
            displaySta.close();
            con.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        
        return userid;
    }
    
}
