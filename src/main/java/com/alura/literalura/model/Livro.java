package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @ManyToOne // Eager fetching for Livro
    private Autor autor;

    private String  languages;

    private Long download_count;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.title = dadosLivro.title();
        this.languages = dadosLivro.languages().get(0);
        this.download_count = dadosLivro.download_count();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return "---------------------------\n" +
                "Titulo: " + title + "\n" +
                "Autor: " + getAutor().getName() + "\n" +
                "Idioma: " + languages + "\n" +
                "Quantidade de downloads: " + download_count + "\n" +
               "---------------------------" + "\n";


    }
}
