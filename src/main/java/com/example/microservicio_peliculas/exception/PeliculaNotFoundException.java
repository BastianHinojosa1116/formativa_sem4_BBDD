// Indica que este archivo pertenece al paquete de excepciones de nuestro microservicio
package com.example.microservicio_peliculas.exception;

// -------------------- IMPORTACIONES -------------------------

// Importa los códigos de estado HTTP que usaremos para asignar un 404
import org.springframework.http.HttpStatus;

// Permite asociar la excepción a un código de estado HTTP automáticamente
import org.springframework.web.bind.annotation.ResponseStatus;

// -------------------- CLASE -------------------------

/**
 * Excepción personalizada.
 * Esta clase se lanza cuando un usuario intenta buscar una película por ID
 * y esta no existe en la lista.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Hace que, al lanzarla, Spring devuelva directamente un 404 Not Found
public class PeliculaNotFoundException extends RuntimeException {

    /**
     * Constructor de la excepción.
     * Recibe el ID que el usuario buscó y no fue encontrado.
     * 
     * @param id ID de la película que no existe.
     */
    public PeliculaNotFoundException(Long id) {
        // Generamos un mensaje personalizado que luego será visible en la respuesta de
        // error
        super("La película con id " + id + " no fue encontrada");
    }
}

/*
 * ¿Qué es una Excepción personalizada?
 * 
 * Es una clase que creamos nosotros para indicar errores específicos y claros,
 * no solo usar las genéricas de Java.
 * 
 * @ResponseStatus(HttpStatus.NOT_FOUND)
 * 
 * Esta anotación hace que cuando esta excepción sea lanzada, automáticamente
 * devuelva un código 404 Not Found sin que tengamos que especificarlo en cada
 * controlador.
 * 
 * Extiende de RuntimeException
 * 
 * Porque es un error que no depende de compilación sino que ocurre en tiempo de
 * ejecución (cuando buscan una película que no existe).
 * 
 * Mensaje personalizado
 * 
 * "La película con id 99 no fue encontrada"
 * Esto es lo que el usuario o sistema verá al consumir la API cuando ocurra el
 * error.
 */