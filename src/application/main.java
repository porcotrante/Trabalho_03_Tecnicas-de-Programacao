package application;
import java.io.*;
import java.util.Scanner;

import entities.Pessoa;

public class main {

	public static void main(String[] args) {
		try {
			File f = new File("C:\\Users\\theoa\\Desktop\\programas\\programas java\\Pessoa\\pessoa.txt");
			FileOutputStream fo = new FileOutputStream(f); 
			Scanner sc = new Scanner(System.in);
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
			sc.close();
			fo.close();
		}
		catch(Exception ex) {
			return; 
		}
	}

}
