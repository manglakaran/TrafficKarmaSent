package com;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class TweetDisplay {
     int tweetHourlyCount=0;
    public ArrayList<TweetInfo> getTweetDetail() {
        
        ArrayList<TweetInfo> al = new ArrayList<TweetInfo>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\twitterData\\"+CitySelect.city+"\\traffic_data_"+CitySelect.city+"_display.txt"));
            String line = br.readLine();
            StringBuffer data = new StringBuffer();
            while (line != null) {
                data.append(line);
                line = br.readLine();
            }
            String text = data.toString();
            text = text.replaceAll("\\n", " ");
            //System.out.println(text);
            String fullTokens[] = text.split("\\$\\$\\$");
            System.out.println(fullTokens.length);
           for(int i=fullTokens.length-1;i>=fullTokens.length-100;i--) {
                String time="";
                String screenName="";
                String profileURL="";
                String userId="";
                String statusId="";
                String name="";
                String textData="";
                String dataLine = fullTokens[i];
                //System.out.println(dataLine);
                String partialTokens[] = dataLine.split("\\|\\|");
                time = partialTokens[0];
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(time);
                Long dateL=date.getTime();
                Long now=new Date().getTime();
                if(dateL>now-3600*1000)
                {
                   
                    tweetHourlyCount++;
                }
                screenName = partialTokens[1];
                profileURL = partialTokens[2];
                userId = partialTokens[3];
                statusId = partialTokens[4];
                name=partialTokens[5];
                textData=partialTokens[6];
                //System.out.println(screenName + "   " + textData + "   " + time);
                if (!textData.startsWith("RT")&& !textData.startsWith("@TrafficKarma")) {
                    TweetInfo t = new TweetInfo(time, screenName, profileURL, userId, statusId, name, textData);
                    al.add(t);
                }

            }
           
            
            

    }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return al;
    }
    public String getHourlyCount()
    {
        //System.out.println(tweetHourlyCount);
        return tweetHourlyCount+"";
    }
}
