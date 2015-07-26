package trafficdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class GetChennaiFbPost {

    public static void main(String[] args) throws IOException {
        try {
            String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
            AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            while (true) {
                Date current = new Date();
                long currentL = current.getTime();
                currentL = currentL / 1000;//in seconds 10 digit
                long since = currentL - 1800;
                //System.out.println(currentL / 1000);
                String url_d = "https://graph.facebook.com/141144945912047/feed?since=" + since + "&until=" + currentL + "&access_token=CAAMFiSDA43ABAEr1dedHZCZBRvYWLKH7SffZB6vCZAffpPUsYdRz3HQ7kie0BQrxOEbin8Hx7sfAoyPZBGMdhKky0LtleOiVFTAQOAE3uS5V1JoJLYm8glIvLtsreF7uZCgZBT1k53CGDLuShmrh5KQLif6ZAlcsdOhvSym14NZBuz5FatHX5XonwdUZB5nzQc5ckZD";
                URL url = new URL(url_d);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();
                InputStreamReader ip = new InputStreamReader((InputStream) request.getContent());
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JSONObject json = (JSONObject) new JSONParser().parse(root.toString());
                JSONArray arr1 = (JSONArray) json.get("data");
                for (int i = 0; i < arr1.size(); i++) {
                    String time = "nana";
                    String message = "nana";
                    String id = "nana";
                    String from = "nana";
                    String fromId = "nana";
                    JSONObject json1 = (JSONObject) arr1.get(i);
                    time = json1.get("created_time").toString();
                    if (json1.get("message") != null) {
                        message = json1.get("message").toString();
                        message = message.replaceAll("#", " ");
                        message = message.replaceAll("!", " ");
                        message = message.replaceAll("$", " ");
                        message = message.replaceAll("\\s+", " ");
                        message = message.replaceAll("\\n+", " ");
                    }
                    id = json1.get("id").toString();
                    JSONObject json2 = (JSONObject) json1.get("from");
                    from = json2.get("name").toString();
                    fromId = json2.get("id").toString().trim();
                    System.out.println(fromId);
                    if (!fromId.equals("141144945912047")) {
                        FileWriter complaintsWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Chennai\\Complaints.txt", true);
                        complaintsWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                        complaintsWrite.close();
                    }
                    if (fromId.equals("141144945912047")) {
                        boolean flag=true;
                        String XmlData = classifier.classifyWithInlineXML(message);
                        StringTokenizer st = new StringTokenizer(XmlData, "<");
                        while (st.hasMoreTokens()) {
                            String temp=message.toLowerCase().trim();
                            String ele = st.nextToken();
                            if ((ele.startsWith("LO") || ele.startsWith("PER")||ele.startsWith("ORG"))&&temp.startsWith("avoid")) {
                                FileWriter dataWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Chennai\\JustData.txt", true);
                                FileWriter trafficWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Chennai\\TrafficData.txt", true);
                                ele = ele.replace("LOCATION>", "");
                                ele = ele.replace("/LOCATION>", "");
                                ele = ele.replace("PERSON>", "");
                                ele = ele.replace("/PERSON>", "");
                                ele = ele.replace("/ORGANIZATION>", "");
                                ele = ele.replace("ORGANIZATION>", "");
                                System.out.println(ele.trim());
                                String address = ele.trim() + ",chennai";
                                String url_geo = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address) + "&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
                                URI uri = new URI(url_geo);
                                URL page = new URL(uri.toString());
                                StringBuffer text = new StringBuffer();
                                HttpURLConnection conn = (HttpURLConnection) page.openConnection();
                                conn.connect();
                                InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                                BufferedReader buff = new BufferedReader(in);
                                String st1 = buff.readLine();
                                while (st1 != null) {
                                    text.append(st1);
                                    st1 = buff.readLine();
                                }
                                String data = text.toString();
                                Object obj = JSONValue.parse(data);
                                JSONObject jso = (JSONObject) obj;
                                JSONArray arr1_geo = (JSONArray) jso.get("results");
                                String data1 = arr1_geo.get(0).toString();
                                Object obj1 = JSONValue.parse(data1);
                                JSONObject jso1 = (JSONObject) obj1;
                                String address_updated = jso1.get("formatted_address").toString();
                                String data2 = jso1.get("geometry").toString();
                                Object obj2 = JSONValue.parse(data2);
                                JSONObject jso2 = (JSONObject) obj2;
                                String data3 = jso2.get("location").toString();
                                Object obj3 = JSONValue.parse(data3);
                                JSONObject jso3 = (JSONObject) obj3;
                                String lat = jso3.get("lat").toString();
                                String lng = jso3.get("lng").toString();
                                System.out.println(lat);
                                trafficWrite.write(time + "###" + address_updated + "###" + lat + "###" + lng + "$$$");
                                if(flag)
                                dataWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                                trafficWrite.close();
                                dataWrite.close();
                                flag=false;
                            } else {
                                if(flag)
                                {
                                    FileWriter dataWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Chennai\\JustData.txt", true);
                                    dataWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                                    dataWrite.close();
                                }
                                
                                flag=false;
                            }
                        }
                    }
                }
                Thread.sleep(1800 * 1000);//in miliseconds
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
