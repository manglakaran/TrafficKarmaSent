
package databases_rideshare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author rideshare@iiit-Delhi
 */
public class RideReturnInfo {
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
    public ArrayList<String> getUserInfo(int rideId)
    {
        ArrayList<String> returnList=new ArrayList<String>();
        try
        {
            Connection con=getConnection();
            PreparedStatement st = con.prepareStatement("select * from ridedata where rideid=? ");
            st.setInt(1,rideId);
            ResultSet rs=st.executeQuery();
            rs.next();
            String userId=rs.getString(5);
            String source=rs.getString(3);
            String destination=rs.getString(4);
            String sll=rs.getString("sourcell");
            String dll=rs.getString("destinationll");
            st = con.prepareStatement("select * from userdata where userid=?");
            st.setString(1,userId);
            rs=st.executeQuery();
            rs.next();
            String sex=rs.getString(4);
            String age=rs.getString(5);
            String username=rs.getString(2);
            String cn=rs.getString("contactn");
            
            returnList.add(sex); returnList.add(username); returnList.add(source); returnList.add(destination);
            returnList.add(age);returnList.add(sll);returnList.add(dll);returnList.add(cn);
//            System.out.println(sex);
//            System.out.println(userId);
//            System.out.println(source);
//            System.out.println(destination);
//            System.out.println(age);
            st.close();
            con.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return returnList;
    }
    public int getRideId()
    {
        int rideId=0;
        try
        {
            Connection con=getConnection();
            PreparedStatement autoVal = con.prepareStatement("SELECT AUTO_INCREMENT from information_schema.TABLES WHERE TABLE_SCHEMA = 'rideshare' AND TABLE_NAME = 'ridedata'");
            ResultSet rs=autoVal.executeQuery();
            rs.next();
            rideId=rs.getInt(1);
        }
        catch(Exception ex){
            System.out.println("exception in getting userId"+ex.getMessage());
        }
        return rideId;
    }
    
}
