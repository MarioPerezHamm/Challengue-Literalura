package com.aluracursos.Challengue_Literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> autores,
        @JsonAlias ("summaries") List<String> resumen,
        @JsonAlias("languages") List<String>  lenguaje,
        @JsonAlias("download_count") Integer descargas
        ) {
}
