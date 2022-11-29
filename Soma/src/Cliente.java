import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 7004);
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream ps = new DataOutputStream(s.getOutputStream());
		
		int numero=0;
		Scanner teclado = new Scanner(System.in);
		System.out.println(in.readUTF());
		
		System.out.println(in.readUTF());
		do{
			numero = teclado.nextInt();
			ps.writeInt(numero);
			System.out.println(in.readUTF());
		}while(numero!=401);
		
		numero = teclado.nextInt();
		ps.writeInt(numero);
		System.out.println(in.readUTF());
		
		s.close();
		teclado.close();
	}
}
