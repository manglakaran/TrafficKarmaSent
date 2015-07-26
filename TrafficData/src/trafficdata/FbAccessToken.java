package trafficdata;
import com.restfb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.*;

public class FbAccessToken
{
	public static void main(String[] args)throws IOException
	{
		FileWriter fw=new FileWriter("C:\\users\\server\\extendedtoken.txt");
		AccessToken accessToken =new DefaultFacebookClient().obtainExtendedAccessToken("850511448302448","4b14c19b3035a3deb91e893849b1c42f", "CAAIZBKrbTB9IBANEAt3H01tLO1wk6IMvcGlAKZBVmBgcjHqV1UF1lHhVVi34YS477jwEqdKgcaJLQ8CPjVZAVCeQVEbJ4iOqzRFlQOvkBNMoEjGAQdpeN0JdRZCqLhR22N4GNJtW1JkrDSZAM2dZBuoTN9pYU8Yo0pSnwFqQkN12Tum1JlZApydhgK1RD0M0X4ZD");
		System.out.println("My extended access token: " + accessToken);
		fw.write("\n"+accessToken);
		fw.close();

	}
}