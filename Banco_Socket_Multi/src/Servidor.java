import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

class Conta {
	String numeroConta;
	String nomeCliente;
	double saldo;

	public Conta(String numeroConta, String nomeCliente, double saldo) {
		this.numeroConta = numeroConta;
		this.nomeCliente = nomeCliente;
		this.saldo = saldo;
	}

	public Boolean depositar(double quantia) {
		if (quantia > 0) {
			this.saldo += quantia;
			return true;
		}
		return false;
	}

	public Boolean sacar(double quantia) {
		if (this.saldo > quantia && this.saldo > 0 && quantia > 0) {
			this.saldo -= quantia;
			return true;
		}
		return false;
	}

}

class Banco {

	List<Conta> contas = new ArrayList<>();

	public Banco() {
		contas.add(new Conta("1", "Ana", 0));
		contas.add(new Conta("2", "Rafael", 0));
		contas.add(new Conta("3", "Gustavo", 0));
		contas.add(new Conta("4", "Jeferson", 0));
	}

	public Conta buscarConta(String nConta) {
		Conta c = null;
		for (Conta conta : contas) {
			if (conta.numeroConta.equals(nConta)) {
				c = conta;
				break;
			}
		}
		return c;
	}

}

public class Servidor extends Thread {
	private int port;
	private Socket s;

	public Servidor(Socket s) {
		this.s = s;
	}
	// Cria o ServerSocket na porta especificada se estiver dispon�vel
	// this.s = s;
	// this.port = port;
	// System.out.println("Servidor iniciado na porta " + port);
	// serverSocket = new ServerSocket(port);

	public void run() {
		try {
			System.out.println("Servidor iniciado na porta ");
			Banco banco = new Banco();

			//while (true) {

				// Aguarda uma conex�o na porta especificada e cria retorna o socket que ir�
				// comunicar com o cliente
				String ip = s.getInetAddress().getHostAddress();
				System.out.println("Conectado com " + ip);

				// Cria um DataInputStream para o canal de entrada de dados do socket
				DataInputStream in = new DataInputStream(s.getInputStream());

				// Cria um DataOutputStream para o canal de sa�da de dados do socket
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				// Aguarda por algum dado e imprime a linha recebida quando recebe
				String nConta = "";
				out.writeUTF("Informe numero conta: ");

				double quantia = 0;
				String op = "";
				Conta conta = null;

				while (true) {
					nConta = in.readUTF();
					conta = banco.buscarConta(nConta);
					if (conta != null) {
						out.writeUTF("ok");
						break;
					}
				}
				out.writeUTF("Bem vindo, " + conta.nomeCliente
						+ " \n Operações Disponiveis: [saldo, sacar, depositar, encerrar]");
				if (conta != null) {
					do {
						op = in.readUTF();
						switch (op) {
							case "saldo":
								out.writeUTF("Saldo: " + conta.saldo);
								break;
							case "depositar":
								out.writeUTF("Quantia: ");
								quantia = in.readDouble();
								if (conta.depositar(quantia)) {
									out.writeUTF("deposito de " + quantia
											+ " realizado com sucesso, seu novo saldo é de: " + conta.saldo);
								} else {
									out.writeUTF("não foi possivel realizar a operação de saque, saldo é de: "
											+ conta.saldo);
								}
								break;
							case "sacar":
								out.writeUTF("Quantia: ");
								quantia = in.readDouble();
								if (conta.sacar(quantia)) {
									out.writeUTF("saque de " + quantia + " realizado com sucesso, seu novo saldo é de: "
											+ conta.saldo);
								} else {
									out.writeUTF("não foi possivel realizar a operação de saque, saldo é de: "
											+ conta.saldo);
								}
								break;
							case "encerrar":
								out.writeUTF("encerrou!");
								break;
							default:
								System.out.println(in.readUTF());
								break;
						}
						System.out.println("O cliente " + ip + " solicitou: " + op);
					} while (!op.equals("encerrar"));
				}

				// Encerro o socket de comunica��o
				s.close();
				System.out.println("Desconectado de " + ip);
			//}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Encerro o ServerSocket
		// serv.close();
	}

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(7000);
			server.setReuseAddress(true);
			while (true) {
				Socket s = server.accept();
				System.out.println("Abriu");
				new Servidor(s).start();
			}
		} catch (ServerException e) {
			System.out.println("A conex�o com o cliente foi resetada!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}