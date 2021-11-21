package library;

import library.entities.Emprestimo;
import library.entities.FilaEspera;
import library.entities.Leitor;
import library.entities.Livro;

import java.util.ArrayList;
import java.util.Scanner;

import static library.Utilities.ExcelUtils.*;
import static library.services.EmprestimoService.*;
import static library.services.LeitorService.ordenarListByNome;
import static library.services.LeitorService.pesquiseLeitorByIdOrNome;
import static library.services.LivroService.*;

/**
 * Projeto feito em java 8
 * utilizando recursos lambdas disponiveis a partir desta versão
 * @author rroch
 */
public class Library {

    public static void main(String[] args) throws Exception {
        /**
         * Linha 27 ha 32 - é usada para carregar os dados dos arquivos
         * chamando os métodos da classe ExcelUtils
         */
        readBookFromCSV();
        ArrayList<Livro> bookList = readBookFromXLSX();
        ArrayList<Leitor> readerList = readReaderFromXLSX();
        ArrayList<Emprestimo> emprestimoList = readEmprestimoFromXLSX();
        ArrayList<FilaEspera> filaEsperaList = readFilaEsperaFromXLSX();
        showOption(bookList, readerList, emprestimoList, filaEsperaList);
    }

    /**
     * Método usado para mostrar um menu de opçOes para os usuarios
     * Utilizamos um switch case para acessar os métodos de acordo com as escolhas do usuario
     * @param bookList
     * @param readerList
     * @param emprestimoList
     * @param filaEsperaList
     * @throws Exception
     */
    public static void showOption(ArrayList<Livro> bookList, ArrayList<Leitor> readerList, ArrayList<Emprestimo> emprestimoList, ArrayList<FilaEspera> filaEsperaList) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        do {

            System.out.println("Opção 1 - Registre um livro.");
            System.out.println("Opção 2 - Pesquise um livro específico por título e / ou nome do autor.");
            System.out.println("Opção 3 - Liste todos os livros por título e / ou nome do autor em ordem alfabética.");
            System.out.println("Opção 4 - Pesquise um leitor específico por nome e / ou ID.");
            System.out.println("Opção 5 - Liste todos os leitores por ordem alfabética e / ou de identificação.");
            System.out.println("Opção 6 - Registre que um leitor pegou um livro emprestado.");
            System.out.println("Opção 7 - Registre que um leitor devolveu um livro.");
            System.out.println("Opção 8 - Liste os livros que ele pegou emprestado.");
            System.out.println("Opção 0 - Sair do programa");
            System.out.println("____________________________________________________________________________________________");

            System.out.print("\nDigite aqui sua opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao){
                case 1:
                    cadastrar(sc, bookList);
                    break;
                case 2:
                    pesquiseLivroByTituloOrNomeAutor(sc, bookList);
                    break;
                case 3:
                    ordenarListByTituloOrNomeAutor(sc, bookList);
                    break;
                case 4:
                    pesquiseLeitorByIdOrNome(sc, readerList);
                    break;
                case 5:
                    ordenarListByNome(readerList);
                    break;
                case 6:
                    cadastrarEmprestimo(sc, readerList, emprestimoList, filaEsperaList);
                    break;
                case 7:
                    devolver(sc, emprestimoList, filaEsperaList);
                    break;
                case 8:
                    listarLivroEmprestadoByLeitor(sc, readerList, emprestimoList);
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }

}