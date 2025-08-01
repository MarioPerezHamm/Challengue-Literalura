package com.aluracursos.Challengue_Literalura.modelos;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
@Entity
@Table(name = "libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String autores;
   // private List<String> lenguaje;
    private Integer autorMuerte;
   // private List<String> resumen;
   @Column(columnDefinition = "TEXT")
   private String resumen;
    private Integer descargas;
    private String lenguaje;

    public Libro(){}

    public Libro(DatosLibros d, DatosAutores a){
        this.titulo = d.titulo();
        this.autores = a.autorNombre();
        //this.lenguaje = d.lenguaje();
        this.lenguaje = String.join(",", d.lenguaje());   // de ["en"] a "en"
        this.autorMuerte = a.autorMuerte();
//        this.resumen = d.resumen();
        this.resumen = String.join(" ", d.resumen());
        this.descargas = d.descargas();
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

//    public List<String> getLenguaje() {
//        return lenguaje;
//    }

//    public void setLenguaje(List<String> lenguaje) {
//        this.lenguaje = lenguaje;
//    }

    public Integer getAutorMuerte() {
        return autorMuerte;
    }

    public void setAutorMuerte(Integer autorMuerte) {
        this.autorMuerte = autorMuerte;
    }

//    public List<String> getResumen() {
//        return resumen;
//    }

//    public void setResumen(List<String> resumen) {
//        this.resumen = resumen;
//    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
}
