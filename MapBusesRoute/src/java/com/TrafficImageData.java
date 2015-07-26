/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class TrafficImageData {
    public ArrayList<ImageInfo> getInfo()
    {
        int imageCount=0;
        ArrayList<ImageInfo> al=new ArrayList<ImageInfo>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\twitterData\\"+CitySelect.city+"\\traffic_data_"+CitySelect.city+"_media.txt"));
            String line = br.readLine();
            StringBuffer data = new StringBuffer();
            while (line != null) {
                data.append(line);
                line = br.readLine();
            }
            String text = data.toString();
            text = text.replaceAll("\\n", " ");
            String fullTokens[] = text.split("\\$\\$\\$");
            //System.out.println(fullTokens.length);
            StringTokenizer st = new StringTokenizer(text, "$$$$");
            for(int i=fullTokens.length-1;(i>=0 && imageCount<30);i--) { 
                String imageURL;
                String textData;
                String time;
                String userName;
                String profileImage;
                String dataLine = fullTokens[i];
                String partialTokens[] = dataLine.split("\\|\\|");
                imageURL=partialTokens[0];
                textData=partialTokens[1];
                time=partialTokens[2];
                userName=partialTokens[3];
                //System.out.println(imageURL+"   "+textData+"   "+time);
                profileImage=partialTokens[4];
                if(!textData.startsWith("RT"))
                {
                imageCount++;
                ImageInfo ii=new ImageInfo(imageURL, textData, time, userName, profileImage);
                al.add(ii);
                }
            }
            
            
            /*while (line!=null) {
                String imageURL;
                String text;
                String time;
                String userName;
                String profileImage;
                StringTokenizer st1 = new StringTokenizer(line, "###");
                imageURL=st1.nextToken();
                text=st1.nextToken();
                time=st1.nextToken();
                userName=st1.nextToken();
                System.out.println(imageURL+"   "+text+"   "+time);
                profileImage=st1.nextToken();
                ImageInfo i=new ImageInfo(imageURL, text, time, userName, profileImage);
                al.add(i);
                line=br.readLine();
            }
*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return al;
    }
    
}
