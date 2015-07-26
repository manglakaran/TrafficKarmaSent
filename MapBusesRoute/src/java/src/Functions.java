/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Dibyendu1363
 */
public class Functions {

    public  String getMean(String st) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }



        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        double sum1 = 0;
        int count = 0;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");

            if (parts2[0].equals(first) && parts2[1].equals(end)) {
                sum = sum + Double.parseDouble(parts1[8]);
                sum1 = sum1 + Double.parseDouble(parts1[7]);
                count++;
            }
        }

        double speed_mean = sum / count;
        double per = sum / sum1 * 100;

        String state = "";

        if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }

        String result = speed_mean + " Km/Hr -> " + state;

        return result;

    }

    public  String getMedian(String st) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        ArrayList<String> speeds_main = new ArrayList<String>();
        double speed_limit = 0;

        ArrayList<String> all = new ArrayList<String>();

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");
            if (parts2[0].equals(first) && parts2[1].equals(end)) {
                all.add(parts1[7] + " # " + parts1[8] + " # " + parts1[9]);
            }
        }

        for (int i = 0; i < all.size(); i++) {
            String s = all.get(i);
            String prts[] = s.split("#");
            speeds_main.add(prts[1].trim());
        }

        Collections.sort(speeds_main);
        double speed_median_main = Double.parseDouble(speeds_main.get((speeds_main.size() + 1) / 2 - 1));
        String state = "";

        for (int k = 0; k < all.size(); k++) {
            String s = all.get(k);
            String prts[] = s.split("#");
            if (prts[1].trim().equals(speed_median_main + "")) {
                state = prts[2];
                speed_limit = Double.parseDouble(prts[0]);
            }
        }

        if (state.equals("None")) {
            double per = speed_median_main / speed_limit * 100;
            if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }
        }

        String result = speed_median_main + " Km/Hr -> " + state;

        return result;

    }

    public  String getVariance(String st) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        int count = 0;
        ArrayList<Double> speeds = new ArrayList<Double>();

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");

            if (parts2[0].equals(first) && parts2[1].equals(end)) {
                speeds.add(Double.parseDouble(parts1[8]));
                sum = sum + Double.parseDouble(parts1[8]);
                count++;
            }
        }

        double speed_mean = sum / count;
        double sum1 = 0;

        for (int i = 0; i < speeds.size(); i++) {
            sum1 = sum1 + Math.pow(speeds.get(i) - speed_mean, 2);
        }

        double speed_variance = sum1 / speeds.size();

        String result = speed_variance + " SqKm/Hr";

        return result;

    }

    public  String getSD(String st) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        int count = 0;
        ArrayList<Double> speeds = new ArrayList<Double>();

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");

            if (parts2[0].equals(first) && parts2[1].equals(end)) {
                speeds.add(Double.parseDouble(parts1[8]));
                sum = sum + Double.parseDouble(parts1[8]);
                count++;
            }
        }

        double speed_mean = sum / count;
        double sum1 = 0;

        for (int i = 0; i < speeds.size(); i++) {
            sum1 = sum1 + Math.pow(speeds.get(i) - speed_mean, 2);
        }

        double speed_SD = Math.sqrt(sum1 / speeds.size());

        String result = speed_SD + " Km/Hr";

        return result;
    }

    public  String getMeanDay(String st, String dt) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String ss = "";
        int flag = 0;
        for (int i = 0; i < dt.length(); i++) {
            if (Character.isDigit(dt.charAt(i))) {
                ss = ss.concat(dt.charAt(i) + "");
                flag = 1;
            }
            if (flag == 1 && !Character.isDigit(dt.charAt(i))) {
                break;
            }
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        double sum1 = 0;
        int count = 0;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");
            String parts3[] = parts[0].split("/");

            if (parts2[0].equals(first) && parts2[1].equals(end) && parts3[0].equals(ss)) {
                sum = sum + Double.parseDouble(parts1[8]);
                sum1 = sum1 + Double.parseDouble(parts1[7]);
                count++;
            }
        }

        double speed_mean_day = sum / count;
        double per = sum / sum1 * 100;

        String state = "";

        if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }

        String result = speed_mean_day + " Km/Hr -> " + state;

        return result;

    }

    public  String getMedianDay(String st, String dt) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String ss = "";
        int flag = 0;
        for (int i = 0; i < dt.length(); i++) {
            if (Character.isDigit(dt.charAt(i))) {
                ss = ss.concat(dt.charAt(i) + "");
                flag = 1;
            }
            if (flag == 1 && !Character.isDigit(dt.charAt(i))) {
                break;
            }
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        ArrayList<String> all = new ArrayList<String>();
        ArrayList<String> speeds_main = new ArrayList<String>();
        double speed_limit = 0;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");
            String parts3[] = parts[0].split("/");

            if (parts2[0].equals(first) && parts2[1].equals(end) && parts3[0].equals(ss)) {
                all.add(parts1[7] + " # " + parts1[8] + " # " + parts1[9]);
            }
        }

        for (int i = 0; i < all.size(); i++) {
            String s = all.get(i);
            String prts[] = s.split("#");
            speeds_main.add(prts[1].trim());
        }

        Collections.sort(speeds_main);
        double speed_median_day_main = Double.parseDouble(speeds_main.get((speeds_main.size() + 1) / 2 - 1));
        String state = "";

        for (int k = 0; k < all.size(); k++) {
            String s = all.get(k);
            String prts[] = s.split("#");
            if (prts[1].trim().equals(speed_median_day_main + "")) {
                state = prts[2];
                speed_limit = Double.parseDouble(prts[0]);
            }
        }

        if (state.equals("None")) {
            double per = speed_median_day_main / speed_limit * 100;
            if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }
        }

        String result = speed_median_day_main + " Km/Hr -> " + state;

        return result;

    }

    public  String getVarianceDay(String st, String dt) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String ss = "";
        int flag = 0;
        for (int i = 0; i < dt.length(); i++) {
            if (Character.isDigit(dt.charAt(i))) {
                ss = ss.concat(dt.charAt(i) + "");
                flag = 1;
            }
            if (flag == 1 && !Character.isDigit(dt.charAt(i))) {
                break;
            }
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        int count = 0;
        ArrayList<Double> speeds = new ArrayList<Double>();

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");
            String parts3[] = parts[0].split("/");

            if (parts2[0].equals(first) && parts2[1].equals(end) && parts3[0].equals(ss)) {
                speeds.add(Double.parseDouble(parts1[8]));
                sum = sum + Double.parseDouble(parts1[8]);
                count++;
            }
        }

        double speed_mean_day = sum / count;
        double sum1 = 0;

        for (int i = 0; i < speeds.size(); i++) {
            sum1 = sum1 + Math.pow(speeds.get(i) - speed_mean_day, 2);
        }

        double speed_variance_day = sum1 / speeds.size();

        String result = speed_variance_day + " SqKm/Hr";

        return result;

    }

    public  String getSDDay(String st, String dt) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String ss = "";
        int flag = 0;
        for (int i = 0; i < dt.length(); i++) {
            if (Character.isDigit(dt.charAt(i))) {
                ss = ss.concat(dt.charAt(i) + "");
                flag = 1;
            }
            if (flag == 1 && !Character.isDigit(dt.charAt(i))) {
                break;
            }
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;
        double sum = 0;
        int count = 0;
        ArrayList<Double> speeds = new ArrayList<Double>();

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            String parts1[] = parts[1].split("--");
            String parts2[] = parts1[1].split("_");
            String parts3[] = parts[0].split("/");

            if (parts2[0].equals(first) && parts2[1].equals(end) && parts3[0].equals(ss)) {
                speeds.add(Double.parseDouble(parts1[8]));
                sum = sum + Double.parseDouble(parts1[8]);
                count++;
            }
        }

        double speed_mean_day = sum / count;
        double sum1 = 0;

        for (int i = 0; i < speeds.size(); i++) {
            sum1 = sum1 + Math.pow(speeds.get(i) - speed_mean_day, 2);
        }

        double speed_SD_day = Math.sqrt(sum1 / speeds.size());

        String result = speed_SD_day + " Km/Hr";

        return result;

    }

    public  String getMeanTime(String st, String tm) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        int val = 0;
        if (tm.contains("pm") | tm.contains("p.m") | tm.contains("p.m.")) {
            val = 12;
            tm = tm.replace("p", "");
            tm = tm.replace("m", "");
            tm = tm.replace(".", "");
            tm = tm.replace(" ", "");
        } else {
            if (tm.contains("a")) {
                tm = tm.replace("a", "");
            }
            if (tm.contains("m")) {
                tm = tm.replace("m", "");
            }
            tm = tm.replace(" ", "");
            tm = tm.replace(".", "");
        }

        String query;
        if (tm.contains(":")) {
            String prt[] = tm.split(":");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else if (tm.contains(" ")) {
            String prt[] = tm.split(" ");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else {
            int fnl = Integer.parseInt(tm) + val;
            query = fnl + ":" + "00";
        }


        ArrayList<String> dates = new ArrayList<String>();

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            if (!dates.contains(parts[0])) {
                dates.add(parts[0]);
            }
        }


        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        br1.readLine();
        String str1;

        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
        for (String base : dates) {
            hm.put(base, new ArrayList<String>());
        }
        
        while ((str1 = br1.readLine()) != null) {
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String s = pair.getKey() + "";
                String oo[] = str1.split(" ");
                String ooo[] = oo[1].split("--");
                if (s.equals(oo[0]) && ooo[1].equals(first + "_" + end)) {
                    String sp = ooo[0] + "#" + ooo[8] + "#" + ooo[7];
                    ArrayList temp = hm.get(pair.getKey());
                    temp.add(sp);
                }
            }
        }
        
        //System.out.println(hm);

        ArrayList<Double> speed_main = new ArrayList<Double>();
        HashMap<String, String> hm1 = new HashMap<String, String>();

        Iterator i = hm.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            ArrayList arr = hm.get(pair.getKey());
            ArrayList<String> entries_per_day = new ArrayList<String>();
            ArrayList<Integer> gaps = new ArrayList<Integer>();
            ArrayList<String> speeds = new ArrayList<String>();
            for (int p = 0; p < arr.size(); p++) {
                String ll = arr.get(p) + "";
                String l[] = ll.split("#");
                speed_main.add(Double.parseDouble(l[2]));
                String p1[] = l[0].split(":");
                int g = Integer.parseInt(p1[0]) * 3600 + Integer.parseInt(p1[1]) * 60 + Integer.parseInt(p1[2]);
                String pa[] = query.split(":");
                int c = Integer.parseInt(pa[0]) * 3600 + Integer.parseInt(pa[1]) * 60;
                int gap;
                if (c > g) {
                    gap = c - g;
                } else {
                    gap = g - c;
                }
                entries_per_day.add(l[0]);
                gaps.add(gap);
                speeds.add(l[1]);
            }
            String closest_time = entries_per_day.get(gaps.indexOf(Collections.min(gaps)));
            String closest_time_speed = speeds.get(gaps.indexOf(Collections.min(gaps))) + "";
            String closest_time_speed_main = speed_main.get(gaps.indexOf(Collections.min(gaps))) + "";
            hm1.put(pair.getKey() + "", closest_time + "#" + closest_time_speed + "#" + closest_time_speed_main);
        }
        
        //System.out.println(hm1);

        double sum = 0;
        double sum1 = 0;
        
        Iterator ii = hm1.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry pair = (Map.Entry) ii.next();
            String h = hm1.get(pair.getKey());
            String lol[] = h.split("#");
            sum = sum + Double.parseDouble(lol[1]);
            sum1 = sum1 + Double.parseDouble(lol[2]);
        }

        double speed_mean_time = sum / hm1.size();

        double per = sum / sum1 * 100;

        String state = "";

        if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }

        String result = speed_mean_time + " Km/Hr -> " + state;

        return result;

    }

    public  String getMedianTime(String st, String tm) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        int val = 0;
        if (tm.contains("pm") | tm.contains("p.m") | tm.contains("p.m.")) {
            val = 12;
            tm = tm.replace("p", "");
            tm = tm.replace("m", "");
            tm = tm.replace(".", "");
            tm = tm.replace(" ", "");
        } else {
            if (tm.contains("a")) {
                tm = tm.replace("a", "");
            }
            if (tm.contains("m")) {
                tm = tm.replace("m", "");
            }
            tm = tm.replace(" ", "");
            tm = tm.replace(".", "");
        }

        String query;
        if (tm.contains(":")) {
            String prt[] = tm.split(":");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else if (tm.contains(" ")) {
            String prt[] = tm.split(" ");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else {
            int fnl = Integer.parseInt(tm) + val;
            query = fnl + ":" + "00";
        }


        ArrayList<String> dates = new ArrayList<String>();

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            if (!dates.contains(parts[0])) {
                dates.add(parts[0]);
            }
        }


        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        br1.readLine();
        String str1;

        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
        for (String base : dates) {
            hm.put(base, new ArrayList<String>());
        }

        String speed_limit = "";
        while ((str1 = br1.readLine()) != null) {
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String s = pair.getKey() + "";
                String oo[] = str1.split(" ");
                String ooo[] = oo[1].split("--");
                if (s.equals(oo[0]) && ooo[1].equals(first + "_" + end)) {
                    String sp = ooo[0] + "#" + ooo[8] + "#" + ooo[9];
                    speed_limit = ooo[7];
                    ArrayList temp = hm.get(pair.getKey());
                    temp.add(sp);
                }
            }
        }

        HashMap<String, String> hm1 = new HashMap<String, String>();

        Iterator i = hm.entrySet().iterator();

        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            ArrayList arr = hm.get(pair.getKey());
            ArrayList<Integer> gaps = new ArrayList<Integer>();
            ArrayList<String> speeds = new ArrayList<String>();
            ArrayList<String> states = new ArrayList<String>();

            for (int p = 0; p < arr.size(); p++) {
                String ll = arr.get(p) + "";
                String l[] = ll.split("#");
                String p1[] = l[0].split(":");
                int g = Integer.parseInt(p1[0]) * 3600 + Integer.parseInt(p1[1]) * 60 + Integer.parseInt(p1[2]);
                String pa[] = query.split(":");
                int c = Integer.parseInt(pa[0]) * 3600 + Integer.parseInt(pa[1]) * 60;
                int gap;
                if (c > g) {
                    gap = c - g;
                } else {
                    gap = g - c;
                }
                gaps.add(gap);
                speeds.add(l[1]);
                states.add(l[2]);
            }

            String closest_time_speed = speeds.get(gaps.indexOf(Collections.min(gaps))) + "";
            String closest_time_state = states.get(gaps.indexOf(Collections.min(gaps))) + "";

            hm1.put(pair.getKey() + "", closest_time_speed + "#" + closest_time_state);
        }

        ArrayList<String> a1 = new ArrayList<String>();

        Iterator uu = hm1.entrySet().iterator();
        while (uu.hasNext()) {
            Map.Entry pair = (Map.Entry) uu.next();
            String sty = hm1.get(pair.getKey()) + "";
            String pl[] = sty.split("#");
            a1.add(pl[0]);
        }

        Collections.sort(a1);

        String speed_median_time = a1.get((a1.size() + 1) / 2 - 1);

        String state = "";
        Iterator u1 = hm1.entrySet().iterator();
        while (u1.hasNext()) {
            Map.Entry pair = (Map.Entry) u1.next();
            String sty = hm1.get(pair.getKey()) + "";
            String pl[] = sty.split("#");

            if (speed_median_time.equals(pl[0])) {
                state = pl[1];
            }
        }

        if (state.equals("None")) {
            double per = Double.parseDouble(speed_median_time) / Double.parseDouble(speed_limit) * 100;
            if (per >= 80.02) {
                state = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                state = "Medium";
            } else {
                state = "Heavy";
            }
        }
        
        String result = speed_median_time + " Km/Hr -> " + state;

        return result;

    }

    public  String getVarianceTime(String st, String tm) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        int val = 0;
        if (tm.contains("pm") | tm.contains("p.m") | tm.contains("p.m.")) {
            val = 12;
            tm = tm.replace("p", "");
            tm = tm.replace("m", "");
            tm = tm.replace(".", "");
            tm = tm.replace(" ", "");
        } else {
            if (tm.contains("a")) {
                tm = tm.replace("a", "");
            }
            if (tm.contains("m")) {
                tm = tm.replace("m", "");
            }
            tm = tm.replace(" ", "");
            tm = tm.replace(".", "");
        }

        String query;
        if (tm.contains(":")) {
            String prt[] = tm.split(":");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else if (tm.contains(" ")) {
            String prt[] = tm.split(" ");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else {
            int fnl = Integer.parseInt(tm) + val;
            query = fnl + ":" + "00";
        }


        ArrayList<String> dates = new ArrayList<String>();

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            if (!dates.contains(parts[0])) {
                dates.add(parts[0]);
            }
        }


        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        br1.readLine();
        String str1;

        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
        for (String base : dates) {
            hm.put(base, new ArrayList<String>());
        }

        while ((str1 = br1.readLine()) != null) {
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String s = pair.getKey() + "";
                String oo[] = str1.split(" ");
                String ooo[] = oo[1].split("--");
                if (s.equals(oo[0]) && ooo[1].equals(first + "_" + end)) {
                    String sp = ooo[0] + "#" + ooo[8];
                    ArrayList temp = hm.get(pair.getKey());
                    temp.add(sp);
                }
            }
        }

        HashMap<String, String> hm1 = new HashMap<String, String>();

        Iterator i = hm.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            ArrayList arr = hm.get(pair.getKey());
            ArrayList<String> entries_per_day = new ArrayList<String>();
            ArrayList<Integer> gaps = new ArrayList<Integer>();
            ArrayList<String> speeds = new ArrayList<String>();
            for (int p = 0; p < arr.size(); p++) {
                String ll = arr.get(p) + "";
                String l[] = ll.split("#");
                String p1[] = l[0].split(":");
                int g = Integer.parseInt(p1[0]) * 3600 + Integer.parseInt(p1[1]) * 60 + Integer.parseInt(p1[2]);
                String pa[] = query.split(":");
                int c = Integer.parseInt(pa[0]) * 3600 + Integer.parseInt(pa[1]) * 60;
                int gap;
                if (c > g) {
                    gap = c - g;
                } else {
                    gap = g - c;
                }
                entries_per_day.add(l[0]);
                gaps.add(gap);
                speeds.add(l[1]);
            }
            String closest_time = entries_per_day.get(gaps.indexOf(Collections.min(gaps)));
            String closest_time_speed = speeds.get(gaps.indexOf(Collections.min(gaps))) + "";
            hm1.put(pair.getKey() + "", closest_time + "#" + closest_time_speed);
        }

        ArrayList<Double> lp = new ArrayList<Double>();

        double sum = 0;
        Iterator ii = hm1.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry pair = (Map.Entry) ii.next();
            String h = hm1.get(pair.getKey());
            String lol[] = h.split("#");
            lp.add(Double.parseDouble(lol[1]));
            sum = sum + Double.parseDouble(lol[1]);
        }

        double speed_mean_time = sum / hm1.size();
        double sm = 0;

        for (int y = 0; y < lp.size(); y++) {
            sm = sm + Math.pow(lp.get(y) - speed_mean_time, 2);
        }

        double speed_variance_time = sm / lp.size();

        String result = speed_variance_time + " SqKm/Hr";

        return result;

    }

    public  String getSDTime(String st, String tm) throws FileNotFoundException, IOException {

        String first, end;

        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        int val = 0;
        if (tm.contains("pm") | tm.contains("p.m") | tm.contains("p.m.")) {
            val = 12;
            tm = tm.replace("p", "");
            tm = tm.replace("m", "");
            tm = tm.replace(".", "");
            tm = tm.replace(" ", "");
        } else {
            if (tm.contains("a")) {
                tm = tm.replace("a", "");
            }
            if (tm.contains("m")) {
                tm = tm.replace("m", "");
            }
            tm = tm.replace(" ", "");
            tm = tm.replace(".", "");
        }

        String query;
        if (tm.contains(":")) {
            String prt[] = tm.split(":");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else if (tm.contains(" ")) {
            String prt[] = tm.split(" ");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else {
            int fnl = Integer.parseInt(tm) + val;
            query = fnl + ":" + "00";
        }


        ArrayList<String> dates = new ArrayList<String>();

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        while ((str = br.readLine()) != null) {
            String parts[] = str.split(" ");
            if (!dates.contains(parts[0])) {
                dates.add(parts[0]);
            }
        }


        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        br1.readLine();
        String str1;

        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
        for (String base : dates) {
            hm.put(base, new ArrayList<String>());
        }

        while ((str1 = br1.readLine()) != null) {
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String s = pair.getKey() + "";
                String oo[] = str1.split(" ");
                String ooo[] = oo[1].split("--");
                if (s.equals(oo[0]) && ooo[1].equals(first + "_" + end)) {
                    String sp = ooo[0] + "#" + ooo[8];
                    ArrayList temp = hm.get(pair.getKey());
                    temp.add(sp);
                }
            }
        }

        HashMap<String, String> hm1 = new HashMap<String, String>();

        Iterator i = hm.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            ArrayList arr = hm.get(pair.getKey());
            ArrayList<String> entries_per_day = new ArrayList<String>();
            ArrayList<Integer> gaps = new ArrayList<Integer>();
            ArrayList<String> speeds = new ArrayList<String>();
            for (int p = 0; p < arr.size(); p++) {
                String ll = arr.get(p) + "";
                String l[] = ll.split("#");
                String p1[] = l[0].split(":");
                int g = Integer.parseInt(p1[0]) * 3600 + Integer.parseInt(p1[1]) * 60 + Integer.parseInt(p1[2]);
                String pa[] = query.split(":");
                int c = Integer.parseInt(pa[0]) * 3600 + Integer.parseInt(pa[1]) * 60;
                int gap;
                if (c > g) {
                    gap = c - g;
                } else {
                    gap = g - c;
                }
                entries_per_day.add(l[0]);
                gaps.add(gap);
                speeds.add(l[1]);
            }
            String closest_time = entries_per_day.get(gaps.indexOf(Collections.min(gaps)));
            String closest_time_speed = speeds.get(gaps.indexOf(Collections.min(gaps))) + "";
            hm1.put(pair.getKey() + "", closest_time + "#" + closest_time_speed);
        }

        ArrayList<Double> lp = new ArrayList<Double>();

        double sum = 0;
        Iterator ii = hm1.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry pair = (Map.Entry) ii.next();
            String h = hm1.get(pair.getKey());
            String lol[] = h.split("#");
            lp.add(Double.parseDouble(lol[1]));
            sum = sum + Double.parseDouble(lol[1]);
        }

        double speed_mean_time = sum / hm1.size();
        double sm = 0;

        for (int y = 0; y < lp.size(); y++) {
            sm = sm + Math.pow(lp.get(y) - speed_mean_time, 2);
        }

        double speed_SD_time = Math.sqrt(sm / lp.size());

        String result = speed_SD_time + " Km/Hr";

        return result;

    }

    public  String getState(String st, String dt, String tm) throws FileNotFoundException, IOException {

        String first, end;
        if (st.contains(":")) {
            String pp[] = st.split(":");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("-")) {
            String pp[] = st.split("-");
            first = pp[0];
            end = pp[1];
        } else if (st.contains("_")) {
            String pp[] = st.split("_");
            first = pp[0];
            end = pp[1];
        } else {
            String pp[] = st.split(" ");
            first = pp[0];
            end = pp[1];
        }

        String ss = "";
        int flag = 0;
        for (int i = 0; i < dt.length(); i++) {
            if (Character.isDigit(dt.charAt(i))) {
                ss = ss.concat(dt.charAt(i) + "");
                flag = 1;
            }
            if (flag == 1 && !Character.isDigit(dt.charAt(i))) {
                break;
            }
        }

        String query;
        int val = 0;
        if (tm.contains("pm") | tm.contains("p.m") | tm.contains("p.m.")) {
            val = 12;
            tm = tm.replace("p", "");
            tm = tm.replace("m", "");
            tm = tm.replace(".", "");
            tm = tm.replace(" ", "");
        } else {
            if (tm.contains("a")) {
                tm = tm.replace("a", "");
            }
            if (tm.contains("m")) {
                tm = tm.replace("m", "");
            }
            tm = tm.replace(" ", "");
            tm = tm.replace(".", "");
        }
        if (tm.contains(":")) {
            String prt[] = tm.split(":");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else if (tm.contains(" ")) {
            String prt[] = tm.split(" ");
            String hr = prt[0];
            String mnt = prt[1];
            int fnl = Integer.parseInt(hr) + val;
            query = fnl + ":" + mnt;
        } else {
            int fnl = Integer.parseInt(tm) + val;
            query = fnl + ":" + "00";
        }

        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\final_data_bing.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String str;

        ArrayList<String> a1 = new ArrayList<String>();
        ArrayList<Integer> a2 = new ArrayList<Integer>();
        double speed_limit = 0;

        while ((str = br.readLine()) != null) {
            String p[] = str.split(" ");
            String pp[] = p[0].split("/");
            String ppp[] = p[1].split("--");
            if (pp[0].equals(ss) && ppp[1].equals(first + "_" + end)) {
                speed_limit = Double.parseDouble(ppp[7]);
                a1.add(ppp[0] + "#" + ppp[8] + "#" + ppp[9]);

                String p1[] = ppp[0].split(":");
                int g1 = Integer.parseInt(p1[0]) * 3600 + Integer.parseInt(p1[1]) * 60 + Integer.parseInt(p1[2]);
                String pa[] = query.split(":");
                int c = Integer.parseInt(pa[0]) * 3600 + Integer.parseInt(pa[1]) * 60;
                int gap;
                if (c > g1) {
                    gap = c - g1;
                } else {
                    gap = g1 - c;
                }
                a2.add(gap);
            }
        }

        ArrayList<Integer> a3 = new ArrayList<Integer>();
        for (int ii = 0; ii < a2.size(); ii++) {
            a3.add(a2.get(ii));
        }

        Collections.sort(a2);
        String fg[] = a1.get(a3.indexOf(a2.get(0))).split("#");

        if (fg[2].equals("None")) {
            double per = Double.parseDouble(fg[1]) / speed_limit * 100;
            if (per >= 80.02) {
                fg[2] = "Mild";
            } else if (per < 80.02 && per > 66.7) {
                fg[2] = "Medium";
            } else {
                fg[2] = "Heavy";
            }
        }
        
        String result = fg[1] + " Km/Hr -> " + fg[2];

        return result;

    }
}