package com.aluracursos.Challengue_Literalura.repository;

import com.aluracursos.Challengue_Literalura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT DISTINCT l.autores FROM Libro l")
    List<String> encontrarAutores();

    List<Libro> findByLenguaje(String lenguaje);
    List<Libro> findTop10ByOrderByDescargasDesc();

}
