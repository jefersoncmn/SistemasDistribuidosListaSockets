import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 7001);

		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream ps = new DataOutputStream(s.getOutputStream());
		
		Scanner teclado = new Scanner(System.in);
		System.out.println(in.readUTF());
		
		String str = "";
		do{
			str = teclado.nextLine();
			ps.writeUTF( str );
			System.out.println(in.readUTF());
		}while(!str.equals("tchau"));
		
		teclado.close();

		s.close();
	}
}