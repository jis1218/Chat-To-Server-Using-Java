import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	
	static final int PORT_NUM = 9090;

	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT_NUM);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Socket clientSocket = null;
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;
		
		String str = "";
		while(true){
			try{			
				System.out.println("���� ���� �����");
				clientSocket = serverSocket.accept();
				System.out.println("���� ���� �Ǿ����ϴ�");
				bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				str = bufferedReader.readLine(); //�� �����θ� �д´�. ���Ͱ�
				System.out.println(str);
				
				str += "�� ÷��\r\n";
				outputStream = clientSocket.getOutputStream();
				outputStream.write(str.getBytes());
				outputStream.flush();
				
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(str!=null){
					try {
						System.out.println("finally ����");
						bufferedReader.close();
						clientSocket.close(); //���⸦ �ּ�ó�� ���ָ� Port�� �̹� ���ǰ� �ִٰ� ���.
						//serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}				
			}
			
		}
		
		
	}
}
