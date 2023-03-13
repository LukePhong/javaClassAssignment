package networktest;
import java.net.*;
import java.io.*;
public class kehuduan {
	public static void main(String[] args) throws Exception {
		System.out.println("客户端启动");
		Socket s=new Socket("localhost",8848);
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
				System.out.println(line+"客户端发送");
			}
		
		
		
		
		
		
	}

}
