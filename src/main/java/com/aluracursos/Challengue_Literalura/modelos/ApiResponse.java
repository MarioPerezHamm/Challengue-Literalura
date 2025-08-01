package com.aluracursos.Challengue_Literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(
        @JsonAlias("results") List<DatosLibros> resultados
) {
}
