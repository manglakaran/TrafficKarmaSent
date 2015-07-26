/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateDisplayJson {

    public String getJson(String city, String duration) throws Exception {
        String finalReturn = "";
        JSONArray list = new JSONArray();
        JSONObject finalObj = new JSONObject();
        ArrayList<String> month=new ArrayList<String>();
        month.add("Jan");
        month.add("Feb");
        month.add("Mar");
        month.add("Apr");month.add("May");month.add("Jun");month.add("Jul");month.add("Aug");
        month.add("Sep");month.add("Oct");month.add("Nov");month.add("Dec");
        Date d = new Date();
        int dayCount = Integer.parseInt(duration);
        Long dL = d.getTime();
        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\twitterData\\" + city + "\\traffic_data_" + city + "_display.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringBuffer text = new StringBuffer();
        while (line != null) {
            text.append(line);
            line = br.readLine();
        }
        String textData = text.toString();
        textData = textData.replaceAll("\\n", " ");
        String fullTokens[] = textData.split("\\$\\$\\$");
        for(int i=fullTokens.length-1;i>=1;i--) 
            {
                StringTokenizer st=new StringTokenizer(fullTokens[i],"||");
                String dateString=st.nextToken();
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(dateString);
                Long dateL=date.getTime();
                //System.out.println(dateL);
                String name=st.nextToken();
                name=name.replaceAll("'", " ");
                if(dL-((dayCount+1)*86400*1000)<=dateL && dateL<= dL-((dayCount)*86400*1000))
                {
                    //System.out.println("i am in");
                    JSONObject js=new JSONObject();
                    js.put("userScreenName", name);
                    System.out.println(name);
                    js.put("userProfilePic", st.nextToken());
                    js.put("userId", st.nextToken());
                    js.put("statusId", st.nextToken());
                    js.put("username", st.nextToken());
                    js.put("text", st.nextToken());
                   
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm a ");
                    //System.out.println(format.format(date));
                    //System.out.println(date.getDate()+"-"+month.get(date.getMonth())+" "+format.format(date));              
                    //System.out.println(dateL);
                    
                    js.put("time",date.getDate()+"-"+month.get(date.getMonth())+" "+format.format(date));
                    list.add(js);
                }
                line=br.readLine();
            }
        finalObj.put("locations", list);
            br.close();
            finalReturn=finalObj.toString();
            System.out.println(finalReturn);
        return finalReturn;
    }

}
