import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.Date;

public class Servidor {

	private int port = 7004;
	private int soma = 0;
	private ServerSocket serverSocket;
	
	public Servidor() throws ServerException, IOException{
		
		int x=1;
		serverSocket = new ServerSocket(port);
		Socket s = serverSocket.accept();
		System.out.println("Conectado com " + s.getInetAddress().getHostAddress() + " - " + new Date());
		DataInputStream  in  = new DataInputStream(s.getInputStream());
		
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		System.out.println("Servidor iniciado na porta " + port);
		int numero=0;
		while(x==1) {
			
			out.writeUTF("Bem vindo! Digite numeros e quando digitar 666 recebera a soma");
			numero=0;
			soma=0;
			
			do {
				out.writeUTF("Numero");
				numero=in.readInt();
				soma=soma+numero;
			}while(numero!=666);
		
			soma = soma -666;
			out.writeUTF("SOMA = " + soma +   " Digite qualquer numero para sair");
			numero=in.readInt();
			x=0;
			s.close();
			System.out.println("Desconectado de " + s.getInetAddress().getHostAddress());
			serverSocket.close();
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
