package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    @Query("select e from Autor e")
    List<Livro> todosOsAutores();


    @Query("select a from Autor a WHERE a.birth_year <= :ano AND a.death_year >= :ano")
    List<Autor> autoresVivosEmDeterminadoAno(int ano);

    Optional<Autor> findByName(String name);

    @Query("select a from Autor a WHERE a.name ilike %:name%")
    Optional<Autor> buscarAutorPeloNome(String name);

}

