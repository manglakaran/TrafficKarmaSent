import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.* ;
import java.io.* ; 
public class RegexMatches
{
		public static void main( String args[] ) throws IOException{
				//checking URLs	
				String commentstr = "###Delhi Police: Traffic police tighten noose on criminals: Cr... http://t.co/gstXeyTM31 | https://t.co/apGmzF9vUw h 😔	😖 😮 😱 😱 ";
				System.out.println(commentstr);
				String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
				Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
				//Pattern p = Pattern.compile("[\\uD83D\\uDE01-\\uD83D\\uDE4F]");
				Matcher m = p.matcher(commentstr);
				int i = 0;
				while (m.find()) {
						commentstr = commentstr.replaceAll(m.group(i),"").trim();
						i++;
				}
				System.out.println( commentstr);

				Pattern emo = Pattern.compile("[\\uD83D\\uDE01-\\uD83D\\uDE4F]");
				List<String> list = new ArrayList<String>();
				Matcher m_emo = emo.matcher(commentstr);
				while (m_emo.find()) {
						list.add(m_emo.group());
				}
				System.out.println(list);
				Set<String> unique = new HashSet<String>(list);
				for (String key : unique) {
						System.out.println(key + ": " + Collections.frequency(list, key));
				}
				System.out.println(unique);

				//checking acronyms
				Map<String, String> map = new HashMap<String, String>();
				BufferedReader in = new BufferedReader(new FileReader("out.txt"));
				String line = "";
				while ((line = in.readLine()) != null) {
						String parts[] = line.split("\t");
						map.put(parts[0], parts[1]);
				}
				in.close();

				String sentence = "RT @toi_BhartiJ: A hoarding at a traffic signal in Delhi's Greater Kailash area 😉 ";
				String[] words = sentence.split(" ");
				List<String> checked = new ArrayList<String>();
				for (String word : words)
				{
						if(map.containsKey(word.toUpperCase())) {

								checked.add(map.get(word.toUpperCase()));
						} else {
								checked.add(word);
						}


				}

				String[] myArray = new String[checked.size()];
				checked.toArray(myArray);

				//checking HTML entities

				BufferedReader htm_in = new BufferedReader(new FileReader("html_ent.txt"));
				List<String> a = new ArrayList<String>();	
				List<String> flist = new ArrayList<String>();	
				while ((line = htm_in.readLine()) != null) {
						a.add(line);
				}
				for(String word : myArray) {
						if(!a.contains(word)){
								flist.add(word);
						}

				}
				//		System.out.println(flist);

				// checking emoji's

		}

}	

