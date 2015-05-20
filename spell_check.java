import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.* ;
import java.io.* ;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource; 
public class abc
{
		public static void main( String args[] ) throws Exception{
		try{
						String url = "https://languagetool.org:8081/?language=en-US&text=" + "corect" ;
     
                        URI uri = new URI(url);
                        URL page = new URL(uri.toString());
                        StringBuffer text = new StringBuffer();
                        HttpURLConnection conn = (HttpURLConnection) page.openConnection();
                        conn.connect();
                        InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                        BufferedReader buff = new BufferedReader(in);
                        String st1 = buff.readLine();
                        while (st1 != null) {
                            text.append(st1);
                            st1 = buff.readLine();
                        }
                        String data = text.toString();
                        System.out.println(data);
						if(data.contains("MORFOLOGIK_RULE_EN_US")){
							Pattern p = Pattern.compile("(replacements=\"([^\"]*)\")");
							Matcher m  = p.matcher(data);
							m.find();
							m.find();
							System.out.println(m.group());
							Pattern p1 = Pattern.compile("\"(.*?)#");
							Matcher m1  = p1.matcher(m.group());
							//m1.find();
							m1.find();
							String ans = m1.group().substring(1, m1.group().length()-1);
						    System.out.println(ans);
					
						}
						else{
						    System.out.println("lalalala");
									
						}
    		}		
    	catch(Exception e){
			System.out.println(e);
		}	

	
	}
		
}


