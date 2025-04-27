// Indica que esta clase pertenece al paquete de servicios
package com.example.microservicio_peliculas.service;

// -------------------- IMPORTACIONES -------------------------

// Importamos la clase Pelicula (entidad)
import com.example.microservicio_peliculas.model.Pelicula;

// Importamos la interfaz Repository para acceder a la base de datos
import com.example.microservicio_peliculas.repository.PeliculaRepository;

// Importamos la excepción personalizada
import com.example.microservicio_peliculas.exception.PeliculaNotFoundException;

import org.springframework.data.domain.Sort;

// Importamos utilidades de Spring

import org.springframework.stereotype.Service;

// Importamos utilidades para listas
import java.util.List;

// -------------------- CLASE -------------------------

/**
 * Servicio encargado de manejar la lógica de negocio relacionada a las
 * películas.
 * Ahora utiliza una base de datos real (Oracle) en lugar de una lista en
 * memoria.
 */

@Service // Indica a Spring que esta clase será un servicio gestionado automáticamente
         // (Bean)
public class PeliculaService {

        private final PeliculaRepository peliculaRepository;

        public PeliculaService(PeliculaRepository peliculaRepository) {
                this.peliculaRepository = peliculaRepository;
        }

        // -------------------- ATRIBUTOS -------------------------

   

        // -------------------- MÉTODOS -------------------------

        /**
         * Devuelve la lista completa de películas desde la base de datos.
         * 
         * @return Lista de películas obtenidas desde Oracle.
         */
        public List<Pelicula> obtenerTodas() {
                return peliculaRepository.findAll(Sort.by("id").ascending());
            }
            

        /**
         * Busca una película por su ID consultando en Oracle.
         * 
         * @param id ID de la película buscada.
         * @return Película encontrada o lanza una excepción si no existe.
         */
        public Pelicula obtenerPorId(Long id) {
                return peliculaRepository.findById(id)
                                .orElseThrow(() -> new PeliculaNotFoundException(id)); // ✔ Aquí corregimos pasando el
                                                                                       // ID directamente
        }

        public Pelicula actualizar(Long id, Pelicula peliculaActualizada) {
               

                Pelicula existente = peliculaRepository.findById(id)
                                .orElseThrow(() -> new PeliculaNotFoundException(id));

                existente.setTitulo(peliculaActualizada.getTitulo());
                existente.setAnnio(peliculaActualizada.getAnnio());
                existente.setDirector(peliculaActualizada.getDirector());
                existente.setGenero(peliculaActualizada.getGenero());
                existente.setSinopsis(peliculaActualizada.getSinopsis());

                return peliculaRepository.save(existente);
        }

        public void eliminar(Long id) {
                

                Pelicula existente = peliculaRepository.findById(id)
                                .orElseThrow(() -> new PeliculaNotFoundException(id));

                                peliculaRepository.delete(existente);
        }
}

/*
 * ¿Qué cambió en este servicio?
 * 
 * Ahora no usamos una lista simulada.
 * 
 * Consultamos directamente en la base de datos de Oracle gracias a JPA.
 * 
 * repo.findAll()
 * 
 * Recupera automáticamente todas las películas desde la tabla "peliculas".
 * 
 * repo.findById()
 * 
 * Busca por ID directamente en la base y devuelve la película si existe.
 * Si no, lanza una excepción que será atrapada por el manejador
 * GlobalExceptionHandler.
 */
