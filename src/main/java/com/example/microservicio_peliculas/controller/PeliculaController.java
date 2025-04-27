// Indica el paquete al que pertenece este archivo
package com.example.microservicio_peliculas.controller;

import com.example.microservicio_peliculas.hateoas.PeliculaModelAssembler;

// -------------------- IMPORTACIONES -------------------------

import com.example.microservicio_peliculas.model.Pelicula;
import com.example.microservicio_peliculas.model.ResponseWrapper;
import com.example.microservicio_peliculas.service.PeliculaService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    private final PeliculaModelAssembler assembler;


    public PeliculaController(PeliculaService peliculaService, PeliculaModelAssembler assembler) {
        this.peliculaService = peliculaService;
        this.assembler = assembler;
    }

    // --------------------- ENDPOINT 1 ----------------------

     // ✅ Obtener todas las películas con modelo enriquecido HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pelicula>>> obtenerTodas() {
        

        List<Pelicula> peliculas = peliculaService.obtenerTodas();

        if (peliculas.isEmpty()) {
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CollectionModel.empty());
        }

        // Convertimos cada película a EntityModel y agregamos enlaces
        List<EntityModel<Pelicula>> modelos = peliculas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        // Agregamos también un enlace al "self" del recurso (GET /peliculas)
        return ResponseEntity.ok(CollectionModel.of(modelos,
                linkTo(methodOn(PeliculaController.class).obtenerTodas()).withSelfRel()));
    } /**
     * Obtiene TODAS las películas disponibles.
     * Si la lista está vacía, devuelve un mensaje informativo.
     */


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

    // ✅ Actualizar película
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> actualizarPelicula(@PathVariable Long id,
          Pelicula peliculaActualizada) {
       

        Pelicula actualizada = peliculaService.actualizar(id, peliculaActualizada);

        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    // ✅ Eliminar película (sin HATEOAS, porque no hay cuerpo)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> eliminarPelicula(@PathVariable Long id) {
       

        peliculaService.eliminar(id);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        "Película eliminada exitosamente",
                        0,
                        null));
    }
}
