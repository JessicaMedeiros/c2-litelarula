package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository  extends JpaRepository<Livro, Long> {

    @Query("select e from Livro e")
    List<Livro> todosOsLivros();

    @Query("select l from Livro l where l.languages like :idioma")
    List<Livro> buscarLivrosPeloIdioma(String idioma);


}
