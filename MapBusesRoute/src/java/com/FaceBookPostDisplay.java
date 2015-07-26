package com;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class FaceBookPostDisplay {

    int postHourlyCount = 0;
    ArrayList<String> mes = new ArrayList<String>();

    public ArrayList<FaceBookPostInfo> getPostDetail() {
        ArrayList<FaceBookPostInfo> al = new ArrayList<FaceBookPostInfo>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\facebookData\\" + CitySelect.city + "\\JustData.txt"));
            String line = br.readLine();
            StringBuffer data = new StringBuffer();
            while (line != null) {
                data.append(line);
                line = br.readLine();
            }
            String text = data.toString();
            //System.out.println(text);
            String fullTokens[] = text.split("\\$\\$\\$");
            System.out.println(fullTokens.length + "laal");

            for (int i = fullTokens.length - 1; i >= fullTokens.length - 30; i--) {
                String time = "";
                String message = "";
                String id = "";
                String from = "";
                String fromId = "";
                String dataLine = fullTokens[i];
                //System.out.println(dataLine);
                String partialTokens[] = dataLine.split("\\#\\#\\#");
                time = partialTokens[0];
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(time);
                Long dateL = date.getTime();
                Long now = new Date().getTime();
                if (dateL > now - 3600 * 1000) {
                    postHourlyCount++;
                }
                message = partialTokens[1];
                String messageSummary[] = message.split(" ");
                if (messageSummary.length < 30) {
                    message = partialTokens[1];
                } else {
                    message = "";
                    for (int summ = 0; summ < 30; summ++) {
                        message = message + " " + messageSummary[summ];
                    }
                }

                id = partialTokens[2];
                from = partialTokens[3];
                fromId = partialTokens[4];

                //System.out.println(message );
                FaceBookPostInfo fb = new FaceBookPostInfo(time, message, id, from, fromId);
                al.add(fb);

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return al;
    }

    public String getHourlyCount() {
        System.out.println(postHourlyCount);
        return postHourlyCount + "";
    }
}
