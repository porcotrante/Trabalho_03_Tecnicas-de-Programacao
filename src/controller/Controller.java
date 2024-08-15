package controller;

import java.util.Scanner;
import java.io.*;

/**
 * Classe responsável pelo controle de cadastro, consulta, atualização e exclusão de pessoas.
 * <p>
 * Esta classe permite realizar operações CRUD (Create, Read, Update, Delete) em um cadastro de pessoas
 * utilizando arquivos de texto para armazenamento das informações.
 * </p>
 * 
 * @author Pedro Wagner, Pedro Militão, Théo Araujo
 */
public class Controller {

    private static String filepath = "./pessoa.txt";

    /**
     * @return O caminho do arquivo de dados.
     */
    private static String getFilepath() {
        return filepath;
    }

    /**
     * Construtor
     * <p>
     * Inicia o menu de operações, permitindo que o usuário escolha a operação desejada.
     * </p>
     *
     * @throws IOException Se ocorrer algum erro na manipulação de arquivos.
     */
    public Controller() throws IOException {
        Scanner sc = new Scanner(System.in);
        int repetir = 0;

        // Loop principal para a execução contínua do menu até o usuário decidir sair.
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
                case "C": //se a escolha for C, pergunta os dados e cadastra a pessoa se não estiver cadastrada
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
                case "R": //se for R, ele lê uma pessoa pelo nome
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
                case "U": //se for U, ele faz o Update de uma pessoa lendo pelo nome
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
                case "D": //se for D, ele deleta lendo pelo nome
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

        sc.close(); //fechando a stream para evitar leaks
    }

    /**
     * Cadastra uma nova pessoa e armazena seus dados em um arquivo de texto.
     *
     * @param nome     O nome da pessoa.
     * @param idade    A idade da pessoa.
     * @param telefone O telefone da pessoa.
     * @return true se o cadastro foi realizado com sucesso, false se a pessoa já está cadastrada.
     * @throws IOException Se ocorrer algum erro na gravação do arquivo.
     */
    public static boolean createPessoa(String nome, String idade, String telefone) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(getFilepath(), true));
        boolean criada = false;
        
        // Verifica se a pessoa já está cadastrada
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

    /**
     * Consulta os dados de uma pessoa pelo nome, lendo o arquivo de cadastro.
     *
     * @param nome O nome da pessoa a ser consultada.
     * @return Um array com os dados da pessoa (nome, idade, telefone) ou {@code null} se a pessoa não for encontrada.
     * @throws IOException Se ocorrer algum erro na leitura do arquivo.
     */
    public static String[] readPessoa(String nome) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFilepath())));
        String linha;
        boolean encontrado = false;
        String[] dados = null;
        
        // Percorre o arquivo linha por linha para encontrar a pessoa
        while ((linha = br.readLine()) != null) {
            dados = linha.split(";");
            if (dados[0].equals(nome)) {
                encontrado = true;
                break;
            }
        }
        br.close();
        return encontrado ? dados : null;
    }

    /**
     * Atualiza os dados de uma pessoa.
     * <p>
     * A operação consiste em deletar o registro atual e criar um novo com os dados atualizados.
     * </p>
     *
     * @param nome     O nome da pessoa a ser atualizada.
     * @param idade    A nova idade da pessoa.
     * @param telefone O novo telefone da pessoa.
     * @return true se a atualização foi realizada com sucesso, false se a pessoa não for encontrada.
     * @throws IOException Se ocorrer algum erro na leitura ou gravação do arquivo.
     */
    private static boolean updatePessoa(String nome, String idade, String telefone) throws IOException {
        boolean atualizado = false;
        
        // A atualização é feita através da deleção seguida da criação com novos dados
        if (deletePessoa(nome)) {
            createPessoa(nome, idade, telefone);
            atualizado = true;
        }
        return atualizado;
    }

    /**
     * Deleta o cadastro de uma pessoa.
     * <p>
     * A operação envolve a leitura de todos os registros e a reescrita do arquivo sem o registro a ser deletado.
     * </p>
     *
     * @param nome O nome da pessoa a ser deletada.
     * @return true se a pessoa foi deletada com sucesso, false se a pessoa não for encontrada.
     * @throws IOException Se ocorrer algum erro na leitura ou gravação do arquivo.
     */
    private static boolean deletePessoa(String nome) throws IOException {
        File oldFile = new File(getFilepath());
        File newFile = new File("./temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(oldFile));
        PrintWriter pw = new PrintWriter(new FileWriter(newFile, true));
        boolean deletado = false;

        String linha;
        String[] dados;
        
        // Copia todas as linhas do arquivo original, exceto a linha a ser deletada
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

        // Substitui o arquivo original pelo novo arquivo temporário
        oldFile.delete();
        newFile.renameTo(new File(getFilepath()));
        return deletado;
    }
}
