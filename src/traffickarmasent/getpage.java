package traffickarmasent;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public final class getpage {

    public static void main(String[] args) throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("GPtsu5cjC08KTOEojEoaHw");
        cb.setOAuthConsumerSecret("SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
        cb.setOAuthAccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre");
        cb.setOAuthAccessTokenSecret("DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        int pageno = 1;
        //String user = "sydtraffic_cs";

        int flag = 0;
        String message;
        String[] users = {"livetrafficsyd", "trafficnsw", "sydtraffic_cs", "WazeTrafficSYD", "livetrafficnsw"};
        //String[] users = {"manglakaran", "kirbrick"};
        Date e_time = new Date(99, 2, 12);
        Date[] d_users = {new Date(99,2,12),new Date(99,2,12),new Date(99,2,12),new Date(99,2,12),new Date(99,2,12) };
        //Date[] d_users = {new Date(99, 2, 12), new Date(99, 2, 12)};
        //System.out.println(e_time);
        while (true) {
            for (int j = 0; j < users.length; j++) {
                String user = users[j];
            //System.out.println(d_users[j]);
                //System.out.println(user);
                List<Status> statuses = new ArrayList<Status>();
                pageno = 1;
                flag = 0;
                while (true) {

                    try {

                        int size = statuses.size();
                        System.out.println(size);
                        Paging page = new Paging(pageno++, 100);
                        statuses.addAll(twitter.getUserTimeline(user, page));
                        
                        if (statuses.size() == 500) {
                            break;
                        }
                    } catch (TwitterException e) {

                        e.printStackTrace();
                    }
                }
                for (int i = statuses.size() - 1; i >= 0; i--) {

                    Status st = statuses.get(i);
                    //System.out.println("bahar" + st.getText());
                    if (d_users[j].before(st.getCreatedAt())) {
                        message = removeUrl(st.getText());
                        System.out.println(message );
                        d_users[j] = st.getCreatedAt();
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
