##### 서버간 통신에서 문자열을 주고 받을 때 중요한 것은 bufferedReader.readLine()이다.
##### 이 함수는 엔터값 기준, 즉 줄 단위로만 읽기 때문에 넘겨오는 값에 /n/r이 있어야 한다.
##### 그동안 잘 안되었던 이유는 in.nextLine()에서 /n/r을 써주지 않았기 때문에 readLine()에서 받지를 못한 것이었다. 결국 close()가 되어서야 client 쪽에서 넘어가고 server쪽에서 인식을 한거였다.

```java
public class ClientMain {
	public static final int PORT_NUM = 9090;
	public static void main(String args[]){
		
		Socket socket = null;
		OutputStream outputStream = null;
		Scanner in = null;
		BufferedReader bufferedReader = null;		
		
		try {		
			
			while(true){
				socket = new Socket("192.168.0.9", PORT_NUM); //서버와 연결시켜주는 코드라고 보면 된다.
				System.out.println("chatting 하십시오");
				
				in = new Scanner(System.in);
				String chat = in.next();
				if("exit".equals(chat)) break;
				//chat += "\r\n";
				
				System.out.println(chat);
```
##### 위의 코드의 경우 socket을 초기화해주는 코드가 while문 안에 있어서 chatting 메세지를 받을 때마다 서버와 연결이 새로된다. 하지만 socket이 바깥에 있을 경우에는 chatting 메세지를 한번에 받아 전달한다. 따라서 chat+= "\r\n" 코드가 있는 경우에는 바로바로 메세지가 가게 되고 없을 경우에는 한번에 가게 된다.



아래와 같은 코드를 돌리면 계속 에러가 뜬다.
```java
try {
			socket = new Socket("192.168.0.9", PORT_NUM); //서버와 연결시켜주는 코드라고 보면 된다.
			String chat = "";
			while(true){
				System.out.println("chatting 하십시오");
				
				Scanner in = new Scanner(System.in);
				chat = in.nextLine();
				
				
				System.out.println(chat);
				
				if("exit".equals(chat)) break;
				
				outputStream = socket.getOutputStream();
				outputStream.write(chat.getBytes());
				outputStream.flush();
				
				in.close(); //When you call, sc.close() in first method, it not only closes your scanner but closes your System.in input stream as well. https://stackoverflow.com/questions/13042008/java-util-nosuchelementexception-scanner-reading-user-input
				
				
				System.out.println("여기까지 오니?");
			}
			
			
			
		}
```

in.close()를 한 후 while문을 다시 돌게 되면 
```Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.util.Scanner.nextLine(Scanner.java:1540)
	at ClientMain.main(ClientMain.java:22)
```
이런 에러가 뜨는 것을 볼 수가 있는데 그 이유를 찾아보니 다음과 같았다.
When you call, sc.close() in first method, it not only closes your scanner but closes your System.in input stream as well. https://stackoverflow.com/questions/13042008/java-util-nosuchelementexception-scanner-reading-user-input
계속 System.in을 close 해줬기 때문에 그런 것이었다.