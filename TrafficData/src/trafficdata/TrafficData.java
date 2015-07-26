package trafficdata;

import com.restfb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrafficData {

    public static void main(String[] args) throws IOException {
        //BufferedWriter fw=null;
        try{
        //fw=new BufferedWriter(new FileWriter("d:\\Parkingcomplaints.txt"));
        Date current = new Date();
        FacebookClient facebookClient = new DefaultFacebookClient("CAAMFiSDA43ABAEr1dedHZCZBRvYWLKH7SffZB6vCZAffpPUsYdRz3HQ7kie0BQrxOEbin8Hx7sfAoyPZBGMdhKky0LtleOiVFTAQOAE3uS5V1JoJLYm8glIvLtsreF7uZCgZBT1k53CGDLuShmrh5KQLif6ZAlcsdOhvSym14NZBuz5FatHX5XonwdUZB5nzQc5ckZD");
        Connection<Post> myFeed = facebookClient.fetchConnection("147207215344994/posts", Post.class);
		//Page page = facebookClient.fetchObject("BangaloreTrafficPolice", Page.class);
        //Connection<User> myFriends = facebookClient.fetchConnection("me/friends",User.class,Parameter.with("Fields", "name,id,picture"));
        //Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);

    	//System.out.println("Count of my friends: " + myFriends.getData().size());
        String url = "https://www.facebook.com/pages/Delhi-Traffic-Police/117817371573308";
        Page page = facebookClient.fetchObject(url, Page.class);
        System.out.println(page.getId());

        long currentL = current.getTime() * 1000;
        System.out.println(currentL);
        Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);
        to:
        for (List<Post> feed : pageFeed) {
            for (Post post : feed) {
                if ((post.getFrom().getName().equals("Delhi Traffic Police"))) {
                    FileWriter fw=new FileWriter("c:\\users\\server\\lala.txt",true);
                    System.out.println(post.getFrom().getName());
                    Date d = post.getCreatedTime();
                    String message=post.getMessage();
//                    long dL = d.getTime() * 1000;
//                    System.out.println(dL);
                    String name = post.getFrom().getName().trim();
                   
                   //System.out.println(post.getFrom().getId());
                    User u=facebookClient.fetchObject(post.getFrom().getId(), User.class);
                    String profilePic="laal";
                    String picLink="#";
                    if(post.getPicture()!=null)
                    picLink=post.getPicture();
                    String profileLink="laal";
                    String userLoc="laal";
                    if(u.getPicture()!=null)       
                    profilePic=u.getPicture().getUrl();
                    if(u.getLink()!=null)
                    profileLink=u.getLink();
                    if(u.getLocation()!=null)
                    userLoc=u.getLocation().getName();
                    System.out.println(picLink);
                    fw.write(d.toLocaleString()+"###"+name+"###"+profileLink+"###"+message+"###"+picLink+"###"+profilePic+"###"+userLoc+" $$$$\n");
                    fw.close(); 
                    
                }

            }
        }}
        
        finally
        {
               
        }

//		System.out.println("User name: " + page.getName());
//		System.out.println(page.getId());
//		System.out.println("Page likes: " + page.getLikes());
    }
}
