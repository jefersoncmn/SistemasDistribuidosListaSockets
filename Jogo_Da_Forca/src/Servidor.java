import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Servidor {
	private int port = 7004;

	int erros = 0;
	private ServerSocket serverSocket;
	private static final Random RANDOM = new Random();

	public Servidor() throws ServerException, IOException{
		List<String> frases = new ArrayList<String>();
		
		frases.add("QUADRADO");
		frases.add("ESTRELA");
		frases.add("CIRCULO");
		frases.add("QUADRADO");

		serverSocket = new ServerSocket(port);
		
		System.out.println("Servidor iniciado na porta " + port);
		String palavra, letra, Spainel;
		char[] painel;
		letra = " ";

		while(!letra.equals("BYE")) {
			Socket s = serverSocket.accept();
			System.out.println("Conectado com " + s.getInetAddress().getHostAddress() + " - " + new Date());
	
			DataInputStream  in  = new DataInputStream(s.getInputStream());
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("Bem vindo ao jogo da forca!\nTente adivinhar a palavra!");
			
			Boolean novojogo = true;	
			Spainel = "";
			palavra = "";
			painel = palavra.toCharArray();

			do {
				
				if(novojogo) {
					novojogo = false;
					palavra = frases.get(RANDOM.nextInt(frases.size()));
					
					painel = palavra.toCharArray();
					Spainel = "";
					
					for(int i = 0;i<palavra.length();i++) {
						painel[i] = '_';
						Spainel += "_";
					}
				}
				
				out.writeUTF(Spainel);
				letra = in.readUTF();
				letra = letra.toUpperCase();
				if(palavra.contains(letra)) {
					for(int i = 0;i<palavra.length();i++) {
						System.out.println("Contador: "+i);
						if(letra.contains(palavra.substring(i, i+1))) {
							System.out.println("if");
							painel[i] =letra.toCharArray()[0];
						}
					}
				}	
				else {
					erros++;
				}
				if(erros == 5) {
					out.writeUTF("Voce cometeu 5 erros e perdeu, para jogar novamente clique Enter, para sair digite \"BYE\" ");
					letra = in.readUTF().toUpperCase();	
					erros=0;
					novojogo=true;
				}
				Spainel = "";
				for(int i=0; i<palavra.length();i++) {
					Spainel += painel[i];
				}
				System.out.println(letra + "-" + Spainel + "-" + palavra);	
				if(Spainel.equals(palavra)) {
					erros=0;
					out.writeUTF(palavra + " Voce acertou.\nSe desejar jogar mais uma vez, de Enter, se quiser ir embora digite \"bye\"");
					letra = in.readUTF().toUpperCase();	
					novojogo=true;
				}
			}while(!letra.equals("BYE"));

			s.close();
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
