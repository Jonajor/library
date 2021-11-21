package library.services;

import library.entities.Leitor;

import java.util.*;

import static library.Utilities.ExcelUtils.printDataReader;

public class LeitorService {

    /**
     * Método que pesquisa o Leitor por Id ou por nome
     * @param sc
     * @param readerList
     * @return
     */
    public static Leitor pesquiseLeitorByIdOrNome(Scanner sc, ArrayList<Leitor> readerList) {
        System.out.printf("Digite o Id do leitor ou o nome: ");
        String scInput = sc.nextLine();

        /**
         * linha 24 - 26 - é onde é feito o filtro ou por id ou por nome
         */
        Optional<Leitor> reader = readerList.stream()
                .filter(findMap -> scInput.equals(findMap.getId()) || scInput.equals(findMap.getNome()))
                .findAny();

        if (!reader.isPresent() || reader.equals(null)){
            System.out.println("Leitor não Encontrado");
        } else {
            System.out.println("\n############## Leitor encontrado ##############");
            printDataReader(reader.get());
        }

        return reader.get();
    }

    /**
     * Método que ordena a lista de leitores por nome
     * @param readerList
     */
    public static void ordenarListByNome(ArrayList<Leitor> readerList) {

        Collections.sort(readerList, Comparator.comparing(Leitor::getNome));

        for (Leitor leitor: readerList){
            System.out.println("\n############## Leitores ordenados por nome ##############");
            printDataReader(leitor);
        }
    }
}
