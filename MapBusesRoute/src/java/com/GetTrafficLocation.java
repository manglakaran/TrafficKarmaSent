package com;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

    public class GetTrafficLocation {
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

    public ArrayList<TrafficInfo> getLoc(int dayCount) {
        int startZone;
        int endZone;
        ArrayList<TrafficInfo> atl = new ArrayList<TrafficInfo>();
        try {
            if(dayCount==0)
            {
                startZone=0; endZone=7;             
            }
            if(dayCount==1)
            {
                startZone=8; endZone=9;             
            }
            TimeData td=new TimeData();
            HashMap<String,String> timeData=td.getTime();
            Date d = new Date();
            Long dL = d.getTime();
            Connection con = getConnection();
            PreparedStatement sta = con.prepareStatement("Select * from traffictweets where location=?");
            sta.setString(1,CitySelect.city.toLowerCase());
            ResultSet rs = sta.executeQuery();
            if(dayCount==0){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("00")||compareDate.startsWith("01")||compareDate.startsWith("02")||compareDate.startsWith("03")||compareDate.startsWith("04")||compareDate.startsWith("05")||compareDate.startsWith("06")||compareDate.startsWith("07")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==1){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("08")||compareDate.startsWith("09")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==2){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("10")||compareDate.startsWith("11")||compareDate.startsWith("12")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==3){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("13")||compareDate.startsWith("14")||compareDate.startsWith("15")||compareDate.startsWith("16")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==4){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("17")||compareDate.startsWith("18")||compareDate.startsWith("19")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==5){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("20")||compareDate.startsWith("21")||compareDate.startsWith("22")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            if(dayCount==6){
            while (rs.next()) {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(rs.getString(7));
                String dateText=rs.getString(7);
                String []dateTextTokens=dateText.split(" ");
                String compareDate=dateTextTokens[3];
                
                Long dateL = date.getTime();
                String name = rs.getString(15);
                name = name.replaceAll("'", " ");
                if (compareDate.startsWith("23")||compareDate.startsWith("24")) {
                    String timeInfo="NA";
                    //System.out.println(compareDate);
//                    if(timeData.containsKey(rs.getString(1)+"#"+rs.getString(5)))
//                        timeInfo=timeData.get(rs.getString(1)+"#"+rs.getString(5));
                    double lat = Double.parseDouble(rs.getString(13));
                    double lng = Double.parseDouble(rs.getString(14));
                    String sentimentScore=0.0+"";
                    String state=rs.getString(11);
                    //System.out.println(timeInfo);
                    TrafficInfo t;
                      
                     t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "NA",1+"","NA",timeInfo);
                     atl.add(t);
                    
                   
                }
            }
        }
            //For file system.
//            FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\twitterData\\" + CitySelect.city + "\\traffic_data_" + CitySelect.city + ".txt");
//            Date d = new Date();
//            Long dL = d.getTime();
//            System.out.println(dayCount);
//            BufferedReader br = new BufferedReader(fr);
//            String line = br.readLine();
//            StringBuffer text = new StringBuffer();
//            while (line != null) {
//                text.append(line);
//                line = br.readLine();
//            }
//            String textData = text.toString();
//            textData = textData.replaceAll("\\n", " ");
//            String fullTokens[] = textData.split("\\$\\$\\$");
//            for (int i = 0; i < fullTokens.length; i++) {
//                StringTokenizer st = new StringTokenizer(fullTokens[i], "###");
//                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(st.nextToken());
//                Long dateL = date.getTime();
//                //System.out.println(dateL);
//                String name = st.nextToken();
//                name = name.replaceAll("'", " ");
//                String citySel = CitySelect.city.toLowerCase();
//                if (!(name.equals(CitySelect.cityFull)) && dL - ((dayCount + 1) * 86400 * 1000) <= dateL && dateL <= dL - ((dayCount) * 86400 * 1000)) {
//                    //System.out.println(dateL);
//                    double lat = Double.parseDouble(st.nextToken());
//                    double lng = Double.parseDouble(st.nextToken());
//                    String tweetText = st.nextToken();
//                    TrafficInfo t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "Twitter");
//                    atl.add(t);
//                }
//                line = br.readLine();
//            }//
//           FileReader frf = new FileReader("C:\\Users\\Server\\Documents\\facebookData\\" + CitySelect.city + "\\TrafficData.txt");
//            Date df = new Date();
//            Long dLf = df.getTime();
//            //System.out.println("lalsa");
//            BufferedReader brf = new BufferedReader(frf);
//            String linef = brf.readLine();
//            //System.out.println(linef);
//            StringBuffer textf = new StringBuffer();
//            while (linef != null) {
//                textf.append(linef);
//                linef = brf.readLine();
//            }
//            String textDataf = textf.toString();
//            textDataf = textDataf.replaceAll("\\n", " ");
//            String fullTokensf[] = textDataf.split("\\$\\$\\$");
//            System.out.println(fullTokensf.length);
//            for (int i = 0; i < fullTokensf.length; i++) {
//                StringTokenizer st = new StringTokenizer(fullTokensf[i], "###");
//                Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(st.nextToken());
//                //System.out.println(date);
//                Long dateL = date.getTime();
//                //System.out.println(dateL);
//                String name = st.nextToken();
//                //System.out.println(name +"   "+date);
//                name = name.replaceAll("'", " ");
//                String citySel = CitySelect.city.toLowerCase();
//                if (!(name.equals(CitySelect.cityFull)) && dLf - ((dayCount + 1) * 86400 * 1000) <= dateL && dateL <= dLf - ((dayCount) * 86400 * 1000)) {
//                    //System.out.println(dateL);
//                    double lat = Double.parseDouble(st.nextToken());
//                    double lng = Double.parseDouble(st.nextToken());
//                    //System.out.println(name);
//                    TrafficInfo t = new TrafficInfo(name, lat, lng, date.toLocaleString().toString(), "Facebook","2.0","unknown");
//                    atl.add(t);
//                }
//               
//            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return atl;
    }

}
