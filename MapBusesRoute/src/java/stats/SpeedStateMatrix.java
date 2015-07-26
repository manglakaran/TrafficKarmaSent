/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dibyendu1363
 */
public class SpeedStateMatrix {

    public static void main(String args[]) throws FileNotFoundException, IOException, ParseException {

        // time slots
        ArrayList<String> time_slots = new ArrayList<String>();
        String myTime = "00:00:00";
        for (int t = 0; t < 24; t++) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.SECOND, 3599);
            String newTime = df.format(cal.getTime());
            time_slots.add(myTime + "-" + newTime);
            d = df.parse(newTime);
            cal.setTime(d);
            cal.add(Calendar.SECOND, 1);
            myTime = df.format(cal.getTime());
        }

        // points all
        ArrayList<String> points = new ArrayList<String>();
        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\mumbai_points_82_routeids.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        String line1;
        while ((line1 = br1.readLine()) != null) {
            String parts[] = line1.split("-");
            points.add(parts[1]);
        }


        // stats finding from data
        double speeds[][] = new double[points.size()][24];
        int count[][] = new int[points.size()][24];
        String speed_state[][] = new String[points.size()][24];
        FileReader fr = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\mumbai_data_google_82_routeids.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String parts[] = line.split("--");
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).equals(parts[3])) {
                    String space[] = parts[0].split("\\s\\s");
                    DateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                    Date time_got = sdf1.parse(space[1]);
                    for (int j = 0; j < time_slots.size(); j++) {
                        String splitter[] = time_slots.get(j).split("-");
                        DateFormat sdf11 = new SimpleDateFormat("HH:mm:ss");
                        Date slot_first = sdf11.parse(splitter[0]);
                        DateFormat sdf12 = new SimpleDateFormat("HH:mm:ss");
                        Date slot_end = sdf12.parse(splitter[1]);
                        if (slot_first.before(time_got) && slot_end.after(time_got)) {
                            speeds[i][j] = speeds[i][j] + Double.parseDouble(parts[6]);
                            count[i][j] = count[i][j] + 1;
                        } else {
                            if (slot_first.toString().equals(time_got.toString()) && slot_end.toString().equals(time_got.toString())) {
                                speeds[i][j] = speeds[i][j] + Double.parseDouble(parts[6]);
                                count[i][j] = count[i][j] + 1;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < time_slots.size(); j++) {
                double final_speed = speeds[i][j] / count[i][j];
                String final_state = "";
                String final_color = "";
//                System.out.println(speeds[i][j]);
//                System.out.println(count[i][j]);
//                System.out.println(final_speed);
//                System.out.println(speed_traffic);
//                System.exit(0);
                if (final_speed > 50.0) {
                    final_state = "Mild";
                    final_color = "#00ff00";
                } else if (final_speed <= 50 && final_speed > 25) {
                    final_state = "Medium";
                    final_color = "#ffff00";
                } else {
                    final_state = "Heavy";
                    final_color = "#ff0000";
                }
                speed_state[i][j] = final_state + "," + final_color;
            }
        }

        for (int i = 0; i < points.size(); i++) {
            System.out.print(points.get(i) + "\t");
            for (int j = 0; j < time_slots.size(); j++) {
                System.out.print(speed_state[i][j] + "\t");
            }
            System.out.println();
        }
    }
}