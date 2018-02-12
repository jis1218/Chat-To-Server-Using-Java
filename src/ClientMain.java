import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientMain {
	public static final int PORT_NUM = 9090;
	public static void main(String args[]){
		
		ServerThread serverThread = new ServerThread();
		serverThread.start();
		
		Socket socket = null;
		OutputStream outputStream = null;
		Scanner in = null;
		BufferedReader bufferedReader = null;		
		
		try {		
			
			while(true){
				socket = new Socket("192.168.0.9", PORT_NUM); //서버와 연결시켜주는 코드라고 보면 된다.
				System.out.println("chatting 하십시오");
				
				in = new Scanner(System.in);
				String chat = in.nextLine();
				if("exit".equals(chat)) break;
				chat += "\r\n";
				
				
				
				System.out.println(chat);
				
				
				
				outputStream = socket.getOutputStream();
				outputStream.write(chat.getBytes());
				outputStream.flush();				
				
				//in.close(); //When you call, sc.close() in first method, it not only closes your scanner but closes your System.in input stream as well. https://stackoverflow.com/questions/13042008/java-util-nosuchelementexception-scanner-reading-user-input
				
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String fin = bufferedReader.readLine();
				System.out.println(fin);
				
				
				
				System.out.println("여기까지 오니?");
			}
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(outputStream!=null){
				try {
					System.out.println("여기 작동");
					in.close();
					outputStream.close();
					socket.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
