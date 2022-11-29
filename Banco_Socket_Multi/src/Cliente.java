import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws IOException {
		// Cria o socket com o recurso desejado na porta especificada
		Socket s = new Socket("127.0.0.1", 7000);

		// Cria a Stream de saida de dados
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		
		Scanner teclado = new Scanner(System.in);
		System.out.println(in.readUTF());
		
		String res = "";
		String str = "";
		double quantia = 0;

		while(!res.equals("ok")){
			str = teclado.nextLine();
			out.writeUTF( str );
			res = in.readUTF();			
		}
		System.out.println(in.readUTF());
		do {
			str = teclado.nextLine();
			out.writeUTF( str );
			switch(str){
				case "saldo":
					System.out.println(in.readUTF());
					break;
				case "depositar":
					System.out.println(in.readUTF());
					quantia = teclado.nextDouble();					
					out.writeDouble(quantia);
					System.out.println(in.readUTF());
					break;
				case "sacar":
					System.out.println(in.readUTF());
					quantia = teclado.nextDouble();
					out.writeDouble(quantia);
					System.out.println(in.readUTF());
					break;
				case "encerrar":
					out.writeUTF("encerrou!");
					break;
				default:
					out.writeUTF("Operaçao invalida!");			
					break;
			}		
		} while(!str.equals("encerrar"));

		// Encerra o socket
		s.close();
		teclado.close();
	}
}
