package mtis;

import com.TrafficInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJson {

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
    ArrayList<TrafficInfo> atl = new ArrayList<TrafficInfo>();

    public String getJsonRes(String city, String duration) {
        String fullName = "";
        if (city.equals("delhi")) {
            fullName = "New Delhi, Delhi, India";
        }

        if (city.equals("mumbai")) {
            fullName = "Mumbai, Maharashtra, India";
        }

        if (city.equals("bangalore")) {
            fullName = "Bengaluru, Karnataka, India";
        }
        String finalRet = "";

        try {
            Connection con = getConnection();
            JSONArray list = new JSONArray();
            JSONObject finalObj = new JSONObject();
            ArrayList<String> month = new ArrayList<String>();
            month.add("Jan");
            month.add("Feb");
            month.add("Mar");
            month.add("Apr");
            month.add("May");
            month.add("Jun");
            month.add("Jul");
            month.add("Aug");
            month.add("Sep");
            month.add("Oct");
            month.add("Nov");
            month.add("Dec");
            Date d = new Date();
            int dayCount = Integer.parseInt(duration);
            Long dL = d.getTime();
            PreparedStatement ps = con.prepareStatement("select * from traffictweets where location=?");
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String dateString = rs.getString(7);
                String dateString1 = dateString;
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(dateString);
                Long dateL = date.getTime();
                //System.out.println(dateL);
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (!(name.equals(fullName)) && dL - ((dayCount + 1) * 86400 * 1000) <= dateL && dateL <= dL - ((dayCount) * 86400 * 1000)) {
                    JSONObject js = new JSONObject();
                    js.put("label", name);
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm a ");
                    //System.out.println(format.format(date));
                    //System.out.println(date.getDate()+"-"+month.get(date.getMonth())+" "+format.format(date));              
                    //System.out.println(dateL);
                    double lat = rs.getDouble(13);
                    js.put("lat", lat);
                    double lng = rs.getDouble(14);
                    js.put("lon", lng);
                    //String tweetText = st.nextToken();
                    js.put("status", "N/A");
                    js.put("time", date.getDate() + "-" + month.get(date.getMonth()) + " " + format.format(date));
                    list.add(js);
                }
            }
            finalObj.put("locations", list);

            //System.out.println(finalObj.toString());
            finalRet = finalObj.toString();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return finalRet;
    }
}
