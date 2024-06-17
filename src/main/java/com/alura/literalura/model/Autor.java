package com.alura.literalura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int birth_year;
    private int death_year;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(int birth_year, int death_year, String name) {
        this.birth_year = birth_year;
        this.death_year = death_year;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        List<String> titulos = livros.stream()
                .map(livro -> livro.getTitle())
                .collect(Collectors.toList());

        return "---------------------------\n" +
                "Autor: " + name + "\n" +
                "Data de nascimento: " + birth_year + "\n" +
                "Falecimento: " + death_year + "\n" +
                "Livros: " + titulos + "\n" +
                "---------------------------\n";
    }



}