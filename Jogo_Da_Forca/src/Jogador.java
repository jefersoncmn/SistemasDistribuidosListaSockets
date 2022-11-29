import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

public class Jogador {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 7004);
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream ps = new DataOutputStream(s.getOutputStream());
		
		Scanner teclado = new Scanner(System.in);
		System.out.println(in.readUTF());
		
		String str = "";
		String entrada = "";
		do{
			entrada = in.readUTF();
			System.out.println(entrada);
			str = teclado.nextLine();
			ps.writeUTF( str );
		}while(!entrada.equals("bye"));

		teclado.close();
		s.close();
	}
}