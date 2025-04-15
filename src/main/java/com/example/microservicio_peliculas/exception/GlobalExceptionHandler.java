package com.example.microservicio_peliculas.exception;

import com.example.microservicio_peliculas.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Usa ResponseWrapper para mantener consistencia en las respuestas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura cuando una película no se encuentra (ID inválido).
     */
    @ExceptionHandler(PeliculaNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> manejarPeliculaNoEncontrada(PeliculaNotFoundException ex) {
        ResponseWrapper<String> respuesta = new ResponseWrapper<>(
                "NOT FOUND",
                0,
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    /**
     * Captura errores de argumentos inválidos, por ejemplo al intentar agregar
     * una película con un ID ya existente.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        ResponseWrapper<String> respuesta = new ResponseWrapper<>(
                "BAD REQUEST",
                0,
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura cualquier otra excepción no controlada (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<String>> manejarExcepcionGeneral(Exception ex) {
        ResponseWrapper<String> respuesta = new ResponseWrapper<>(
                "INTERNAL SERVER ERROR",
                0,
                List.of("Ha ocurrido un error inesperado. Intenta más tarde.")
        );
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
