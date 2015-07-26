package TwitterHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.*;
import java.util.*;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class HandleTweet {

    public void TweetKarma(String text,String from) {
        Twitter twitter = new TwitterFactory().getInstance();
        text=text.replaceAll("@TrafflineDEL", " ");
        AccessToken accessToken = new AccessToken("3023900604-QM2opAtHAtAqss7mHAgLg1BKta2UXK9hybMct5w", "XZZJ4VzVkeho6TJFwGuOJYTK0djeHqbkLP72AUBsL3JBv");
        twitter.setOAuthConsumer("hgwsx8CcgIvVpGboiQ5NXDrjd", "DvxLIuycI1DiqdG7rI8a2MJxpCAp0VCNI36k4vdTzTRqcLq4dy");
        twitter.setOAuthAccessToken(accessToken);
        try {
            twitter.updateStatus("@TrafficKarma @"+" "+text);
        } catch (TwitterException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Successfully updated the status.");
    }

}
