/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author karan
 */
public class spellcheck {
    public static void main(String[] args) throws IOException, URISyntaxException {
     
         Pattern p = Pattern.compile("\\@\\w+");
         String a = "@this.!$%^|+ The @that Swearing-in... http://t.co/ze…###na$$$Fri Feb 13 17:54:54 IST 2015###Honda Point, Bagadpatti Road, Tarakpur, Ahmednagar, Maharashtra 414001, India###19.0991951###74.7335744###RT @amitgupta75: @TrafflineDEL Heavy traffic on NH-8 from Hero Honda Chowk to Exit 16. Traffic moving very slowly. Avoid if possible.###na$$$Fri Feb 13 18:11:11 IST 2015###New Delhi, Delhi, India###28.6139391###77.2090212###RT @dtptraffic: TRAFFIC ADVISORY\n" ;
         a = mentions(a);
         a = removechars(a);
          System.out.println(a);
     
               
    }
    public static String mentions(String message){
        Pattern p = Pattern.compile("\\@\\w+");
        Matcher m = p.matcher(message);
        while(m.find()){
            //System.out.println(m.group());
             message =  message.replaceAll(m.group(), "");
         }
        return message;
    
    }
    public static String removechars(String message){
        message =  message.replaceAll("\\.","");
        message = message.replaceAll("\\!","");
        message = message.replaceAll("\\$","");
        message = message.replaceAll("\\%","");
        message = message.replaceAll("\\^","");
        message = message.replaceAll("\\|","");
        message = message.replaceAll("\\+","");
    
        return message; 
    }
    
}
