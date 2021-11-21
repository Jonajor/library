package library.services;

import library.entities.Livro;

import java.util.*;

import static library.Utilities.ExcelUtils.printDataBook;

public class LivroService {

    /**
     * Método que permite cadastrar um livro em memória, mas nao atualiza o arquivo
     * @param sc
     * @param bookList
     */
    public static void cadastrar(Scanner sc, ArrayList<Livro> bookList){
        Livro livro = new Livro();

        livro.setId(UUID.randomUUID().toString());
        System.out.printf("Digite o nome do autor: ");
        livro.setAutorFirstName(sc.nextLine());

        System.out.printf("Digite o ultimo nome do autor: ");
        livro.setAutorLastName(sc.nextLine());

        System.out.printf("Digite o titulo do livro: ");
        livro.setTitulo(sc.nextLine());

        System.out.printf("Digite o genero do livro: ");
        livro.setGenre(sc.nextLine());

        System.out.println();
        bookList.add(livro);
    }

    /**
     * Método que permite pesquisar o livro por titulo ou pelo primeiro no do autor
     * @param sc
     * @param bookList
     * @throws Exception
     */
    public static void pesquiseLivroByTituloOrNomeAutor(Scanner sc, ArrayList<Livro> bookList) throws Exception {
        System.out.printf("Digite o nome do livro ou o autor: ");
        String scInput = sc.nextLine();

        Optional<Livro> book = bookList.stream()
                .filter(findMap -> scInput.equals(findMap.getTitulo()) || scInput.equals(findMap.getAutorFirstName()))
                .findAny();

        if (!book.isPresent() || book.equals(null)){
            System.out.println("Livro nao encontrado");
        } else {
            System.out.println("\n############## Livro encontrado ##############");
            printDataBook(book.get());
        }

    }

    /**
     * Método que ordena a lista de livros
     * Temos tres opçOes de ordenação
     * @param sc
     * @param bookList
     */
    public static void ordenarListByTituloOrNomeAutor(Scanner sc, ArrayList<Livro> bookList) {
        int opcao = 0;
        System.out.println("Opção 1 - Ordenar por titulo e autor.");
        System.out.println("Opção 2 - Ordenar somente por titulo");
        System.out.println("Opção 3 - Ordenar somente por autor");

        System.out.print("\nDigite aqui sua opção de ordenação: ");
        opcao = Integer.parseInt(sc.nextLine());

        switch (opcao){
            case 1:
                Collections.sort(bookList, Comparator.comparing(Livro::getTitulo)
                        .thenComparing(Livro::getAutorFirstName));
                break;
            case 2:
                Collections.sort(bookList, Comparator.comparing(Livro::getTitulo));
                break;
            case 3:
                Collections.sort(bookList, Comparator.comparing(Livro::getAutorFirstName));
                break;
        }

        for (Livro livro: bookList){
            System.out.println("\n############## Livros Ordenados ##############");
            printDataBook(livro);
        }
    }

}
