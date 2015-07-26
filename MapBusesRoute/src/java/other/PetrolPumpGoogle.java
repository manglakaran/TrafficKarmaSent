/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Dibyendu1363
 */
public class PetrolPumpGoogle {

    public  ArrayList<EdgeInfo> getList() throws MalformedURLException, IOException, ParseException {

        String key = "AIzaSyBRh3X-kGRZknSQOySFVitucwpiYSgiu3o";

        // url from where response to be loaded
        String url_d = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=18.942675,72.824899&radius=6000&type=gas_station&key=" + key;

        // requesting to URL
        URL url = new URL(url_d);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to a JSON object and then whole string using GSON API
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //may be an array, may be an object. 
        String str = rootobj.toString();

        JSONObject json = (JSONObject) new JSONParser().parse(str);
        JSONArray get = (JSONArray) json.get("results");

        ArrayList<EdgeInfo> arr = new ArrayList<EdgeInfo>();
        
        for (int i = 0; i < get.size(); i++) {

            String str1 = get.get(i).toString();
            JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
            String id = json1.get("id").toString();
            String place_id = json1.get("place_id").toString();

            String geometry = json1.get("geometry").toString();
            JSONObject json2 = (JSONObject) new JSONParser().parse(geometry);
            String lat_long = json2.get("location").toString();
            JSONObject json3 = (JSONObject) new JSONParser().parse(lat_long);
            double lng = Double.parseDouble(json3.get("lng").toString());
            double lat = Double.parseDouble(json3.get("lat").toString());
            
            int index = i+1;
            
            EdgeInfo ei = new EdgeInfo(index, place_id, id, lat, lng);
            arr.add(ei);

        }
        
        return arr;


    }
}
