
package databases_rideshare;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author rideshare@iiit-Delhi
 */
public class RidesUserReturnInfo {
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
    public ArrayList<ArrayList<String>> getRidesInfo(String UserId, String time)
    {
        ArrayList<ArrayList<String>> returnListf=new ArrayList<ArrayList<String>>();;
        try
        {
            Connection con=getConnection();
            BigInteger bi = new BigInteger(time);
            int x=15*60*1000;
            BigInteger sub=new BigInteger(x+"");
            bi=bi.subtract(sub);
            time=""+bi;
            time=""+0;
            System.out.println(""+UserId+" "+time);
            PreparedStatement st = con.prepareStatement("select rideid, timestamp, source, destination, sourcell, destinationll, pref from ridedata where userid=? and timestamp>=?");
            st.setString(1,UserId);
            st.setString(2, time);
            ResultSet rs=st.executeQuery();
            
            if (!rs.next())
            {
                st.close();
            con.close();
                return returnListf;
            }
            else
            {
                rs.beforeFirst();
                System.out.println("In Else!");
                while (rs.next())
                {
                    
                    ArrayList<String> r=new ArrayList();
                    for (int i=1; i<=7; i++)
                    {
                        r.add(rs.getString(i));
                        //r.add(rs.getString(i));
                    }
                    returnListf.add(r);
                    
                }
                st.close();
            con.close();
                return returnListf;
            }
            //rs.next();
              //System.out.println(sex);
//            System.out.println(userId);
//            System.out.println(source);
//            System.out.println(destination);
//            System.out.println(age);
            
        }
        catch(Exception ex)
        {
            
            System.out.println(ex.getMessage());
        }
        return returnListf;
    }
    
}
