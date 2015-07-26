/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verification;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Server
 */
public class TrafficDataDelhiVerification {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\twitterData\\Mumbai\\traffic_data_mumbai_verification.txt"));
        String str = "";
        int count_misclassified = 0;
        int count_total = 0;
        while ((str = br.readLine()) != null) {
            if (str.contains(",")) {
                String comma_splitter[] = str.split(",");
                String _splitter[] = comma_splitter[1].split("\\-");
                String google_state = _splitter[0].trim().toLowerCase();
                String sentiment_state = _splitter[_splitter.length - 1].trim().toLowerCase();
                count_total++;
                if ((google_state.equals("heavy") && sentiment_state.equals("moderate")) || (google_state.equals("heavy") && sentiment_state.equals("fast")) || (google_state.equals("medium") && sentiment_state.equals("fast"))) {
                    count_misclassified++;
                }
            }
        }
        int accuracy = (count_total-count_misclassified)*100/count_total;
        System.out.println("Classification Accuracy = "+accuracy+"%");
    }
}
