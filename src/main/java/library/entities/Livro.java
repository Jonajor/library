package library.entities;

import com.opencsv.bean.CsvBindByName;

public class Livro {

    /**
     * A anotação @CsvBindByName é utilizada para trabalhar com a leitura de arquivos .csv
     * Comecei trabalhando com os dois tipos xlsx e csv
     * Mas no decorer do desenvolvimento preferi seguir dando foco no xlsx
     */
    @CsvBindByName
    private String id;
    @CsvBindByName(column = "author_first_name", required = true)
    private String autorFirstName;
    @CsvBindByName(column = "author_last_name", required = true)
    private String autorLastName;
    @CsvBindByName(column = "book_title", required = true)
    private String titulo;
    @CsvBindByName
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutorFirstName() {
        return autorFirstName;
    }

    public void setAutorFirstName(String autorFirstName) {
        this.autorFirstName = autorFirstName;
    }

    public String getAutorLastName() {
        return autorLastName;
    }

    public void setAutorLastName(String autorLastName) {
        this.autorLastName = autorLastName;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
