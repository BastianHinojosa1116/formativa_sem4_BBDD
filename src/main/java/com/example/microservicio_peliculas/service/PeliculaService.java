// Indica que esta clase pertenece al paquete de servicios
package com.example.microservicio_peliculas.service;

// -------------------- IMPORTACIONES -------------------------

// Importamos la clase Pelicula (entidad)
import com.example.microservicio_peliculas.model.Pelicula;

// Importamos la interfaz Repository para acceder a la base de datos
import com.example.microservicio_peliculas.repository.PeliculaRepository;

// Importamos la excepción personalizada
import com.example.microservicio_peliculas.exception.PeliculaNotFoundException;

// Importamos utilidades de Spring
import org.springframework.beans.factory.annotation.Autowired;
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

        // -------------------- ATRIBUTOS -------------------------

        // Inyección automática del repositorio que conecta con Oracle
        @Autowired
        private PeliculaRepository repo;

        // -------------------- MÉTODOS -------------------------

        /**
         * Devuelve la lista completa de películas desde la base de datos.
         * 
         * @return Lista de películas obtenidas desde Oracle.
         */
        public List<Pelicula> obtenerTodas() {
                return repo.findAll();
        }

        /**
         * Busca una película por su ID consultando en Oracle.
         * 
         * @param id ID de la película buscada.
         * @return Película encontrada o lanza una excepción si no existe.
         */
        public Pelicula obtenerPorId(Long id) {
                return repo.findById(id)
                                .orElseThrow(() -> new PeliculaNotFoundException(id)); // ✔ Aquí corregimos pasando el
                                                                                       // ID directamente
        }

        /**
         * Agrega una nueva película al repositorio.
         * 
         * @param pelicula Objeto Pelicula que se va a guardar.
         * @return La película guardada (con ID si se genera automáticamente).
         */
        public Pelicula agregar(Pelicula pelicula) {
                if (repo.existsById(pelicula.getId())) {
                    // Si ya existe una película con ese ID, lanzamos una excepción
                    throw new IllegalArgumentException("Ya existe una película con el ID " + pelicula.getId());
                }
            
                // Si no existe, se guarda normalmente
                return repo.save(pelicula);
            }

        /**
         * Elimina una película por su ID.
         * 
         * @param id ID de la película a eliminar.
         */
        public void eliminar(Long id) {
                if (!repo.existsById(id)) {
                        throw new PeliculaNotFoundException(id);
                }
                repo.deleteById(id);
        }

        /**
         * Actualiza una película existente.
         * 
         * @param id            ID de la película a actualizar.
         * @param nuevaPelicula Datos nuevos de la película.
         * @return La película actualizada.
         */
        public Pelicula actualizar(Long id, Pelicula nuevaPelicula) {
                Pelicula existente = repo.findById(id)
                                .orElseThrow(() -> new PeliculaNotFoundException(id));

                // Actualizar campos
                existente.setTitulo(nuevaPelicula.getTitulo());
                existente.setAnnio(nuevaPelicula.getAnnio());
                existente.setDirector(nuevaPelicula.getDirector());
                existente.setGenero(nuevaPelicula.getGenero());
                existente.setSinopsis(nuevaPelicula.getSinopsis());

                return repo.save(existente);
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
