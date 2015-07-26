/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficdata;

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
 * @author Dibyendu1363
 */
public class DataGetterGoogle {

    public String getDataGoogle(String coord) throws MalformedURLException, IOException, ParseException, InterruptedException {

        String key_kir = "AIzaSyBRh3X-kGRZknSQOySFVitucwpiYSgiu3o";
        String key = "AIzaSyBIH2gDxgWNFhQ8Dq4M50VOHNqzfiPSSGg";

        String url_d = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coord + "&type=transit_station&radius=500&key=" + key_kir;
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

        String str1 = get.get(0).toString();
        JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
        String geometry = json1.get("geometry").toString();
        JSONObject json2 = (JSONObject) new JSONParser().parse(geometry);
        String lat_long = json2.get("location").toString();
        JSONObject json3 = (JSONObject) new JSONParser().parse(lat_long);
        double lat = Double.parseDouble(json3.get("lat").toString());
        double lng = Double.parseDouble(json3.get("lng").toString());
        String destination = lat + "," + lng;

        double speed = 0;
        String state = "";

        String url_d1 = "https://maps.googleapis.com/maps/api/directions/json?origin=" + destination + "&destination=" + coord + "&output=json&key=" + key;
        URL url1 = new URL(url_d1);
        HttpURLConnection request1 = (HttpURLConnection) url1.openConnection();
        request1.connect();
        JsonParser jpp = new JsonParser(); //from gson
        JsonElement rootp = jpp.parse(new InputStreamReader((InputStream) request1.getContent())); //convert the input stream to a json element
        JsonObject rootobjp = rootp.getAsJsonObject(); //may be an array, may be an object. 
        String strp = rootobjp.toString();
        JSONObject jsonp = (JSONObject) new JSONParser().parse(strp);
        JSONArray getp = (JSONArray) jsonp.get("routes");
        String str1p = getp.get(0).toString();
        JSONObject json1p = (JSONObject) new JSONParser().parse(str1p);
        JSONArray get1p = (JSONArray) json1p.get("legs");
        String str2p = get1p.get(0).toString();
        JSONObject json2p = (JSONObject) new JSONParser().parse(str2p);

        String str3 = json2p.get("distance").toString();

        JSONObject json3p = (JSONObject) new JSONParser().parse(str3);
        String str4 = json3p.get("value").toString();

        Double dist1p = Double.parseDouble(str4);
        DecimalFormat dfu = new DecimalFormat("#.###");
        Double dist = Double.parseDouble(dfu.format(dist1p / 1000));

        JSONObject json4 = (JSONObject) new JSONParser().parse(str2p);
        String str5 = json4.get("duration").toString();

        JSONObject json5 = (JSONObject) new JSONParser().parse(str5);
        int duration = Integer.parseInt(json5.get("value").toString());

        speed = dist / duration * 3600;
        speed = Math.round(speed * 100);
        speed = speed / 100;

        if (speed >= 0 && speed <= 25) {
            state = "Heavy";
        } else if (speed > 25 && speed <= 50) {
            state = "Medium";
        } else if (speed > 50) {
            state = "Mild";
        }


        if (json2p.containsKey("steps")) {
            JSONArray steps = (JSONArray) json2p.get("steps");
            String det1 = steps.get(steps.size() - 1) + "";
            JSONObject js1 = (JSONObject) new JSONParser().parse(det1);

            String str31 = js1.get("distance").toString();
            JSONObject jss = (JSONObject) new JSONParser().parse(str31);
            String str41 = jss.get("value").toString();
            Double dist1 = Double.parseDouble(str41);
            DecimalFormat dfu1 = new DecimalFormat("#.###");
            Double dist3 = Double.parseDouble(dfu1.format(dist1 / 1000));

            String str53 = js1.get("duration").toString();
            JSONObject json53 = (JSONObject) new JSONParser().parse(str53);
            int duration3 = Integer.parseInt(json53.get("value").toString());

            speed = dist3 / duration3 * 3600;
            speed = Math.round(speed * 100);
            speed = speed / 100;

            state = "Null";
            if (speed >= 0 && speed <= 25) {
                state = "Heavy";
            } else if (speed > 25 && speed <= 50) {
                state = "Medium";
            } else if (speed > 50) {
                state = "Mild";
            }

        }

        Thread.sleep(600);

        return speed + "," + state;

    }
}