package com.aluracursos.Challengue_Literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(
        @JsonAlias("name") String autorNombre,
        @JsonAlias("death_year") Integer autorMuerte
) {
}
