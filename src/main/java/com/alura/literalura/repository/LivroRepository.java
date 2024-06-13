package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository  extends JpaRepository<Livro, Long> {

    @Query("select e from Livro e")
    List<Livro> todosOsLivros();


}
