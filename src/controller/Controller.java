package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import model.Pessoa;


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
		Controller.createPessoa(sc); 
		break;
	case "R":
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
		File f = new File("C:\\Users\\theoa\\Desktop\\programas\\programas java\\Pessoa\\pessoa.txt");
		FileOutputStream fo = new FileOutputStream(f); 
		System.out.println("Qual o nome da pessoa?");
		String nome = sc.next(); 
		System.out.println("Qual a idade da pessoa?");
		String idade = sc.next();
		System.out.println("Qual o telefone da pessoa?");
		String telefone = sc.next(); 
		Pessoa pessoa = new Pessoa(nome, idade, telefone);
		fo.write(nome.getBytes());
		fo.write(idade.getBytes());
		fo.write(telefone.getBytes());
		fo.close();
	}
	}
