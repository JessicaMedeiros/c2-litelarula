package com.alura.literalura.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
         String title,
         List<Autor> authors,
         List<String> languages,
        Long download_count

) {
}
