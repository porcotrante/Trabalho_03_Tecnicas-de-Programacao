package entities;
import java.util.List;

public class consultarPessoa {
	
	public static Object buscarPessoa(List<Pessoa> lista, String nome) {
		for(Pessoa pessoa: lista) {
			if (pessoa.getNome() == nome) {
				return pessoa; 
			}
		}
		return null;
		
		
	}
}
