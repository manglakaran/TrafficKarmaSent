/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kanu
 */
public class test {

    private static final String DBDriver = "com.mysql.jdbc.Driver";
    private static final String DBUrl = "jdbc:mysql://localhost/traffickramasenti";
    private static final String DBUser = "root";
    private static final String DBPasswd = "admin";

    static {
        try {
            Class.forName(DBDriver);
        } catch (Exception ex) {
            System.out.println("error in connecton");
        }
    }

    private static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPasswd);
        return con;
    }

    public static void main(String[] args) {
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement("select text from traffictweets");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String data = rs.getString(1);
                //if(data.endsWith("..."))
                //System.out.println(data);
                String urlPattern = "\\u2026";
                Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(data);
                int i = 0;
                while (m.find()) {
                    //System.out.println(m.group()+"lalalal");
                    data = data.replaceAll(m.group(i), "").trim();
                   System.out.println(data + "\n");
                }
                //System.out.println(data + "\n");

            }

        } catch (Exception ex) {
            System.out.println("Exception in data fetching");
        }

    }

}