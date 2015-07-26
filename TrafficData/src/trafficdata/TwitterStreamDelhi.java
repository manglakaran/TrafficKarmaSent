package trafficdata;

import TwitterHandle.HandleTweet;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.xml.sax.InputSource;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.MediaEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author kanu
 */
public class TwitterStreamDelhi {
    private static final String DBDriver="com.mysql.jdbc.Driver";
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
        private static Connection getConnection()throws Exception
	{
            Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPasswd);
            return con;
	}

    public static void main(String[] args) throws IOException, ParserConfigurationException, Exception {
        FileReader fr2 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\TrafficData\\SentiWordNet_scores_final.txt");
        BufferedReader br2 = new BufferedReader(fr2);
        String str2;
        HashMap<String, String> hm = new HashMap<String, String>();
        while ((str2 = br2.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(str2, "^");
            hm.put(st.nextToken(), st.nextToken());
        }
        String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        String[] options = {"-maxLength", "1000", "-retainTmpSubcategories"};
        LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
        TreebankLanguagePack tlp = lp.getOp().langpack();
        Connection con = getConnection();
        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("hgwsx8CcgIvVpGboiQ5NXDrjd");
        cb.setOAuthConsumerSecret("DvxLIuycI1DiqdG7rI8a2MJxpCAp0VCNI36k4vdTzTRqcLq4dy");
        cb.setOAuthAccessToken("3023900604-QM2opAtHAtAqss7mHAgLg1BKta2UXK9hybMct5w");
        cb.setOAuthAccessTokenSecret("XZZJ4VzVkeho6TJFwGuOJYTK0djeHqbkLP72AUBsL3JBv");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        HandleTweet ht=new HandleTweet();
        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                //if(!(status.getText().startsWith("@TrafficKarma")))
                //ht.TweetKarma(status.getText(), status.getUser().getScreenName());
                String message=status.getText();
                String cleanData=message;
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
            double score = sum / count;
                System.out.println(score);
                String state="";
                if(score<=1.0 && score>0.3)
                    state="fast";
                else if(score<=0.3 && score>=0.1)
                    state="moderate";
                else if(score<0.1 && score>-0.3)
                    state="slow";
                else if(score<=-0.3 && score>=-1.0)
                    state="heavy";
                else
                    state="unknown";
                message = message.replaceAll("#", " ");
                message = message.replaceAll("!", " ");
                message = message.replaceAll("$", " ");
                message = message.replaceAll("\\s+", " ");
                message = message.replaceAll("\\n+", " ");
                BufferedWriter fw = null;
                BufferedWriter fw1 = null;
                BufferedWriter fw2 = null;
                try {
                    PreparedStatement displaySta = con.prepareStatement("insert into trafficdatadisplay(time,screenname,profileurl,userid,statusid,username,text,city,source) values(?,?,?,?,?,?,?,?,?)");
                    displaySta.setString(1,new Date().toString());
                    displaySta.setString(2, status.getUser().getScreenName());
                    displaySta.setString(3, status.getUser().getBiggerProfileImageURL());
                    displaySta.setString(4, status.getUser().getId()+"");
                    displaySta.setString(5, status.getId()+"");
                    displaySta.setString(6, status.getUser().getName());
                    displaySta.setString(7, status.getText());
                    displaySta.setString(8, "delhi");
                    displaySta.setString(9, "twitter");
                    displaySta.executeUpdate();
                    displaySta.close();
                    fw2 = new BufferedWriter(new FileWriter("C:\\Users\\Server\\Documents\\twitterData\\Delhi\\traffic_data_delhi_display.txt", true));
                    fw2.write(new Date()+"||"+status.getUser().getScreenName()+"||"+status.getUser().getBiggerProfileImageURL()+"||"+status.getUser().getId()+"||"+status.getId()+"||"+status.getUser().getName()+"||"+message+"$$$");
                    fw2.flush();
                    fw2.close();
                    MediaEntity e[]=status.getMediaEntities();
                    if(e.length>0);
                    {
                        for(int me=0;me<e.length;me++)
                        {
                            fw1 = new BufferedWriter(new FileWriter("C:\\Users\\Server\\Documents\\twitterData\\Delhi\\traffic_data_delhi_media.txt", true));
                            fw1.write(e[me].getMediaURL()+"||"+message+"||"+status.getCreatedAt()+"||"+status.getUser().getName()+"||"+status.getUser().getBiggerProfileImageURL()+"$$$");
                            System.out.println(e[me].getMediaURL());
                            fw1.close();
                        }
                        
                    }
                    
                    
                    fw = new BufferedWriter(new FileWriter("C:\\Users\\Server\\Documents\\twitterData\\Delhi\\traffic_data_delhi.txt", true));
                   // if(!(status.getText().startsWith("@TrafficKarma")))
                    //{
                    String XmlData = classifier.classifyWithInlineXML(message);
                    System.out.println(XmlData);
                    StringTokenizer st = new StringTokenizer(XmlData, "<");
                    while (st.hasMoreTokens()) {
                        String ele = st.nextToken();
                        if (ele.startsWith("LO")||ele.startsWith("PER")) {
                            FileWriter fw_ver = new FileWriter("C:\\Users\\Server\\Documents\\twitterData\\Delhi\\traffic_data_delhi_verification.txt", true);
                            PreparedStatement sta = con.prepareStatement("insert into trafficdata(time,address,lat,lng,text,sentiment,city,source,state) values(?,?,?,?,?,?,?,?,?)");
                            String geoCode="na";
                            ele = ele.replace("LOCATION>", "");
                            ele = ele.replace("/LOCATION>", "");
                            ele = ele.replace("PERSON>", "");
                            ele = ele.replace("/PERSON>", "");
                            System.out.println(ele.trim());
                            String address = ele.trim() + ",delhi";
                            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address) + "&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
                            URI uri = new URI(url);
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
                            JSONArray arr1 = (JSONArray) jso.get("results");

                            String data1 = arr1.get(0).toString();
                            Object obj1 = JSONValue.parse(data1);
                            JSONObject jso1 = (JSONObject) obj1;
                            String address_updated = jso1.get("formatted_address").toString();
                            //System.out.println(address_updated);
                            String data2 = jso1.get("geometry").toString();
                            Object obj2 = JSONValue.parse(data2);
                            JSONObject jso2 = (JSONObject) obj2;
                            String data3 = jso2.get("location").toString();
                            Object obj3 = JSONValue.parse(data3);
                            JSONObject jso3 = (JSONObject) obj3;
                            String lat = jso3.get("lat").toString();
                            String lng = jso3.get("lng").toString();
                            
                            DataGetterGoogle dd = new DataGetterGoogle();
                            fw_ver.write(new Date()+"-"+status.getId()+"-"+dd.getDataGoogle(lat+","+lng)+"-"+score+"-"+state+"\n");
                            fw_ver.close();
                            sta.setString(1,new Date().toString());
                            sta.setString(2,address_updated);
                            sta.setString(3,lat);
                            sta.setString(4,lng);
                            sta.setString(5,status.getText());
                            sta.setString(6,score+"");
                            sta.setString(7,"delhi");
                            sta.setString(8,"twitter");
                            sta.setString(9,state);
                            sta.executeUpdate();
                            if(status.getGeoLocation()!=null)
                            geoCode=status.getGeoLocation().toString();
                            fw.write(new Date()+"###"+address_updated+"###"+lat+"###"+lng+"###"+status.getText()+"###"+geoCode+"$$$");
                            sta.close();
                        }
                    }
                    //}
                } catch (Exception ex) {
                    Logger.getLogger(TwitterStream.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.flush();
                        fw.close();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(TwitterStream.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onException(Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = {"Delhi traffic", "@TrafflineDEL",  "TrafficDel", "DelhiTrafficPol", "avoid traffic delhi","Delhi traffic police",
            "Breakdown delhi traffic","@TrafficBOM" , "#StreetSmartWithTraffline delhi", "#delhi #TRAFFICALERT ", "#delhi #TRAFFIC","dtptraffic"
                ,"#trafficblues"
        };

        fq.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

    }

}
