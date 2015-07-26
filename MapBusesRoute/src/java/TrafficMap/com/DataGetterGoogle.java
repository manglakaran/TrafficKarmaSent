/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrafficMap.com;

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
        String ret="";
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

        //Thread.sleep(600);

        String str1 = get.get(1).toString();
        JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
        String geometry = json1.get("geometry").toString();
        JSONObject json2 = (JSONObject) new JSONParser().parse(geometry);
        String lat_long = json2.get("location").toString();
        JSONObject json3 = (JSONObject) new JSONParser().parse(lat_long);
        double lat = Double.parseDouble(json3.get("lat").toString());
        double lng = Double.parseDouble(json3.get("lng").toString());
        String destination = lat + "," + lng;
        ret=destination;
        

        return destination;

    }
}