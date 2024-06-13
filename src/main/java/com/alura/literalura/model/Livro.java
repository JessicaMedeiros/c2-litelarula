package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<String> subjects;


    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> authors = new ArrayList<>();

    private List<String>  languages;

    private Long download_count;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.title = dadosLivro.title();
        this.languages = dadosLivro.languages();
        this.authors = dadosLivro.authors();
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

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = Collections.singletonList(subjects);
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }





    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }


    public List<Autor> getAuthors() {
        return authors;
    }


    public void setAuthors(List<Autor> authors) {
        authors.forEach(e -> e.setLivro(this));
        this.authors = authors;
    }


    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return  "titulo='" + title + '\'' +
                ", download_count=" + download_count +
                ", languages=" + languages + '\'';


    }
}
