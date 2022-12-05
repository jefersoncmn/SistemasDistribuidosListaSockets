import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 7002);

		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream ps = new DataOutputStream(s.getOutputStream());
		
		Scanner teclado = new Scanner(System.in);
		System.out.println(in.readUTF());
		
		String str = "";
		do{
			System.out.println("Escolha 'frase' ou 'editar':");
			str = teclado.nextLine();
			
			switch(str) {
			case "frase":
				ps.writeUTF("GET-FORTUNE");
				System.out.println(in.readUTF());
				break;
				
			case "editar":				
				ps.writeUTF("SET-FORTUNE");
				ps.writeUTF(teclado.nextLine());
				System.out.println(in.readUTF());
				break;
				
			default:
				ps.writeUTF( str );
				System.out.println(in.readUTF());
			}
		}while(!str.equals("tchau"));

		teclado.close();
		s.close();
	}
}