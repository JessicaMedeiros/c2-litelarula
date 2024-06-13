package com.alura.literalura.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.mapping.Any;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
         String title,
         ArrayList<String> languages,
         List<DadosAutor> authors,
        Long download_count

) {
}
