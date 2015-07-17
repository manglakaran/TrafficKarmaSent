/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author karan
 */
public class newgetpage {

    public static void main(String[] args) throws IOException {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true);
            cb.setOAuthConsumerKey("GPtsu5cjC08KTOEojEoaHw");
            cb.setOAuthConsumerSecret("SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
            cb.setOAuthAccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre");
            cb.setOAuthAccessTokenSecret("DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter1 = tf.getInstance();
            List<Status> statuses;
            String user;
            String[] users = {"livetrafficsyd", "trafficnsw", "sydtraffic_cs", "WazeTrafficSYD", "livetrafficnsw"};
            Date[] d_users = {new Date(99, 2, 12), new Date(99, 2, 12), new Date(99, 2, 12), new Date(99, 2, 12), new Date(99, 2, 12)};

            while (true) {
                for (int i = 0; i < users.length; i++) {

                    statuses = twitter1.getUserTimeline(users[i]);
                    
                    for (int j= statuses.size()- 1;j >= 0; j--) {
                        Status st = statuses.get(j);
                        if (d_users[i].before(st.getCreatedAt())){
                         String message = removeUrl(st.getText());
                         
                         File file = new File("out_sydney_new.txt");

                        //if file doesnt exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        //true = append file
                        FileWriter fileWritter = new FileWriter(file.getName(), true);
                        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                        bufferWritter.write(message + "\n");
                        bufferWritter.close();

                        System.out.println("Done");    
                            
                            
                        System.out.println("@" + st.getUser().getScreenName() + " - " + st.getText());
                        d_users[i] = st.getCreatedAt();
                        }
                    }  
                }
                try {
                Thread.sleep(300000);                 //1000 milliseconds is one second.
                 } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                  System.out.println("firse");

            }

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
    public static String removeUrl(String message) {
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
}
