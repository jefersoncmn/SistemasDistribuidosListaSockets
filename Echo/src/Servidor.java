import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.Date;

public class Servidor {
	private int port = 7001;
	private ServerSocket serverSocket;
	
	public Servidor() throws ServerException, IOException {
		serverSocket = new ServerSocket(port);
		
		System.out.println("Servidor iniciado na porta " + port);

		while (true) {

			Socket s = serverSocket.accept();
			String ip = s.getInetAddress().getHostAddress();
			System.out.println("Conectado com " + ip + " - " + new Date());

			DataInputStream  in  = new DataInputStream(s.getInputStream());
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("Conectado ao servidor ecoador!");

			String str = "";
			do {
				str = in.readUTF();
				
				switch (str) {
					case "tchau":
						out.writeUTF("Desconectado do servidor ecoador!");
						break;
					default:
						out.writeUTF(str);
						break;
				}
				System.out.println("Cliente " + ip + " - " + new Date() + " - Eco: (" + str + ")");
			} while( !str.equals("tchau") );

			s.close();
			System.out.println("Desconectado de " + ip);
		}
	}

	public static void main(String[] args) {
		
		try {
			new Servidor();
		} catch (ServerException e) {
			System.out.println("A conexão com o cliente foi resetada!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}