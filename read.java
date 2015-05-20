import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class read {
 
	public static void main(String[] args) {
 
		try (BufferedReader br = new BufferedReader(new FileReader("./data.txt")))
		{
 
			String sCurrentLine;
 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
	}
}
