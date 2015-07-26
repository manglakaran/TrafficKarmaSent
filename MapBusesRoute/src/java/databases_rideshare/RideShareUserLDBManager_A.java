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
import java.sql.Statement;

public class RideShareUserLDBManager_A {
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
    
    public String checkUserDetail(String username,String password)
    {
        int i=0;
        try
        {
            Connection con=getConnection();
            String ps = "SELECT * from userdata_a where username='"+username+"' and password='"+password+"'";
            System.out.println("Here1");
            Statement p=con.createStatement();
            ResultSet rs=p.executeQuery(ps);
            
            if (!rs.next())
            {
                System.out.println("Here2");
                p.close();
                con.close();
                return "Wrong";
            }
            else
            {
                //rs.next();
                
                System.out.println("Here:\n");
                System.out.println(rs.getString("userid"));
                String x=rs.getString("userid");
                p.close();
                con.close();
                return x;
            }
            
                
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return "Error in connection! AA";
        }
        
        
        //return userid;
    }
    
}
