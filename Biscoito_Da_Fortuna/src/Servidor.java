import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.Date;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	private int port = 7002;
	private ServerSocket serverSocket;
	
	public static final Random RANDOM = new Random();
	
	public Servidor() throws ServerException, IOException {
		
		List<String> frases = new ArrayList<String>();

		//https://www.mulherportuguesa.com/pessoa/desenvolvimento-pessoal-pessoas/frases-para-biscoitos-sorte/
		frases.add("Não importa o tamanho da montanha, ela não pode tapar o sol.");
		frases.add("O bom-senso vale mais do que muito conhecimento.");
		frases.add("Aquele que se importa com o sentimento dos outros, não é um tolo.");
		frases.add("Lamentar aquilo que não temos é desperdiçar aquilo que já possuímos.");
		frases.add("Uma bela flor é incompleta sem as suas folhas.");
		frases.add("Sem o fogo do entusiasmo, não há o calor da vitória.");
		frases.add("A sorte favorece a mente bem preparada.");
		
		serverSocket = new ServerSocket(port);
		
		System.out.println("Servidor iniciado na porta " + port);

		while (true) {

			Socket s = serverSocket.accept();//fica em espera aguardando que alguém conecte. Quando alguma conexão é aceita ele retorna um objeto Socket.
			String ip = s.getInetAddress().getHostAddress();
			System.out.println("Conectado com " + ip + " - " + new Date());

			DataInputStream  in  = new DataInputStream(s.getInputStream());
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("Conectado ao servidor FortuneCookie!");

			String str = "";
			do {
				str = in.readUTF();
				
				switch (str) {
					case "tchau":
						out.writeUTF("Desconectado do servidor!");
						break;
					case "GET-FORTUNE":
						out.writeUTF(frases.get(RANDOM.nextInt(frases.size())));
						break;
					case "SET-FORTUNE":
						str = in.readUTF();
						frases.add(str);
						out.writeUTF("Frase enviada com sucesso");
						System.out.print("SET-FORTUNE:");
						break;
					default:
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