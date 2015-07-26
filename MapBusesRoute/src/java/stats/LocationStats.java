package stats;

import com.CitySelect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class LocationStats {
    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/traffickarma";
    private static final String DBUser = "root";
    private static final String DBPasswd = "admin";

    static {
        try {
            Class.forName(DBDriver);
        }catch (Exception ex) {
            System.out.println("error in connecton");
        }
    }

    private static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPasswd);
        return con;
    }
    public  ArrayList<String> getCongested()
    {
        ArrayList<String> al=new ArrayList<String>();
        TreeMap<String,Integer> freqMap=new TreeMap<String,Integer>();
        try {
            Connection con = getConnection();
            PreparedStatement sta = con.prepareStatement("Select * from trafficdata where city=?");
            sta.setString(1,CitySelect.city);
            ResultSet rs = sta.executeQuery();
            while(rs.next())
            {
                String address=rs.getString(2).toLowerCase().trim();
                String text=rs.getString(5).toLowerCase();
                //System.out.println(text);
                if(!address.startsWith(CitySelect.cityFull.toLowerCase()) && !text.startsWith("rt"))
                {
                    if(freqMap.containsKey(address))
                    {
                        freqMap.put(address,freqMap.get(address)+1);
                    }
                    else
                        freqMap.put(address,1);
                }
            }
             Set<Entry<String, Integer>> resultSet = freqMap.entrySet();
             List<Entry<String, Integer>> resultList = new ArrayList<Entry<String, Integer>>(resultSet);
             Collections.sort(resultList, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
             int count=0;
            for (Map.Entry<String, Integer> entryFinal : resultList) {
                if(count<10)
                {
                    //System.out.println(entryFinal.getKey());
                    al.add(entryFinal.getKey());
                    count++;
                }
                else
                    break;
                //System.out.println(entryFinal.getKey() + " \t score:  " + entryFinal.getValue());

            }
            //System.out.println(freqMap);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return al;
    }
    
}
