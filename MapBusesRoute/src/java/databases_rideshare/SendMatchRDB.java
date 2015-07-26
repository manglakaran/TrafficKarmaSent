/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases_rideshare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Server
 */
public class SendMatchRDB {
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
    
    public String setYes(String rideid1,String rideid2)
    {
        int i=0;
        try
        {
            Connection con=getConnection();
            String ps = "Insert into matchdata(RequestRid,AskedRid,Okay) values('"+rideid1+"','"+rideid2+"','No')";
            System.out.println("Here1");
            Statement p=con.createStatement();
            p.executeUpdate(ps);
            
            return "Done!";
                
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return "Error in connection! AA";
        }
        
        
        //return userid;
    }
    
    
}
