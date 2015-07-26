package trafficdata;

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
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.json.simple.JSONValue;

public final class UserStreamData {

    public static void main(String[] args) throws TwitterException {
        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("GPtsu5cjC08KTOEojEoaHw");
        cb.setOAuthConsumerSecret("SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
        cb.setOAuthAccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre");
        cb.setOAuthAccessTokenSecret("DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        UserStreamListener listener = new UserStreamListener() {
            @Override
            public void onStatus(Status status) {
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onDeletionNotice(long directMessageId, long userId) {
                System.out.println("Got a direct message deletion notice id:" + directMessageId);
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got a track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onFriendList(long[] friendIds) {

            }

            @Override
            public void onFavorite(User source, User target, Status favoritedStatus) {

            }

            @Override
            public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
                System.out.println("onUnFavorite source:@"
                        + source.getScreenName() + " target:@"
                        + target.getScreenName() + " @"
                        + unfavoritedStatus.getUser().getScreenName()
                        + " - " + unfavoritedStatus.getText());
            }

            @Override
            public void onFollow(User source, User followedUser) {
                System.out.println("onFollow source:@"
                        + source.getScreenName() + " target:@"
                        + followedUser.getScreenName());
            }

            @Override
            public void onUnfollow(User source, User followedUser) {
                System.out.println("onFollow source:@"
                        + source.getScreenName() + " target:@"
                        + followedUser.getScreenName());
            }

            @Override
            public void onDirectMessage(DirectMessage directMessage) {
                System.out.println("onDirectMessage text:"
                        + directMessage.getText());
            }

            @Override
            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
                System.out.println("onUserListMemberAddition added member:@"
                        + addedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
                System.out.println("onUserListMemberDeleted deleted member:@"
                        + deletedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
                System.out.println("onUserListSubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
                System.out.println("onUserListUnsubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListCreation(User listOwner, UserList list) {
                System.out.println("onUserListCreated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListUpdate(User listOwner, UserList list) {
                System.out.println("onUserListUpdated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListDeletion(User listOwner, UserList list) {
                System.out.println("onUserListDestroyed  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserProfileUpdate(User updatedUser) {
                System.out.println("onUserProfileUpdated user:@" + updatedUser.getScreenName());
            }

            public void onUserDeletion(long deletedUser) {
                System.out.println("onUserDeletion user:@" + deletedUser);
            }

            public void onUserSuspension(long suspendedUser) {
                System.out.println("onUserSuspension user:@" + suspendedUser);
            }

            @Override
            public void onBlock(User source, User blockedUser) {
                System.out.println("onBlock source:@" + source.getScreenName()
                        + " target:@" + blockedUser.getScreenName());
            }

            @Override
            public void onUnblock(User source, User unblockedUser) {
                System.out.println("onUnblock source:@" + source.getScreenName()
                        + " target:@" + unblockedUser.getScreenName());
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
                System.out.println("onException:" + ex.getMessage());
            }
        };
        twitterStream.addListener(listener);
        // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.user();
    }

}
