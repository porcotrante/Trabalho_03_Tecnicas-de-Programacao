package model;

public class Pessoa {
	private String nome;
	private String idade; 
	private String telefone;
	public Pessoa(String nome, String idade, String telefone) {
		this.nome = nome;
		this.idade = idade;
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	@Override 
	public String toString() {
		return "O nome da pessoa é:" + nome + "Sua idade é:" + idade + "seu telefone é:" + telefone;
	}
}
