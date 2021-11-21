package library.services;

import library.entities.Emprestimo;
import library.entities.FilaEspera;
import library.entities.Leitor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static library.Utilities.ExcelUtils.*;
import static library.services.LeitorService.pesquiseLeitorByIdOrNome;

public class EmprestimoService {

    /**
     *
     * @param sc
     * @param readerList
     * @param emprestimoList
     * @param filaEsperaList
     * @throws IOException
     * Método cadastrarEmprestimo é o metodo onde os emprestimos sao cadastrados
     */
    public static void cadastrarEmprestimo(Scanner sc, ArrayList<Leitor> readerList, ArrayList<Emprestimo> emprestimoList, ArrayList<FilaEspera> filaEsperaList) throws IOException {

        System.out.printf("Digite o nome do livro: ");
        String scInput = sc.nextLine();

        /**
         * Da linha 34 há 36 - É feita a busca dentro do ArrayList
         * para buscar o nome do livro que o usuaria digitou e a partir do resultado
         * verificamos se o livro ja esta emprestado
         * Foi utilizado o recurso de lambda disponivel a partir da versao do java 8
         */
        Optional<Emprestimo> emprestimo = emprestimoList.stream()
                .filter(findMap -> scInput.equals(findMap.getLivro()))
                .findAny();

        int opcao = 0;
        /**
         * Na linha 45 foi feito um if onde caso o resultado do filtro da linha 37 - 39
         * Retorno algo, significa que o livro ja esta emprestado.
         * Caso o livro ja esteja emprestado o usuario tem a opcao de entrar na fila de espera
         * Se o emprestimo nao estiver presente o codigo vai para linha 63 no else
         * Onde é possivel cadastrar um emprestimo
         * Ou para um usuario novo, ou um usuario ja cadastrado
         */
        if (emprestimo.isPresent()){
            System.out.println("Opção 1 - Livro ja esta emprestado. Cadastre-se na fila de espera? ");
            System.out.println("Opção 2 - Livro ja esta emprestado. Não entrar na fila de espera? ");
            System.out.print("\nDigite aqui sua opção: ");
            opcao = Integer.parseInt(sc.nextLine());
            switch (opcao){
                case 1:
                    FilaEspera fila = cadastrarFilaEspera(sc, scInput);
                    updateFilaEsperaFileXLSX(fila);
                    filaEsperaList.add(fila);
                    break;
                case 2:
                    break;
            }

        } else {
            System.out.println("Opção 1 - Cadastrar emprestimo para  um novo leitor? ");
            System.out.println("Opção 2 - Cadastrar emprestimo para leitor já cadastrado? ");

            System.out.print("\nDigite aqui sua opção de cadastro de emprestimo: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao){
                case 1:
                    cadastrarEmprestimoParaNovoLeitor(scInput, sc, readerList, emprestimoList);
                    break;
                case 2:
                    cadastrarEmprestimoParaLeitorExistente(scInput, sc, readerList, emprestimoList);
                    break;
            }
        }

    }

    /**
     *
     * @param sc
     * @param scInput
     * @return
     * Esse método cadastrarFilaEspera é utilizado para cadastrar um usuario na fila de espera
     */
    private static FilaEspera cadastrarFilaEspera(Scanner sc, String scInput) {
        FilaEspera filaEspera = new FilaEspera();

        filaEspera.setId(UUID.randomUUID().toString());
        System.out.printf("Digite o nome do leitor: ");
        filaEspera.setNomeLeitor(sc.nextLine());
        filaEspera.setNomeLivro(scInput);
        filaEspera.setDataCadastro(LocalDateTime.now().toString());
        System.out.println("######### Leitor cadastrado na fila de espera #########");
        return filaEspera;
    }

    /**
     *
     * @param scInput
     * @param sc
     * @param readerList
     * @param emprestimoList
     * cadastrarEmprestimoParaLeitorExistente método que utilizamos para cadastrar um emprestimos
     * para um usuario ja cadastrado
     */
    private static void cadastrarEmprestimoParaLeitorExistente(String scInput, Scanner sc, ArrayList<Leitor> readerList, ArrayList<Emprestimo> emprestimoList) {
        Emprestimo emprestimo = new Emprestimo();
        try{
            Leitor leitor = pesquiseLeitorByIdOrNome(sc, readerList);

            emprestimo.setLivro(scInput);

            emprestimo.setDataEmprestimo(LocalDate.now().toString());

            emprestimo.setDevolucao("false");

            emprestimo.setLeitor(leitor);

            System.out.println();
            updateEmprestimoFileXLSX(emprestimo);
            emprestimoList.add(emprestimo);
            System.out.println("\n##########Emprestimo cadastrado com sucesso##########\n");

        } catch (NumberFormatException | IOException ex){
            System.out.println("Ocorreu um problema. Leitor não encontrado");
        }

    }

    /**
     *
     * @param scInput
     * @param sc
     * @param readerList
     * @param emprestimoList
     * @throws IOException
     * cadastrarEmprestimoParaNovoLeitor método que utilizamos para cadastrar um emprestimos
     * para um usuario novo
     */
    private static void cadastrarEmprestimoParaNovoLeitor(String scInput, Scanner sc, ArrayList<Leitor> readerList, ArrayList<Emprestimo> emprestimoList) throws IOException {
        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setLivro(scInput);

        emprestimo.setDataEmprestimo(LocalDate.now().toString());

        emprestimo.setDevolucao("false");

        Leitor leitor = new Leitor();

        leitor.setId(UUID.randomUUID().toString());

        System.out.printf("\nDigite o nome do leitor: ");
        leitor.setNome(sc.nextLine());

        System.out.printf("\nDigite o endereco do leitor: ");
        leitor.setEndereco(sc.nextLine());

        emprestimo.setLeitor(leitor);

        System.out.println();
        readerList.add(emprestimo.getLeitor());
        updateReaderFromXLSX(emprestimo.getLeitor());
        updateEmprestimoFileXLSX(emprestimo);
        emprestimoList.add(emprestimo);
        System.out.println("\n##########Emprestimo e Novo cadastrado com sucesso##########\n");
    }

    /**
     *
     * @param sc
     * @param emprestimoList
     * @param filaEsperaList
     * Método usado para cadastrar uma devolução
     */
    public static void devolver(Scanner sc, ArrayList<Emprestimo> emprestimoList, ArrayList<FilaEspera> filaEsperaList){
        System.out.printf("Digite o nome do livro que deseja devolver: ");
        String scInput = sc.nextLine();

        /**
         * Linha 189 - 191 - Faz um filtro para verificar se o livro esta na lista de emprestimos
         */
        Optional<Emprestimo> emprestimo = emprestimoList.stream()
                .filter(findMap -> scInput.equals(findMap.getLivro()))
                .findAny();

        /**
         * Caso emprestimo nao retorne nada, imprimimos na tela de que nao foi encontrado
         */
        if (!emprestimo.isPresent() || emprestimo.equals(null)) {
            System.out.println("Emprestimo nao encontrado");
        } else {
            Emprestimo updateEmprestimo = emprestimo.get();
            updateEmprestimo.setDevolucao("true");
            emprestimoList.set(emprestimoList.indexOf(emprestimo.get()), updateEmprestimo);
            System.out.println("\n############## Livro devolvido ##############");
            exibirProximoDaLista(sc, filaEsperaList);
        }
    }

    /**
     *
     * @param sc
     * @param filaEsperaList
     * Método que exibe o proximo da fila de espera quando o livro é devolvido
     */
    private static void exibirProximoDaLista(Scanner sc, ArrayList<FilaEspera> filaEsperaList) {

        Collections.sort(filaEsperaList, Comparator.comparing(FilaEspera::getDataCadastro));

        FilaEspera fila = filaEsperaList.get(0);

        System.out.println("\n############## Proximo da Lista de Espera ##############");
        printDataListaEspera(fila);

    }

    /**
     *
     * @param sc
     * @param readerList
     * @param emprestimoList
     * Método que lista o emprestimo por leitor
     */
    public static void listarLivroEmprestadoByLeitor(Scanner sc, ArrayList<Leitor> readerList, ArrayList<Emprestimo> emprestimoList) {
        Leitor leitor = pesquiseLeitorByIdOrNome(sc, readerList);

        Optional<Emprestimo> emprestimo = emprestimoList.stream()
                .filter(findMap -> leitor.getNome().equals(findMap.getLeitor().getNome()))
                .findAny();
        if (!emprestimo.isPresent() || emprestimo.equals(null)){
            System.out.println("Não consta emprestimos para este leitor");
        } else{
            printDataEmprestimos(emprestimo.get());
        }
    }
}
