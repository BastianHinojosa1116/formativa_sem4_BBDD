// Indica el paquete al que pertenece este archivo
package com.example.microservicio_peliculas.controller;

// -------------------- IMPORTACIONES -------------------------

import com.example.microservicio_peliculas.model.Pelicula;
import com.example.microservicio_peliculas.model.ResponseWrapper;
import com.example.microservicio_peliculas.service.PeliculaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// --------------------- DEFINICIÓN DEL CONTROLADOR ----------------------

/**
 * Esta clase es un CONTROLADOR REST.
 * Su responsabilidad es recibir las solicitudes HTTP relacionadas a las
 * películas.
 */

@RestController // Controlador REST
@RequestMapping("/peliculas") // Prefijo base de todas las rutas de este controlador
public class PeliculaController {

    // -------------------- INYECCIÓN DEL SERVICIO --------------------

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    // --------------------- ENDPOINT 1 ----------------------

    /**
     * Obtiene TODAS las películas disponibles.
     * Si la lista está vacía, devuelve un mensaje informativo.
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<Pelicula> peliculas = peliculaService.obtenerTodas();

        if (peliculas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay películas registradas actualmente");
        }

        ResponseWrapper<Pelicula> respuesta = new ResponseWrapper<>(
                "OK",
                peliculas.size(),
                peliculas);

        return ResponseEntity.ok(respuesta);
    }

    // --------------------- ENDPOINT 2 ----------------------

    /**
     * Obtiene una película según su ID.
     * Si no la encuentra, lanza una excepción personalizada capturada por el
     * GlobalExceptionHandler.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Pelicula>> obtenerPorId(@PathVariable Long id) {
        Pelicula pelicula = peliculaService.obtenerPorId(id);
    
        ResponseWrapper<Pelicula> respuesta = new ResponseWrapper<>(
                "OK",
                1,
                List.of(pelicula));
    
        return ResponseEntity.ok(respuesta);
    }
}
