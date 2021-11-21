package library.Utilities;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import library.entities.Emprestimo;
import library.entities.FilaEspera;
import library.entities.Leitor;
import library.entities.Livro;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtils {

    /**
     * Da linha 28 a 32 - Foram criadas variaveis estaticas para acessar os arquivos de texto
     */
    private static final String SAMPLE_CSV_FILE_PATH = "src/main/resources/ExFiles/MOCK_DATA.csv";
    private static final String SAMPLE_XLSX_FILE_PATH = "src/main/resources/ExFiles/MOCK_DATA.xlsx";
    private static final String SAMPLE_READER_XLSX_FILE_PATH = "src/main/resources/ExFiles/MOCK_DATA_LEITOR.xlsx";
    private static final String SAMPLE_EMPRESTIMOS_XLSX_FILE_PATH = "src/main/resources/ExFiles/DATA_EMPRESTIMO.xlsx";
    private static final String SAMPLE_FILA_ESPERA_XLSX_FILE_PATH = "src/main/resources/ExFiles/DATA_FILA_ESPERA.xlsx";

    /**
     * Métodos readBookFromCSV, readBookFromXLSX, readReaderFromXLSX, readEmprestimoFromXLSX
     * São métodos que acessam os arquivos e carregam na memória e devolvem um ArrayList de objetos
     * Todos fazem a mesma coisa
     */
    public static void readBookFromCSV() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));

            CsvToBean<Livro> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Livro.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Livro> csvUserIterator = csvToBean.iterator();

            while (csvUserIterator.hasNext()) {
                Livro livro = csvUserIterator.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Livro> readBookFromXLSX(){
        try
        {
            FileInputStream file = new FileInputStream(SAMPLE_XLSX_FILE_PATH);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            ArrayList<Livro> bookList = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                Livro e= new Livro();
                Row ro=sheet.getRow(i);
                for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                    Cell ce = ro.getCell(j);
                    if(j==0){
                        //If you have Header in text It'll throw exception because it won't get NumericValue
                        e.setId(ce.getStringCellValue());
                    }
                    if(j==1){
                        e.setAutorFirstName(ce.getStringCellValue());
                    }
                    if(j==2){
                        e.setAutorLastName(ce.getStringCellValue());
                    }
                    if(j==3){
                        e.setTitulo(ce.getStringCellValue());
                    }
                    if(j==4){
                        e.setGenre(ce.getStringCellValue());
                    }
                }
                bookList.add(e);
            }
            file.close();
            return bookList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Leitor>  readReaderFromXLSX() {
        try
        {
            FileInputStream file = new FileInputStream(SAMPLE_READER_XLSX_FILE_PATH);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            ArrayList<Leitor> readerList = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                Leitor e= new Leitor();
                Row ro=sheet.getRow(i);
                for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                    Cell ce = ro.getCell(j);
                    if(j==1){
                        //If you have Header in text It'll throw exception because it won't get NumericValue
                        e.setId(ce.getStringCellValue());
                    }
                    if(j==2){
                        e.setNome(ce.getStringCellValue());
                    }
                    if(j==3){
                        e.setEndereco(ce.getStringCellValue());
                    }
                }
                readerList.add(e);
            }
            file.close();
            return readerList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Emprestimo> readEmprestimoFromXLSX() {
        try
        {
            FileInputStream file = new FileInputStream(SAMPLE_EMPRESTIMOS_XLSX_FILE_PATH);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            ArrayList<Emprestimo> readerList = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                Emprestimo e= new Emprestimo();
                Row ro=sheet.getRow(i);
                for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                    Cell ce = ro.getCell(j);
                    if(j==1){
                        //If you have Header in text It'll throw exception because it won't get NumericValue
                        e.setLivro(ce.getStringCellValue());
                    }
                    if(j==2){
                        e.setDataEmprestimo(ce.getStringCellValue());
                    }
                    if(j==3){
                        e.setDevolucao(ce.getStringCellValue());
                    }
                    if(j==4){
                        e.getLeitor().setId(ce.getStringCellValue());
                    }
                    if(j==5){
                        e.getLeitor().setNome(ce.getStringCellValue());
                    }
                    if(j==6){
                        e.getLeitor().setEndereco(ce.getStringCellValue());
                    }
                }
                readerList.add(e);
            }
            file.close();
            return readerList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<FilaEspera> readFilaEsperaFromXLSX() {
        try
        {
            FileInputStream file = new FileInputStream(SAMPLE_FILA_ESPERA_XLSX_FILE_PATH);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            ArrayList<FilaEspera> readerList = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                FilaEspera e= new FilaEspera();
                Row ro=sheet.getRow(i);
                for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                    Cell ce = ro.getCell(j);
                    if(j==1){
                        //If you have Header in text It'll throw exception because it won't get NumericValue
                        e.setId(ce.getStringCellValue());
                    }
                    if(j==2){
                        e.setNomeLeitor(ce.getStringCellValue());
                    }
                    if(j==3){
                        e.setNomeLivro(ce.getStringCellValue());
                    }
                    if(j==4){
                        e.setDataCadastro(ce.getStringCellValue());
                    }
                }
                readerList.add(e);
            }
            file.close();
            return readerList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Métodos updateReaderFromXLSX, updateEmprestimoFileXLSX, updateFilaEsperaFileXLSX
     * São métodos que atualizam os arquivos, toda ação de cadastro ou atualização
     * Passam por esses métodos, seja para cadastrar o leitor, ou cadastrar emprestimo ou fila de espera
     * Todos esses métodos inserem uma linha nos repectivos arquivos xlsx
     */
    public static void updateReaderFromXLSX(Leitor leitor){

        try {
            FileInputStream inputStream = new FileInputStream(SAMPLE_READER_XLSX_FILE_PATH);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Object[][] readerData = {
                    {leitor.getId(), leitor.getNome(), leitor.getEndereco()},
            };

            int rowCount = sheet.getLastRowNum();

            for (Object[] aBook : readerData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount);

                for (Object field : aBook) {
                    cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }

            }

            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(SAMPLE_READER_XLSX_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }

    }

    public static void updateEmprestimoFileXLSX(Emprestimo emprestimo) throws IOException {

        try {
            FileInputStream inputStream = new FileInputStream(SAMPLE_EMPRESTIMOS_XLSX_FILE_PATH);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Object[][] emprestimoData = {
                    {emprestimo.getLivro(),emprestimo.getDataEmprestimo(), emprestimo.getDevolucao(), emprestimo.getLeitor().getId(), emprestimo.getLeitor().getNome(), emprestimo.getLeitor().getEndereco()},
            };

            int rowCount = sheet.getLastRowNum();

            for (Object[] aEmprestimo : emprestimoData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount);

                for (Object field : aEmprestimo) {
                    cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }

            }

            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(SAMPLE_EMPRESTIMOS_XLSX_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateFilaEsperaFileXLSX(FilaEspera filaEspera) throws IOException {

        try {
            FileInputStream inputStream = new FileInputStream(SAMPLE_FILA_ESPERA_XLSX_FILE_PATH);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Object[][] filaEsperaData = {
                    {filaEspera.getId(),filaEspera.getNomeLeitor(),filaEspera.getNomeLivro(),filaEspera.getDataCadastro()},
            };

            int rowCount = sheet.getLastRowNum();

            for (Object[] aEspera : filaEsperaData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount);

                for (Object field : aEspera) {
                    cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }

            }

            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(SAMPLE_FILA_ESPERA_XLSX_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Métodos printDataBook, printDataReader, printDataEmprestimos, printDataListaEspera
     * São métodos que imprimem no console os dados de livros, leitores, emprestimos e lista de espera
     */
    public static void printDataBook(Livro livro) {
        System.out.println("id : " + livro.getId());
        System.out.println("author_first_name : " + livro.getAutorFirstName());
        System.out.println("author_last_name : " + livro.getAutorLastName());
        System.out.println("book_title : " + livro.getTitulo());
        System.out.println("genre : " + livro.getGenre());
        System.out.println("\n====================================================");
    }

    public static void printDataReader(Leitor leitor) {
        System.out.println("id : " + leitor.getId());
        System.out.println("leitor_name : " + leitor.getNome());
        System.out.println("endereco : " + leitor.getEndereco());
        System.out.println("\n====================================================");
    }

    public static void printDataEmprestimos(Emprestimo emprestimo) {
        System.out.println("livro : " + emprestimo.getLivro());
        System.out.println("dataEmprestimo : " + emprestimo.getDataEmprestimo());
        System.out.println("devolucao : " + emprestimo.getDevolucao());
        System.out.println("Dados do Leitor que pegou emprestado: \n");
        printDataReader(emprestimo.getLeitor());
        System.out.println("\n====================================================");
    }

    public static void printDataListaEspera(FilaEspera filaEspera) {
        System.out.println("id : " + filaEspera.getId());
        System.out.println("nome Leitor : " + filaEspera.getNomeLeitor());
        System.out.println("nome Livro : " + filaEspera.getNomeLivro());
        System.out.println("data Cadastro: " + filaEspera.getDataCadastro());
        System.out.println("\n====================================================");
    }

}
