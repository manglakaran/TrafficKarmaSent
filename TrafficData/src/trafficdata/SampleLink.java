import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class SampleLink {
	public static void main(String[] args) {
		try {
			while (true) {
				String address = "almora,india";
				String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
						+ URLEncoder.encode(address)
						+ "&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
				URI uri = new URI(url);
				URL page = new URL(uri.toString());
				StringBuffer text = new StringBuffer();
				HttpURLConnection conn = (HttpURLConnection) page
						.openConnection();
				conn.connect();
				InputStreamReader in = new InputStreamReader(
						(InputStream) conn.getContent());
				BufferedReader buff = new BufferedReader(in);
				String st1 = buff.readLine();
				while (st1 != null) {
					text.append(st1);
					st1 = buff.readLine();
				}
				String data = text.toString();
				System.out.println(data);
				Thread.sleep(1000*100);
			}
		} catch (Exception ex) {
			System.out.println();
		}

	}

}
