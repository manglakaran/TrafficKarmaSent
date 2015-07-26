/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trafficdata;


import com.restfb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.*;

public class ExtendToken
{
	public static void main(String[] args)throws IOException
	{
		FileWriter fw=new FileWriter("c:\\users\\server\\extendedtoken.txt");
		AccessToken accessToken =new DefaultFacebookClient().obtainExtendedAccessToken("850511448302448","4b14c19b3035a3deb91e893849b1c42f", "CAAMFiSDA43ABAE91WPghwFTQDzZAshMiOD3vn4tmNKu1LFzeTd4Rw8IGjhKKR5F1kDttHQdP5ZCOcyoYI7dR6xp4eJEXTgOXPNBEXGdc5hvkKopISNDDKGF7xyGs6AKk47kEXtpnHeKp0W0SJGGZB5VawQRwPJPIFpd3q7Y9EPMVAi6AkvZC7H1eEIOKrVUFk0F0TZBSvdO34vLSVWeYrcwap42q7ZCeQZD");
		System.out.println("My extended access token: " + accessToken);
		fw.write("\n"+accessToken);
		fw.close();

	}
}
