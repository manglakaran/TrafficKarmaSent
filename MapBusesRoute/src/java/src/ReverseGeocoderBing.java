/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Dibyendu1363
 */
public class ReverseGeocoderBing {

    public String reverse(String edge) throws FileNotFoundException, IOException, ParseException {

        // Key initializing
        String key = "AgcshwzkV7EoCAHx1RDRcnE2KzU_VS6TvkxzhuqpDuzF44B4h-XF9Hr40MRaKD5p";

        String first, end;

        if (edge.contains(":")) {
            String pp[] = edge.split(":");
            first = pp[0];
            end = pp[1];
        } else if (edge.contains("-")) {
            String pp[] = edge.split("-");
            first = pp[0];
            end = pp[1];
        } else if (edge.contains("_")) {
            String pp[] = edge.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = edge.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String first_coord = "";
        String end_coord = "";

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\points_delhi2.txt");

        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {

            String hoh[] = line.split("-");
            if (hoh[0].equals(first)) {
                first_coord = hoh[1];
            }
            if (hoh[0].equals(end)) {
                end_coord = hoh[1];
            }

        }

        String arr_add[] = new String[2];
        arr_add[0] = first_coord;
        arr_add[1] = end_coord;

        String arr_add_final[] = new String[2];
        
        for (int x = 0; x < arr_add.length; x++) {

            // url from where response to be loaded
            String url_d = "http://dev.virtualearth.net/REST/v1/Locations/" + arr_add[x] + "?o=json&key=" + key;

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
            JSONArray get = (JSONArray) json.get("resourceSets");
            String str1 = get.get(0).toString();

            JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
            JSONArray get1 = (JSONArray) json1.get("resources");
            String str2 = get1.get(0).toString();

            JSONObject json2 = (JSONObject) new JSONParser().parse(str2);
            String address = json2.get("name").toString();

            String part[] = address.split(",");
            String fnl_add = address;

            String lol = "";
            if (part.length >= 2) {
                for (int i = 0; i < part.length - 2; i++) {
                    if (lol.equals("")) {
                        lol = lol + part[i];
                    } else {
                        lol = lol + "," + part[i];
                    }
                }
                fnl_add = lol;
            }
            arr_add_final[x] = fnl_add;
        }
        
        String result = arr_add_final[0]+"$$$"+arr_add_final[1];
        
        return result;
    }
}
