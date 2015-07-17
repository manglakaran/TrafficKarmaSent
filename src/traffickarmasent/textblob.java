/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class textblob {

    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/traffickramasenti";
    private static final String DBUser = "root";
    private static final String DBPasswd = "admin";
    static HashMap<String, Double> senti_map;
    static HashMap<String, Double> hashtag_map;
    static double new_score;
    static double new_count;
    static MaxentTagger tagger;
    static HashMap<String, Double> emohash2;

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

    public static void main(String[] args) throws Exception {
        //loading senti-wordnet
        FileReader fr2 = new FileReader("extras/SentiWordNet_scores_final.txt");
        BufferedReader br2 = new BufferedReader(fr2);
        String str2;
        senti_map = new HashMap<String, Double>();
        while ((str2 = br2.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(str2, "^");
            senti_map.put(st.nextToken(), Double.parseDouble(st.nextToken()));
        }
        //System.out.println(senti_map);
        //loading hashtag
        FileReader fr3 = new FileReader("extras/hashtag_score.txt");
        BufferedReader br3 = new BufferedReader(fr3);
        String str3;
        hashtag_map = new HashMap<String, Double>();
        while ((str3 = br3.readLine()) != null) {
            String[] st = str3.split("\t");
            //System.out.println(st[1]);
            hashtag_map.put(st[1], Double.parseDouble(st[0]));
        }
        //ystem.out.println(hashtag_map);
        
        FileInputStream fos2 = new FileInputStream(new File("extras/hash2.dat")); // loading emoticon dictionary, with key as emoticon and value as its sentiment score
        ObjectInputStream out2 = new ObjectInputStream(fos2);
        emohash2 = (HashMap<String, Double>) out2.readObject();
        //System.out.println(hm2);
        tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement("Select * from traffictweets");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {

            String new_message = rs.getString(3);
            new_score = rs.getDouble(9);
            new_count = rs.getDouble(10);
            
            new_message = "retweet : <LOCATION>Santacruz Airport</LOCATION>,<LOCATION>Mumbai</LOCATION> (area of 1500 acres)ws built to handle <ORGANIZATION>Air Force</ORGANIZATION> traffic\n" +
"It ws convtd too Civil Airport assface WW2 h… ";
            String[] hashdata = new_message.split(" ");
            
            
            
            for(String word : hashdata){
                
                if(word.startsWith("#")){
                    
                    if(hashtag_map.containsKey(word.toLowerCase())){
                        //System.out.println(word);
                    new_score += hashtag_map.get(word);
                    new_count ++;
                    }
                    new_message = new_message.replace(word, " ");
                }
                
            }
            
            
                String urlPattern = "\\u2026";
                Pattern pi = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
                Matcher m = pi.matcher(new_message);
                int i = 0;
                while (m.find()) {
                    //System.out.println(m.group()+"lalalal");
                    new_message = new_message.replaceAll(m.group(i), "").trim();
                   System.out.println(new_message + "\n");
                }
            
            
            
            //System.out.println(new_message);
            //System.out.println(new_message );
            //new_message=" again some stupid just a minute at <LOCATION>Mahim</LOCATION> causeway S/B. <ORGANIZATION>BWSL</ORGANIZATION> chaps will have a good collection day today ";
            //handling words to the spell_checked
            String[] data = new_message.split("<");
            String val = "";
            for (String word : data) {
                //System.out.println(word);
                if (word.startsWith("PER") || word.startsWith("LOC") || word.startsWith("ORGAN")) {
                    word = word.replaceAll("PERSON>", "");
                    word = word.replaceAll("/PERSON>", "");
                    word = word.replaceAll("LOCATION>", "");
                    word = word.replaceAll("/LOCATION>", "");
                    word = word.replaceAll("ORGANIZATION>", "");
                    word = word.replaceAll("/ORGANIZATION>", "");
                    //System.out.println(word);
                    //insert word into database here 
                    //locArray.add(word);
                } else {
                    word = word.replaceAll("/PERSON>", "");
                    word = word.replaceAll("/LOCATION>", "");
                    word = word.replaceAll("/ORGANIZATION>", "");
                    //System.out.println(word);
                    val += word;
                }
            }
            //System.out.println(val);
            val = val.replaceAll("\\s+", " ");

            val = val.trim();
            String[] temp = val.split(" ");
            //temp = clean(temp);
            String match = "";

            for (String word : temp) {
                //System.out.println(word);
                if (!word.equals(" ")) {
                    //System.out.println(word);
                    if (emohash2.containsKey(word)) {
                        new_score += emohash2.get(word);
                        new_count++;
                        new_message = new_message.replace(word, "");
                    }

                //File dir=new File("C:\\Users\\Server\\Documents\\NetBeansProjects\\TrafficKarmaSent");
                    //System.out.println(word);
                    Process p = Runtime.getRuntime().exec("python extras/text_blob.py " + word);
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    match = in.readLine();
                    System.out.println(match);
                    if (!match.equals(word)) {
                        new_message = new_message.replaceAll(word, match);
                    }
                }
            }

            //handle NerTags
            new_message = af_spellcheck(new_message);
                //System.out.println(a);

            //removing irrelevant chars         
            new_message = removeChars(new_message);
            String finalCleaned = new_message;
            //POS- TAGGING
            new_message = posTagging(new_message);

            // removing prepositions and nouns
            new_message = removePrepn(new_message);
            //System.out.println(a);
            //new_message = removeTags(new_message);
            //System.out.println(new_message);

            sentiScores(new_message);
            System.out.println(rs.getString(1) + " " + new_score / new_count + "  " + finalCleaned);
        }
    }

    public static String[] clean(final String[] v) {
        List<String> list = new ArrayList<String>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

    public static String af_spellcheck(String message) {

        message = message.replaceAll("<PERSON>", "");
        message = message.replaceAll("</PERSON>", "");
        message = message.replaceAll("<LOCATION>", "");
        message = message.replaceAll("</LOCATION>", "");
        message = message.replaceAll("<ORGANIZATION>", "");
        message = message.replaceAll("</ORGANIZATION>", "");
        return message;
    }

    public static String removeChars(String message) {
        message = message.replaceAll("\\\\", " ");
        message = message.replaceAll("\\.", "");
        message = message.replaceAll("\\!", "");
        message = message.replaceAll("\\$", "");
        message = message.replaceAll("\\%", "");
        message = message.replaceAll("\\^", "");
        message = message.replaceAll("\\|", "");
        message = message.replaceAll("\\+", "");
        message = message.replaceAll("\\@", "");
        message = message.replaceAll("\\:", "");
        message = message.replaceAll("\\(", "");
        message = message.replaceAll("\\)", "");
        message = message.replaceAll("\\*", "");
        message = message.replaceAll("\\{", "");
        message = message.replaceAll("\\n+", " ");
        return message;
    }

    public static String posTagging(String message) throws Exception {

        String tagged = tagger.tagString(message);

        return tagged;
    }

    public static String removePrepn(String message) {
        String delims = " ";
        String[] tokens = message.split(delims);
        for (String word : tokens) {
            if (word.endsWith("_IN") || word.endsWith("_NNP") || word.endsWith("_NNPS")) {
                message = message.replace(word, "");
            }

        }
        return message;
    }

    public static String removeTags(String new_message) {
        new_message = new_message.replaceAll("<LOCATION>", "");
        new_message = new_message.replaceAll("</LOCATION>", "");
        new_message = new_message.replaceAll("<PERSON>", "");
        new_message = new_message.replaceAll("</PERSON>", "");
        new_message = new_message.replaceAll("<ORGANIZATION>", "");
        new_message = new_message.replaceAll("</ORGANIZATION>", "");
        return new_message;
    }

    public static void sentiScores(String message) {

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
            //System.out.println(word);
            if (senti_map.containsKey(word)) {

                new_score += senti_map.get(word);
                //System.out.println(score);
                new_count++;
            }

        }

    }
}
