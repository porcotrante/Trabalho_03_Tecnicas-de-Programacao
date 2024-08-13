package controller;

import java.util.Scanner;
import java.io.*;

public class Controller {

	private static String filepath = "./pessoa.txt";

	private static String getFilepath() {
		return filepath;
	}

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

			String nome, idade, telefone;
			switch (operacao) {
				case "C":
					System.out.println("Qual o nome da pessoa?");
					nome = sc.next();
					System.out.println("Qual a idade da pessoa?");
					idade = sc.next();
					System.out.println("Qual o telefone da pessoa?");
					telefone = sc.next();
					if (createPessoa(nome, idade, telefone)) {
						System.out.println("Cadastro feito com sucesso!");
					} else {
						System.out.println("Essa pessoa já foi cadastrada!\nOperação cancelada");
					}
					break;
				case "R":
					System.out.println("Qual o nome da pessoa que quer consultar?");
					nome = sc.next();
					String[] dados = readPessoa(nome);
					if (dados != null) {
						System.out.println("Pessoa encontrada!");
						System.out.println("Nome: " + dados[0]);
						System.out.println("Idade: " + dados[1]);
						System.out.println("Telefone: " + dados[2]);
					} else {
						System.out.println("Esse nome não está em nossos registros!");
					}
					break;
				case "U":
					System.out.println("Qual o nome da pessoa cujos dados serão alterados?");
					nome = sc.next();
					System.out.println("Qual a idade da pessoa?");
					idade = sc.next();
					System.out.println("Qual o telefone da pessoa?");
					telefone = sc.next();
					if (updatePessoa(nome, idade, telefone)) {
						System.out.println("Atualização realizada com sucesso");
					} else {
						System.out.println("Esse nome não está em nossos registros!");
					}
					break;
				case "D":
					System.out.println("Qual o nome da pessoa cujos dados serão deletados?");
					nome = sc.next();
					if (deletePessoa(nome)) {
						System.out.println("Deleção executada com sucesso");
					} else {
						System.out.println("Esse nome não está em nossos registros!");
					}
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

	public static boolean createPessoa(String nome, String idade, String telefone) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(getFilepath(), true));
		boolean criada = false;
		if (readPessoa(nome) == null) {
			pw.print(nome + ";");
			pw.print(idade + ";");
			pw.print(telefone + "\n");
			criada = true;
		}
		pw.flush();
		pw.close();
		return criada;
	}

	public static String[] readPessoa(String nome) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFilepath())));
		String linha;
		boolean encontrado = false;
		String[] dados = null;
		while ((linha = br.readLine()) != null) {
			dados = linha.split(";");
			if (dados[0].equals(nome)) {
				encontrado = true;
				break;
			}
		}
		br.close();
		if (encontrado) {
			return dados;
		} else {
			return null;
		}
	}

	private static boolean updatePessoa(String nome, String idade, String telefone) throws IOException {
		boolean atualizado = false;
		if (deletePessoa(nome)) {
			createPessoa(nome, idade, telefone);
			atualizado = true;
		}
		return atualizado;
	}

	private static boolean deletePessoa(String nome) throws IOException {
		File oldFile = new File(getFilepath());
		File newFile = new File("../temp.txt");
		BufferedReader br = new BufferedReader(new FileReader(oldFile));
		PrintWriter pw = new PrintWriter(new FileWriter(newFile, true));
		boolean deletado = false;

		String linha;
		String[] dados;
		while ((linha = br.readLine()) != null) {
			dados = linha.split(";");
			if (!(dados[0].equals(nome))) {
				pw.print(linha + "\n");
			} else {
				deletado = true;
			}
		}
		br.close();
		pw.close();

		oldFile.delete();
		newFile.renameTo(new File(getFilepath()));
		return deletado;
	}
}