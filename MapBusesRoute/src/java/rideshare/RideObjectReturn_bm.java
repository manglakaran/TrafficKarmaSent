/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rideshare;

import databases_rideshare.RideReturnInfo;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class RideObjectReturn_bm {
    public String getJson(ArrayList<String> al)
    {
        String ret="";
        JSONObject finalObj = new JSONObject();
        JSONArray list = new JSONArray();
        
        //int count=0, j=0;
       
            
            JSONObject usersObject = new JSONObject();
            JSONArray userList = new JSONArray();
            for (int i=0; i<al.size(); i++)
            {
                String dtbs=al.get(i);
                int rideId=Integer.parseInt(dtbs.split(",")[0]);
                double oow=Double.parseDouble(dtbs.split(",")[3]);
                JSONObject userObject = new JSONObject();
                RideReturnInfo rri=new RideReturnInfo();
                ArrayList<String> userInfo=rri.getUserInfo(rideId);
                userObject.put("userName", userInfo.get(1));
                userObject.put("sex", userInfo.get(0));
                userObject.put("age", userInfo.get(4));
                userObject.put("source", userInfo.get(2));
                userObject.put("destination", userInfo.get(3));
                userObject.put("rideId", rideId);
                userObject.put("outOfWay", oow);
                userObject.put("sll",userInfo.get(5));
                userObject.put("dll",userInfo.get(6));
                userObject.put("cn",userInfo.get(7));
                userObject.put("rn", dtbs.split(",")[1]);
                userObject.put("ol", dtbs.split(",")[2]);
                userList.add(userObject);
                
            }
            usersObject.put(0, userList);
            list.add(usersObject);
        
        finalObj.put("data",list );
        System.out.println(finalObj);
        return finalObj.toString();
    }
    
}