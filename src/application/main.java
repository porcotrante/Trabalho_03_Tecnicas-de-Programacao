package application;

import controller.Controller;

/**
 * na classe main, o controller é instanciado e assim as operações são executadas, caso haja erro, seu traço é printado
 * @author Pedro Wagner, Théo Araujo
 */

public class main {

	public static void main(String[] args) {
		try {
			Controller control = new Controller();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
