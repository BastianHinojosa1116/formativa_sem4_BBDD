package com.example.microservicio_peliculas.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // Importa funciones para generar enlaces

import com.example.microservicio_peliculas.controller.PeliculaController;
import com.example.microservicio_peliculas.model.Pelicula;

import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Esta clase convierte una entidad Pelicula en un EntityModel<Pelicula>
 * enriquecido con enlaces HATEOAS.
 * 
 * Implementa RepresentationModelAssembler, que es una interfaz de Spring
 * HATEOAS
 * para transformar objetos de dominio (Pelicula) en recursos RESTful
 * enriquecidos.
 */
@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {

    /**
     * Este método transforma una Pelicula en un EntityModel con enlaces.
     * Incluye:
     * ✅ self → Ver esta película
     * 🗑 delete → Eliminar la película
     * 🔁 update → Actualizar la película
     * 📋 all → Ver todas las películas
     */
    @Override
    public @NonNull EntityModel<Pelicula> toModel(@NonNull Pelicula pelicula) {
        return EntityModel.of(
                pelicula, // Entidad original

                // Enlace al detalle de la película (GET /peliculas/{id})
                linkTo(methodOn(PeliculaController.class)
                        .obtenerPorId(pelicula.getId()))
                        .withSelfRel(),

                // Enlace para eliminar (DELETE /peliculas/{id})
                linkTo(methodOn(PeliculaController.class)
                        .eliminarPelicula(pelicula.getId()))
                        .withRel("delete"),

                // Enlace para actualizar (PUT /peliculas/{id}) – cuerpo ignorado aquí
                linkTo(methodOn(PeliculaController.class)
                        .actualizarPelicula(pelicula.getId(), null))
                        .withRel("update"),

                // Enlace para ver todas las películas (GET /peliculas)
                linkTo(methodOn(PeliculaController.class)
                        .obtenerTodas())
                        .withRel("all"));
    }
}
