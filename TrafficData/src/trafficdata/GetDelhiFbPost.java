package trafficdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

public class GetDelhiFbPost {
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
    public static void main(String[] args) throws IOException {
        try {
            FileReader fr2 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\TrafficData\\SentiWordNet_scores_final.txt");
            BufferedReader br2 = new BufferedReader(fr2);
            String str2;
            HashMap<String, String> hm = new HashMap<String, String>();
            while ((str2 = br2.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(str2, "^");
                hm.put(st.nextToken(), st.nextToken());
            }
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            Connection con = getConnection();
            String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
            AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            while (true) {
                Date current = new Date();
                long currentL = current.getTime();
                currentL = currentL / 1000;//in seconds 10 digit
                long since = currentL - 600;
                //System.out.println(currentL / 1000);
                String url_d = "https://graph.facebook.com/117817371573308/feed?since=" + since + "&until=" + currentL + "&access_token=CAAMFiSDA43ABAEr1dedHZCZBRvYWLKH7SffZB6vCZAffpPUsYdRz3HQ7kie0BQrxOEbin8Hx7sfAoyPZBGMdhKky0LtleOiVFTAQOAE3uS5V1JoJLYm8glIvLtsreF7uZCgZBT1k53CGDLuShmrh5KQLif6ZAlcsdOhvSym14NZBuz5FatHX5XonwdUZB5nzQc5ckZD";
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
                    String additionalInfo="nana"; 
                    double score=-2.0;
                    String state="NA";
                    JSONObject json1 = (JSONObject) arr1.get(i);
                    time = json1.get("created_time").toString();
                    if (json1.get("message") != null) {
                        message = json1.get("message").toString();
                        String cleanData = message;
                        String[] cleanDataTokens = cleanData.split(" ");
                        if (cleanDataTokens.length > 60) {
                            cleanData = "";
                            for (int cd = 0; cd < 60; cd++) {
                                cleanData = cleanData + " " + cleanDataTokens[cd];
                            }
                        }
                        cleanData = cleanData.replaceAll(":", " ");
                        cleanData = cleanData.replaceAll("\\.", " ");
                        cleanData = cleanData.replaceAll(",", " ");
                        cleanData = cleanData.replaceAll("-", " ");
                        cleanData = cleanData.replaceAll("'", " ");
                        cleanData = cleanData.replaceAll("@", " ");
                        cleanData = cleanData.replaceAll("#", " ");
                        cleanData = cleanData.replaceAll("\\\\", " ");
                        cleanData = cleanData.replaceAll("/", " ");
                        Iterable<List<? extends HasWord>> sentences;
                        Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(cleanData));
                        List<? extends HasWord> sentence2 = toke.tokenize();
                        List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                        tmp.add(sentence2);
                        sentences = tmp;

                        int count = 0;
                        double sum = 0;
                        for (List<? extends HasWord> sentence : sentences) {
                            Tree parse = lp.parse(sentence);
                            for (int index = 0; index < parse.taggedYield().size(); index++) {
                                if (parse.taggedYield().get(index).toString().contains("/")) {
                                    String par[] = parse.taggedYield().get(index).toString().split("/");
                                    Character myChar = par[1].toLowerCase().charAt(0);
                                    if (myChar == null) {
                                        continue;
                                    }

                                    if (myChar == 'j') {
                                        myChar = 'a';
                                    }
                                    String fnl = par[0] + "_" + myChar;
                                    if (hm.containsKey(fnl)) {
                                        sum = sum + Double.parseDouble(hm.get(fnl));
                                        count++;
                                    }
                                }
                            }
                        }
                        score = sum / count;
                        System.out.println(score);
                        state = "";
                        if (score <= 1.0 && score > 0.3) {
                            state = "fast";
                        } else if (score <= 0.3 && score >= 0.1) {
                            state = "moderate";
                        } else if (score < 0.1 && score > -0.3) {
                            state = "slow";
                        } else if (score <= -0.3 && score >= -1.0) {
                            state = "heavy";
                        }
                        else
                            state = "unknown";
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
                    if (!fromId.equals("117817371573308")) {
                        FileWriter complaintsWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Delhi\\Complaints.txt", true);
                        complaintsWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                        complaintsWrite.close();
                    }
                    if (fromId.equals("117817371573308")) {
                        boolean flag=true;
                        String XmlData = classifier.classifyWithInlineXML(message);
                        StringTokenizer st = new StringTokenizer(XmlData, "<");
                        while (st.hasMoreTokens()) {
                            String temp=message.toLowerCase().trim();
                            String ele = st.nextToken();
                            if ((ele.startsWith("LO") || ele.startsWith("PER")||ele.startsWith("ORG"))&&temp.startsWith("traffic")) {
                                PreparedStatement sta = con.prepareStatement("insert into trafficdata(time,address,lat,lng,text,sentiment,city,source,state) values(?,?,?,?,?,?,?,?,?)");
                                FileWriter dataWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Delhi\\JustData.txt", true);
                                FileWriter trafficWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Delhi\\TrafficData.txt", true);
                                ele = ele.replace("LOCATION>", "");
                                ele = ele.replace("/LOCATION>", "");
                                ele = ele.replace("PERSON>", "");
                                ele = ele.replace("/PERSON>", "");
                                ele = ele.replace("/ORGANIZATION>", "");
                                ele = ele.replace("ORGANIZATION>", "");
                                System.out.println(ele.trim());
                                String address = ele.trim() + ",new delhi, delhi";
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
                                sta.setString(1,new Date().toString());
                                sta.setString(2, address_updated);
                                sta.setString(3, lat);
                                sta.setString(4, lng);
                                sta.setString(5, message);
                                sta.setString(6, score + "");
                                sta.setString(7, "delhi");
                                sta.setString(8, "facebook");
                                sta.setString(9, state);
                                sta.executeUpdate();
                                sta.close();
                                trafficWrite.write(time + "###" + address_updated + "###" + lat + "###" + lng + "$$$");
                                if(flag)
                                dataWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                                trafficWrite.close();
                                dataWrite.close();
                                flag=false;
                            } else {
                                
                                if(flag)
                                {
                                    FileWriter dataWrite = new FileWriter("C:\\Users\\Server\\Documents\\facebookData\\Delhi\\JustData.txt", true);
                                    dataWrite.write(time + "###" + message + "###" + id + "###" + from + "###" + fromId + "$$$");
                                    dataWrite.close();
                                }
                                
                                flag=false;
                            }
                        }
                    }
                }
                Thread.sleep(600 * 1000);//in miliseconds
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
