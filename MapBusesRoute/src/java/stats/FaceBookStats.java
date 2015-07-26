
package stats;

import com.CitySelect;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class FaceBookStats {
    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/traffickarma";
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
    public ArrayList<ArrayList<String>> getSocialStats()
    {
        ArrayList<String> total=new ArrayList<String>();
        ArrayList<String> timeData=new ArrayList<String>();
        ArrayList<ArrayList<String>> al=new ArrayList<ArrayList<String>>();
        try
        {
             int delhiOld=4446+7240;
             int bangaloreOld=1729+3706;
             int mumbaiOld=8699+13883;
             int totalTweet=0;
             int totalFacebook=0;
             if(!CitySelect.city.equals("Mumbai"))
             {
                 BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\facebookData\\"+CitySelect.city+"\\JustData.txt"));
             String line=br.readLine();
             StringBuffer sb1=new StringBuffer();
             while(line!=null)
             {
                 sb1.append(line);
                 line=br.readLine();
             }
             String justData=sb1.toString();
             String[] fullToken=justData.split("\\$\\$\\$");
             int justCount=fullToken.length;
             BufferedReader br1=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\facebookData\\"+CitySelect.city+"\\Complaints.txt"));
             String line1=br1.readLine();
             StringBuffer sb2=new StringBuffer();
             while(line1!=null)
             {
                 sb2.append(line1);
                 line1=br1.readLine();
             }
             String complaintData=sb2.toString();
             String[] fullToken1=complaintData.split("\\$\\$\\$");
             int complaintCount=fullToken1.length;
             totalFacebook=justCount+complaintCount;
             }
             
             //System.out.println(justCount+"  "+complaintCount);
             
             Connection con = getConnection();
             PreparedStatement sta = con.prepareStatement("Select count(*) from trafficdata where city=? and source=?");
             sta.setString(1,CitySelect.city.toLowerCase());
             sta.setString(2,"twitter");
             PreparedStatement sta1 = con.prepareStatement("Select count(*) from trafficdatadisplay where city=? and source=?");
             sta1.setString(1,CitySelect.city.toLowerCase());
             sta1.setString(2,"twitter");
             ResultSet rs = sta.executeQuery();
             rs.next();
             int count1=rs.getInt(1);
             rs = sta1.executeQuery();
             rs.next();
             int count2=rs.getInt(1);
             //System.out.println(count1+count2);
             if(CitySelect.city.equals("Delhi"))
                 totalTweet=count1+count2+delhiOld;
             if(CitySelect.city.equals("Mumbai"))
                 totalTweet=count1+count2+mumbaiOld;
             if(CitySelect.city.equals("Bangalore"))
                 totalTweet=count1+count2+bangaloreOld;
             //System.out.println(totalTweet);
             //System.out.println(justCount+complaintCount);
             int[] timeCount=new int[24];
             PreparedStatement sta2 = con.prepareStatement("Select time from trafficdata where city=? and source=?");
             sta2.setString(1,CitySelect.city.toLowerCase());
             sta2.setString(2,"twitter");
             rs = sta2.executeQuery();
             rs.next();
             while(rs.next())
             {
                 String date=rs.getString(1);
                 String dateTokens[]=date.split(" ");
                 String timeTokens[]=dateTokens[3].split(":");
                 int slot=Integer.parseInt(timeTokens[0]); 
                 timeCount[slot]=timeCount[slot]+1;
             }
             PreparedStatement sta3 = con.prepareStatement("Select time from trafficdatadisplay where city=? and source=?");
             sta3.setString(1,CitySelect.city.toLowerCase());
             sta3.setString(2,"twitter");
             rs = sta3.executeQuery();
             rs.next();
             while(rs.next())
             {
                 String date=rs.getString(1);
                 String dateTokens[]=date.split(" ");
                 String timeTokens[]=dateTokens[3].split(":");
                 int slot=Integer.parseInt(timeTokens[0]); 
                 timeCount[slot]=timeCount[slot]+1;
             }
             for(int i=0;i<timeCount.length;i++)
                 timeData.add(timeCount[i]+"");
                // System.out.println(i+1+"  "+timeCount[i]);
             LocationStats loc=new LocationStats();
             total.add(totalTweet+"");
             total.add(totalFacebook+"");
             
             ArrayList<String> topCongested=loc.getCongested();
             //System.out.println(topCongested);
             al.add(total);
             al.add(timeData);
             al.add(topCongested);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return al;
    }
}
