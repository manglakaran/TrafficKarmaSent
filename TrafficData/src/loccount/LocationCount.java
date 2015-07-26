
package loccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LocationCount {
    private static final String DBDriver="com.mysql.jdbc.Driver";
	private static final String DBUrl = "jdbc:mysql://localhost/traffickarma";
	private static final String DBUser = "root";
	private static final String DBPasswd = "admin";
	
	   static {
        try {
            Class.forName(DBDriver);
        } catch (Exception ex) {
            System.out.println("error in connecton");
        }
    }
        private static Connection getConnection()throws Exception
	{
            Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPasswd);
            return con;
	}
    public static void main(String[] args) throws Exception
    {
        
        Connection con = getConnection();
        int locCount[][]=new int[18][9];
        for(int i=0;i<18;i++)
        {
            for(int j=0;j<9;j++)
            {
                locCount[i][j]=0;
            }
        }
        PreparedStatement sta = con.prepareStatement("Select * from trafficdata where source=?");
       
        sta.setString(1,"facebook");
        ResultSet rs = sta.executeQuery();
        
        while(rs.next())
        {
            String date=rs.getString(1);
            //System.out.println(date);
            String[] tokens=date.split(" ");
            if(tokens[2].equals("17")||tokens.equals("16")||tokens.equals("15"))
            {
                String name = rs.getString(2).toLowerCase();
                
                if(name.startsWith("rajouri"))
                {
                    System.out.println(date);
                    String time=tokens[3];
                    String[] parTokens=time.split(":");
                    for(int i=0;i<9;i++)
                    {
                        locCount[0][i]=locCount[0][i]+1;
                    }
                }
                if(name.startsWith("rajouri"))
                {
                    System.out.println(date);
                    String time=tokens[3];
                    String[] parTokens=time.split(":");
                    for(int i=0;i<9;i++)
                    {
                        locCount[0][i]=locCount[0][i]+1;
                    }
                }
                if(name.startsWith("mayapuri"))
                {
                    System.out.println(date);
                    String time=tokens[3];
                    String[] parTokens=time.split(":");
                    for(int i=0;i<9;i++)
                    {
                        locCount[1][i]=locCount[1][i]+1;
                        locCount[2][i]=locCount[1][i]+1;
                    }
                }
                if(name.startsWith("naraina"))
                {
                    System.out.println(date);
                    String time=tokens[3];
                    String[] parTokens=time.split(":");
                    for(int i=0;i<9;i++)
                    {
                        locCount[3][i]=locCount[3][i]+1;
                        locCount[4][i]=locCount[4][i]+1;
                        locCount[5][i]=locCount[5][i]+1;
                        locCount[6][i]=locCount[6][i]+1;
                        locCount[7][i]=locCount[7][i]+1;
                        
                    }
                }
                if(name.startsWith("dhaula"))
                {
                    System.out.println(date);
                    for(int i=0;i<9;i++)
                    {
                        locCount[8][i]=locCount[8][i]+1;
                                               
                    }
                }
                if(name.startsWith("moti"))
                {
                    System.out.println(date);
                    for(int i=0;i<9;i++)
                    {
                        locCount[9][i]=locCount[9][i]+1;
                        locCount[10][i]=locCount[10][i]+1;
                        locCount[11][i]=locCount[11][i]+1;
                                               
                    }
                }
                if(name.startsWith("africa"))
                {
                    System.out.println(date);
                    for(int i=0;i<9;i++)
                    {
                        locCount[12][i]=locCount[12][i]+1;
                        locCount[13][i]=locCount[13][i]+1;
                                               
                    }
                }
                if(name.startsWith("nauroji"))
                {
                    System.out.println(date);
                    for(int i=0;i<9;i++)
                    {
                        locCount[14][i]=locCount[14][i]+1;
                         
                    }
                }
                
                System.out.println(locCount[9][0]);
            }
            
        }
        
    }
}
