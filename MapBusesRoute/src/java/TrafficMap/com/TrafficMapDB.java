package TrafficMap.com;

import TrafficMap.beans.TrafficMapBean;
import com.CitySelect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author traffickarma@iiitd
 */
public class TrafficMapDB {

    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/traffickramasenti";
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

    public ArrayList<TrafficMapBean> getMapLocations() {
        ArrayList<TrafficMapBean> finalList = new ArrayList<TrafficMapBean>();
        try
        {
            Connection con=getConnection();
            PreparedStatement sta = con.prepareStatement("Select * from traffictweets");
            ResultSet rs = sta.executeQuery();
            int i=0;
            for(i=0;i<80;i++) {
                rs.next();
            }
            for(i=70;i<100;i++) {
                rs.next();
                DataGetterGoogle dgg=new DataGetterGoogle();
                
                String res=dgg.getDataGoogle(rs.getDouble(16)+","+rs.getDouble(17));
                String temp[]=res.split(",");
                double actuallat=Double.parseDouble(temp[0]);
                double actuallng=Double.parseDouble(temp[1]);
                double transitlat=rs.getDouble(13);
                double transitlng=rs.getDouble(14);
                String status=rs.getString(11);
                TrafficMapBean tmb=new TrafficMapBean(actuallat, actuallng, transitlat, transitlng, status);
                finalList.add(tmb);
                //System.out.println(actuallat+" "+actuallng);
            }
            
            
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return finalList;
    }

}
