// Indica la ubicación de este archivo en el paquete del proyecto
package com.example.microservicio_peliculas.exception;

// -------------------- IMPORTACIONES -------------------------

// Importa los códigos de estado HTTP (200, 404, 500, etc.)
import org.springframework.http.HttpStatus;

// Permite construir respuestas HTTP completas (body + status)
import org.springframework.http.ResponseEntity;

// Habilita esta clase como manejador de excepciones globales
import org.springframework.web.bind.annotation.ControllerAdvice;

// Marca los métodos que capturan excepciones específicas
import org.springframework.web.bind.annotation.ExceptionHandler;

// Clases utilitarias para crear el cuerpo de la respuesta personalizada
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// -------------------- CLASE -------------------------

/**
 * Manejador global de excepciones.
 * Esta clase permite capturar y personalizar errores que ocurren en cualquier
 * controlador REST.
 */
@ControllerAdvice // Hace que esta clase esté atenta a todas las excepciones de la aplicación
public class GlobalExceptionHandler {

    /**
     * Método que captura cualquier PeliculaNotFoundException que ocurra en la
     * aplicación.
     * Retorna una respuesta HTTP bien estructurada con código 404.
     */
    @ExceptionHandler(PeliculaNotFoundException.class) // Captura solo las PeliculaNotFoundException
    public ResponseEntity<Object> handlePeliculaNotFound(PeliculaNotFoundException ex) {

        // Creamos un mapa (tipo JSON) para construir la respuesta personalizada
        Map<String, Object> body = new HashMap<>();

        // Agregamos la fecha y hora exacta en la que ocurrió el error
        body.put("timestamp", LocalDateTime.now());

        // Indicamos el código HTTP asociado (404 Not Found)
        body.put("status", HttpStatus.NOT_FOUND.value());

        // Mensaje estándar del tipo de error
        body.put("error", "Not Found");

        // Mensaje específico de la excepción lanzada (ej: "La película con id 99 no fue
        // encontrada")
        body.put("message", ex.getMessage());

        // Retornamos la respuesta como un ResponseEntity con status 404
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

/*
 * Cuando los usuarios o sistemas consumen nuestra API y algo sale mal, es mucho
 * más profesional devolverles un mensaje ordenado y claro como este, en vez de
 * un error genérico de Java
 * 
 * @ControllerAdvice
 * 
 * Sirve para manejar errores globalmente sin tener que escribir el mismo código
 * en cada controlador.
 * 
 * @ExceptionHandler
 * 
 * Detecta cuándo ocurre una excepción específica y permite construir una
 * respuesta personalizada.
 * 
 * Map<String, Object>
 * 
 * Nos permite crear un objeto que será convertido en JSON automáticamente.
 * 
 * LocalDateTime.now()
 * 
 * Incluimos la fecha y hora exacta del error, útil para debug o trazabilidad.
 * 
 * ResponseEntity
 * 
 * Nos deja definir tanto el cuerpo (contenido) como el código de estado (404,
 * 500, etc.) de la respuesta.
 */
