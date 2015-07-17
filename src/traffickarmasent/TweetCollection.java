/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import java.io.FileNotFoundException;
import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import twitter4j.GeoLocation;

public class TweetCollection {

    static Map<String, String> slangMap;
    static List<String> entityList;
    static HashMap<String, Double> emohash1;
    static HashMap<String, Double> emohash2;
    static HashMap<String, Double> senti_map;

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        // loading slang dictionary with key as slang and value as its full form
        slangMap = new HashMap<String, String>();
        BufferedReader slangRead = new BufferedReader(new FileReader("extras/out.txt"));
        String line = "";
        while ((line = slangRead.readLine()) != null) {
            String parts[] = line.split("\t");
            slangMap.put(parts[0], parts[1]);
        }
        slangRead.close();

        //loading entity list
        BufferedReader htm_in = new BufferedReader(new FileReader("extras/html_ent.txt"));
        entityList = new ArrayList<String>();
        while ((line = htm_in.readLine()) != null) {
            entityList.add(line);
        }
        FileInputStream fos1 = new FileInputStream(new File("extras/hash1.dat")); // loading emoticon dictionary, with key as emoticon and value as its sentiment score
        ObjectInputStream out1 = new ObjectInputStream(fos1);
        emohash1 = (HashMap<String, Double>) out1.readObject();
        //System.out.println(hm1);

        FileInputStream fos2 = new FileInputStream(new File("extras/hash2.dat")); // loading emoticon dictionary, with key as emoticon and value as its sentiment score
        ObjectInputStream out2 = new ObjectInputStream(fos2);
        emohash2 = (HashMap<String, Double>) out2.readObject();
        //System.out.println(hm2);

        //loading senti-wordnet
        FileReader fr2 = new FileReader("extras/SentiWordNet_scores_final.txt");
        BufferedReader br2 = new BufferedReader(fr2);
        String str2;
        senti_map = new HashMap<String, Double>();
        while ((str2 = br2.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(str2, "^");
            senti_map.put(st.nextToken(), Double.parseDouble(st.nextToken()));
        }

        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz"; //NER configuration
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        
        MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("ufulV3imKoYNzdh58LotTC1YD");
        cb.setOAuthConsumerSecret("2A781ma736HTenAXXYn9tRIelQYJkbCqY0GLi7W71ZwwDmNU59");
        cb.setOAuthAccessToken("2564905075-MY9osfHabaRnonQVHHhHeA1vCLSOhuHWjBNBiIY");
        cb.setOAuthAccessTokenSecret("JsD8Woc7iiFiDSwoCwjNAb6KNEurz7tBqSj9pJV8WXabr");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {
            double score = 0.0;
            double count = 0;
            ArrayList<String> locArray = new ArrayList<String>();

            @Override

            public void onStatus(Status status) {
                String text = status.getText();

                double geoLat = 0.0;
                double geoLng = 0.0;
                String tweetId = status.getId() + "";
                String userName = status.getUser().getName();
                String userId = status.getUser().getId() + "";
                if (status.getGeoLocation() != null) {
                    geoLat = status.getGeoLocation().getLatitude();
                    geoLng = status.getGeoLocation().getLongitude();
                }

                tweetClean(text, status.getGeoLocation());
                System.out.println(text + "\n" + tweetId + " " + userName + " " + userId);

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

            public void tweetClean(String message, GeoLocation loc) {
                try {

                    // URL removal 
                    message = removeUrl(message);
                    System.out.println("lalala" + message);
                    //removing user mentions
                    message = userMentions(message);
                    //slang removal
                    String[] acro = slangRemoval(message);
                    //entity removal
                    String[] finaldata = entityRemoval(acro);
                    message = "";
                    for (String word : finaldata) {
                        message += word + " ";
                    }
                    //System.out.println(message);
                    //Ner Taggging
                    String XmlData = classifier.classifyWithInlineXML(message);
                    message = XmlData;

                    //handling words to the spell_checked
                    String[] data = message.split("<");
                    String val = "";
                    for (String word : data) {
                        if (word.startsWith("PER") || word.startsWith("LOC")) {
                            word = word.replaceAll("PERSON>", "");
                            word = word.replaceAll("/PERSON>", "");
                            word = word.replaceAll("LOCATION>", "");
                            word = word.replaceAll("/LOCATION>", "");
                            //insert word into database here 
                            locArray.add(word);
                        } else {
                            word = word.replaceAll("/PERSON>", "");
                            word = word.replaceAll("/LOCATION>", "");
                            //System.out.println(word);
                            val += word;
                        }
                    }
                    //System.out.println("see" + val);
                    if (loc != null || locArray.size() > 0) {
                        val = val.replaceAll("\\s+", " ");
                        String[] temp = val.split(" ");
                        String match = "";
                        //spell_check
                        for (String word : temp) {
                            //System.out.println(word);
                            if (emohash2.containsKey(word)) {
                                score += emohash2.get(word);
                                count++;
                                message = message.replace(word, "");
                            }
                            Process p = Runtime.getRuntime().exec("python extras/text_blob.py " + word);
                            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            //System.out.println(in.readLine());
                            match = in.readLine();
                        //match = spell_check(word);
                            //System.out.println(match);
                            if (!match.equals(word)) {
                                message = message.replaceAll(word, match);
                            }

                        }
                        System.out.println(message);
                        //UTF-8 emoji's
                        emojiDetection(message);
                        //handle NerTags
                        message = af_spellcheck(message);
                //System.out.println(a);

                        //removing irrelevant chars         
                        message = removeChars(message);
                //System.out.println(a);

                        //POS- TAGGING
                        message = posTagging(message);

                        // removing prepositions and nouns
                        message = removePrepn(message);
                        //System.out.println(a);

                        sentiScores(message);
                        System.out.println(score);
                        System.out.println(score / count);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }

            public String removeUrl(String message) {
                String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(message);
                int i = 0;
                while (m.find()) {
                    message = message.replaceAll(m.group(i), "").trim();
                    i++;
                }
                return message;
            }

            public String af_spellcheck(String message) {

                message = message.replaceAll("<PERSON>", "");
                message = message.replaceAll("</PERSON>", "");
                message = message.replaceAll("<LOCATION>", "");
                message = message.replaceAll("</LOCATION>", "");
                return message;
            }

            public String removeChars(String message) {

                message = message.replaceAll("\\.", "");
                message = message.replaceAll("\\!", "");
                message = message.replaceAll("\\$", "");
                message = message.replaceAll("\\%", "");
                message = message.replaceAll("\\^", "");
                message = message.replaceAll("\\|", "");
                message = message.replaceAll("\\+", "");
                message = message.replaceAll("\\:", "");
                message = message.replaceAll("\\(", "");
                message = message.replaceAll("\\)", "");
                message = message.replaceAll("\\*", "");
                message = message.replaceAll("\\{", "");
                return message;
            }

            public void sentiScores(String message) {

                message = message.replaceAll("_NNS", "_n");
                message = message.replaceAll("_NN", "_n");
                message = message.replaceAll("_RBR", "_r");
                message = message.replaceAll("_RBS", "_r");
                message = message.replaceAll("_RB", "_r");
                message = message.replaceAll("_JJR", "_a");
                message = message.replaceAll("_JJS", "_a");
                message = message.replaceAll("_JJ", "_a");
                message = message.replaceAll("_VBD", "_v");
                message = message.replaceAll("_VBG", "_v");
                message = message.replaceAll("_VBN", "_v");
                message = message.replaceAll("_VBP", "_v");
                message = message.replaceAll("_VBZ", "_vs");
                message = message.replaceAll("_VB", "_v");

                message = message.replaceAll("\\s+", " ");
                // System.out.println(message);
                String[] senti_token = message.split(" ");

                for (String word : senti_token) {
                    word = word.toLowerCase();
                    System.out.println(word);
                    if (senti_map.containsKey(word)) {

                        score += senti_map.get(word);
                        //System.out.println(score);
                        count++;
                    }

                }

            }

            public void emojiDetection(String message) {
                Pattern emo = Pattern.compile("[\\uD83D\\uDE01-\\uD83D\\uDE4F]");
                Matcher m_emo = emo.matcher(message);
                while (m_emo.find()) {
                    if (emohash1.containsKey(m_emo.group())) //System.out.println("llalala");
                    {
                        score += emohash1.get(m_emo.group());
                    }
                    count++;
                }
            }

            public String userMentions(String message) {
                Pattern p = Pattern.compile("\\@\\w+");
                Matcher m = p.matcher(message);
                while (m.find()) {
                    //System.out.println(m.group());
                    message = message.replaceAll(m.group(), "");
                }
                return message;

            }

            public String[] slangRemoval(String message) {
                ArrayList<String> slangRemovalList = new ArrayList<String>();
                String[] words = message.split(" ");
                for (String single : words) {
                    if (slangMap.containsKey(single.toUpperCase())) {
                        slangRemovalList.add(slangMap.get(single.toUpperCase()));
                    } else {
                        slangRemovalList.add(single);
                    }
                }
                String[] myArray = new String[slangRemovalList.size()];
                slangRemovalList.toArray(myArray);
                return myArray;
            }

            public String posTagging(String message) throws Exception {
                
                String tagged = tagger.tagString(message);

                return tagged;
            }

            public String removePrepn(String message) {
                String delims = " ";
                String[] tokens = message.split(delims);
                for (String word : tokens) {
                    if (word.endsWith("_IN") || word.endsWith("_NNP") || word.endsWith("_NNPS")) {
                        message = message.replace(word, "");
                    }

                }
                return message;
            }

            public String[] entityRemoval(String[] message) {
                List<String> finalList = new ArrayList<String>();
                for (String word : message) {
                    if (!entityList.contains(word.trim())) {
                        finalList.add(word);
                    }
                }
                String[] myArray = new String[finalList.size()];
                finalList.toArray(myArray);
                return myArray;
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = {"Mumbai traffic", "@TrafflineMUM", "TrafficMum", "MumbaiTrafficPol", "avoid traffic Mumbai",
            "Breakdown Mumbai traffic", "@smart_mumbaikar", "@TrafficBOM", "#StreetSmartWithTraffline mumbai", "#mumbai #TRAFFICALERT ", "#mumbai #TRAFFIC"
        };

        fq.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }

}
