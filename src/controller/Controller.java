package controller;

import java.util.Scanner;
import java.io.*;


public class Controller {

	public Controller() throws IOException {
	Scanner sc = new Scanner(System.in);
	int repetir = 0;

	while (repetir == 0) {
		System.out.println("Qual operação você deseja realizar?");
		System.out.println("Digite C para cadastrar uma pessoa,");
		System.out.println("Digite R para consultar uma pessoa, ");
		System.out.println("Digite U para atualizar o cadastro de uma pessoa,");
		System.out.println("Digite D para deletar o cadastro de uma pessoa,");
		System.out.println("Ou digite S para sair e não realizar nenhuma operação.");
		String operacao = sc.next(); 

		switch(operacao) {
			case "C": 
				createPessoa(sc); 
				break;
			case "R":
				System.out.println("Qual o nome da pessoa que quer consultar?");
				String nome = sc.next();
				readPessoa(nome);
				break;
			case "U":
				break;
			case "D":
				break;
			case "S":
				System.out.println("Operação finalizada, obrigado por utilizar o cadastrador de pessoas.");
				repetir = 1; 
				break;
			default:
				System.out.println("Comando inválido, tente novamente.");
				break;
			}
			
	}

	sc.close();
}
	
	public static void createPessoa(Scanner sc) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("./pessoa.txt", true)); 

		System.out.println("Qual o nome da pessoa?");
		String nome = sc.next(); 
		System.out.println("Qual a idade da pessoa?");
		String idade = sc.next();
		System.out.println("Qual o telefone da pessoa?");
		String telefone = sc.next();

		pw.print(nome+";");
		pw.print(idade+";");
		pw.print(telefone+"\n");

		System.out.println("Cadastro feito com sucesso!");
		pw.flush();
		pw.close();
	}

	public static void readPessoa(String nome) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./pessoa.txt")));
		String linha;
		while ((linha = br.readLine()) != null) {
			String[] dados = linha.split(";");

			if (dados[0].equals(nome)) {
				System.out.println("Pessoa encontrada!");
				System.out.println("Nome: "+dados[0]);
				System.out.println("Idade: "+dados[1]);
				System.out.println("Telefone: "+dados[2]);
				break;
			}

		}
		if (linha == null) {
			System.out.println("Pessoa não encontrada");
		}
		br.close();
	}
}
