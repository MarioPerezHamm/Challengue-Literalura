package com.aluracursos.Challengue_Literalura.principal;

import com.aluracursos.Challengue_Literalura.modelos.ApiResponse;
import com.aluracursos.Challengue_Literalura.modelos.DatosAutores;
import com.aluracursos.Challengue_Literalura.modelos.DatosLibros;
import com.aluracursos.Challengue_Literalura.modelos.Libro;
import com.aluracursos.Challengue_Literalura.repository.LibroRepository;
import com.aluracursos.Challengue_Literalura.service.ConsumoAPI;
import com.aluracursos.Challengue_Literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private LibroRepository repository;
//    private List<Libro> libros;
private List<Libro> libros = new ArrayList<>();

    public Principal(LibroRepository repository) {

        this.repository = repository;
    }


    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    =======================================================
                    1 - Buscar libro 
                    2 - Ver libros registrados
                    3 - Ver autores registrados
                    4 - ver libros segun idioma
                    5 - Top 10 libro mas descargados
                    
                    0 - salir
                    =======================================================                  
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    verLibrosRegistrados();
                    break;
                case 3:
                    verAutoresRegistrados();
                    break;
                case 4:
                    verLibrosPorIdioma();
                    break;
                case 5:
                    verTop10LibrosDescargados();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;

            }


        }
    }


    private void buscarLibroWeb() {
        System.out.println("Escriba el nombre del libro que desea buscar:");
        String nombreLibro = teclado.nextLine();
        String urlBusqueda = URL_BASE + nombreLibro.replace(" ", "+");

        try {
            // 1. Consumir la API
            String json = consumoAPI.obtenerDatos(urlBusqueda);

            // 2. Convertir JSON a ApiResponse
            ApiResponse respuesta = conversor.obtenerDatos(json, ApiResponse.class);

            // 3. Obtener el primer resultado
            Optional<DatosLibros> libroCoincidente = respuesta.resultados().stream()
                    .filter(libro -> libro.titulo() != null &&
                            libro.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                    .findFirst();

            if (libroCoincidente.isPresent()) {
                DatosLibros datosLibro = libroCoincidente.get();

                // Validar si hay al menos un autor
                if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
                    DatosAutores autor = datosLibro.autores().get(0);
                    String tituloLibro = datosLibro.titulo();

                    Optional<Libro> libroExistente = repository.findByTituloIgnoreCase(tituloLibro);

                    if (libroExistente.isPresent()) {
                        System.out.println("\n El libro ya está registrado en la base de datos. No se puede volver a registrar.");
                    } else {
                        // Crear libro y añadirlo a la lista
                        Libro libro = new Libro(datosLibro, autor);
                        libros.add(libro);
                        //Guardar en la base de datos
                        repository.save(libro);

                        // Mostrar información
                        System.out.println("\n===== Libro Encontrado =====");
                        System.out.println("Título: " + libro.getTitulo());
                        System.out.println("Autor: " + libro.getAutores());
                        System.out.println("Idioma: " + libro.getLenguaje());
                        System.out.println("Año de muerte del autor: " + libro.getAutorMuerte());
                        System.out.println("Numero de descargas: " + libro.getDescargas());

                        System.out.println("\n Libro guardado exitosamente en la base de datos.");

                        String resumen = (libro.getResumen() != null && !libro.getResumen().isBlank())
                                ? libro.getResumen()
                                : "Resumen no disponible.";

                        System.out.println("Resumen: " + resumen);
                    }

                } else {
                    System.out.println("No se encontró información del autor para este libro.");
                }

            } else {
                System.out.println("No se encontró ningún libro con ese nombre.");
            }

        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar el libro: " + e.getMessage());
        }

    }

    private void verLibrosRegistrados() {
        libros = repository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(libro -> System.out.println("Libro: "+libro.getTitulo()));
    }
    private void verAutoresRegistrados() {
        List<String> autores = repository.encontrarAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(autor -> {
            String[] partes = autor.split(", ");
            if (partes.length == 2) {
                String nombreCompleto = partes[1] + " " + partes[0];
                System.out.println("Autor: " + nombreCompleto);
            } else {
                System.out.println("Autor: " + autor);
            }
        });
    }
    private void verLibrosPorIdioma() {
        System.out.println("""
                            Ingrese el código del idioma:
                              es  -> Español
                              en  -> Inglés
                              fr  -> Francés
                              de  -> Alemán
                              pt  -> Portugués
                              it  -> Italiano
                            Ejemplo: 'es' para español
                            """);
        String idioma = teclado.nextLine();

        List<Libro> librosPorIdioma = repository.findByLenguaje(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
            return;
        }

        librosPorIdioma.forEach(libro ->
                System.out.println("Libro: " + libro.getTitulo() + " | Autor: " + libro.getAutores()));
    }

    private void verTop10LibrosDescargados() {
        List<Libro> topLibros = repository.findTop10ByOrderByDescargasDesc();

        if (topLibros.isEmpty()) {
            System.out.println("No hay libros registrados para mostrar el top.");
            return;
        }

        int contador = 1;
        for (Libro libro : topLibros) {
            System.out.printf("%d. %s | Autor: %s | Descargas: %d\n",
                    contador++, libro.getTitulo(), libro.getAutores(), libro.getDescargas());
        }
    }

}




