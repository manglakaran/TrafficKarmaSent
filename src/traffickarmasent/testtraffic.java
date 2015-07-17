/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author TrafficKarma@iiitd
 */
public class testtraffic {

    public String getDataGoogle(String coord) {
        double speed = 0.0;
        String state = "";
        String destination = "";
        String destination1 = "";
        try {
            String key_kir = "AIzaSyBRh3X-kGRZknSQOySFVitucwpiYSgiu3o";
            String key_karan = "AIzaSyDuJ9cAenDLsuI3Lzwh698hxiD2r2wDpyw";
            String key = "AIzaSyBIH2gDxgWNFhQ8Dq4M50VOHNqzfiPSSGg";
            String key_kireet13="AIzaSyAQOjIILGSqTg2iq9kUaT1wbXxCItwRk-E";

            String url_d = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coord + "&type=transit_station&radius=500&key=" + key_karan;
            URL url = new URL(url_d);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //may be an array, may be an object. 
            String str = rootobj.toString();
            JSONObject json = (JSONObject) new JSONParser().parse(str);
            JSONArray get = (JSONArray) json.get("results");
            if (get.size() == 0) {
                return "";
            }

            Thread.sleep(600);
            
            outer:
            for (int i = 0; i < get.size(); i++) {
                String str1 = get.get(i).toString();
                JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
                JSONArray typeArr = (JSONArray) json1.get("types");
                for (int ty = 0; ty < typeArr.size(); ty++) {
                    if (typeArr.get(ty).equals("bus_station")) {
                        
                        String geometry = json1.get("geometry").toString();
                        JSONObject json2 = (JSONObject) new JSONParser().parse(geometry);
                        String lat_long = json2.get("location").toString();
                        JSONObject json3 = (JSONObject) new JSONParser().parse(lat_long);
                        double lat = Double.parseDouble(json3.get("lat").toString());
                        double lng = Double.parseDouble(json3.get("lng").toString());
                        //System.out.println(lat + " " + lng);
                        destination = lat + "," + lng;
                        String dest2 = get.get(i+1).toString();
                        JSONObject destobj = (JSONObject) new JSONParser().parse(dest2);
                        geometry = destobj.get("geometry").toString();
                        json2 = (JSONObject) new JSONParser().parse(geometry);
                        lat_long = json2.get("location").toString();
                        json3 = (JSONObject) new JSONParser().parse(lat_long);
                        lat = Double.parseDouble(json3.get("lat").toString());
                        lng = Double.parseDouble(json3.get("lng").toString());
                        destination1 = lat + "," + lng;
                        break outer;
                    }
                }
                //System.out.println("lala" + " " + destination);
            }
            if(destination1.equals(""))
            {
                destination1=coord;
            }
            speed = 0;
            state = "";

            String url_d1 = "http://dev.virtualearth.net/REST/V1/Routes?wp.0=" + destination + "&wp.1="+ destination1 +"&optmz=timeWithTraffic&key=Ak30nwhCl3obfhvS7zgEJ-xoQJbU_Lh8hP_tUNV5kJeamdr2_VmuG0KXiquggRkg";
            URL url1 = new URL(url_d1);
            HttpURLConnection request1 = (HttpURLConnection) url1.openConnection();
            request1.connect();
            JsonParser jpp = new JsonParser(); //from gson
            JsonElement rootp = jpp.parse(new InputStreamReader((InputStream) request1.getContent())); //convert the input stream to a json element
            JsonObject rootobjp = rootp.getAsJsonObject(); 
            String strp = rootobjp.toString();
            JSONObject jsonp = (JSONObject) new JSONParser().parse(strp);
            JSONArray getp = (JSONArray) jsonp.get("resourceSets");
            String str1p = getp.get(0).toString();
            JSONObject json1p = (JSONObject) new JSONParser().parse(str1p);
            JSONArray get1p=(JSONArray) json1p.get("resources");
            String str2p = get1p.get(0).toString();
            
            JSONObject json2p = (JSONObject) new JSONParser().parse(str2p);
           
            int duration= Integer.parseInt(json2p.get("travelDurationTraffic").toString());
            double distance= Double.parseDouble(json2p.get("travelDistance").toString()); 
            speed=(distance/duration)*3600;
            state=json2p.get("trafficCongestion").toString();
            Thread.sleep(600);
        } catch (Exception ex) {
            System.out.println("exception");
        }
        return speed + "," + state + "," + destination+","+destination1;

    }

}
