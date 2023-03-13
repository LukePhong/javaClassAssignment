package networktest;
import java.net.*;
import java.io.*;
public class network {
public static void main(String[] args) throws Exception {
	 ServerSocket ss=new ServerSocket(8848);
	 
Socket s=ss.accept();
final BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
PrintWriter out=new PrintWriter(s.getOutputStream(),true);
new Thread() 
{public void run() {
	
	while(true)
	{
		
		try {
			String line= in.readLine();
			System.out.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
};
	}.start();;

	
	BufferedReader key=new BufferedReader(new InputStreamReader(System.in));
	
	while (true)
	{
		String line=key.readLine();
		out.println(line);
	}
	
	
	
	
	
	
	
	
	
	
}
}
