// Indica que esta clase pertenece al paquete model (donde se encuentran las clases que representan objetos o entidades)
package com.example.microservicio_peliculas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// -------------------- IMPORTACIONES -------------------------

// Librería de Lombok que genera automáticamente los métodos getters y setters
import lombok.AllArgsConstructor;

// Lombok genera automáticamente métodos como toString(), equals(), hashCode(), getters y setters
import lombok.Data;

// Lombok genera un constructor vacío sin parámetros
import lombok.NoArgsConstructor;

// -------------------- CLASE -------------------------

/**
 * Clase que representa a una Película.
 * Es el "molde" de cada objeto de tipo Película que manejará nuestro
 * microservicio.
 */
@Data // Lombok: genera automáticamente todos los getters, setters, toString(),
      // equals(), hashCode()
@AllArgsConstructor // Lombok: genera un constructor con todos los atributos como parámetros
@NoArgsConstructor // Lombok: genera un constructor sin parámetros (vacío)
@Entity // 🔵 Indicamos que es una entidad de base de datos
@Table(name = "peliculas") // 🔵 Mapeamos a la tabla "peliculas"
public class Pelicula {

    // Identificador único de la película
    @Id // 🔵 Indicamos que esta es la Primary Key
    private Long id;

    // Título de la película
    private String titulo;

    // Año de estreno de la película
    private int annio;

    // Director de la película
    private String director;

    // Género de la película (Ejemplo: Acción, Drama, Ciencia Ficción)
    private String genero;

    // Sinopsis o descripción corta de la película
    private String sinopsis;
}

/*
 * ¿Qué es esta clase?
 * 
 * Representa a una Película como un objeto.
 * 
 * Cada vez que creemos una nueva película tendrá estos atributos.
 * 
 * ¿Por qué usamos Lombok?
 * 
 * Nos ahorra escribir mucho código repetitivo.
 * 
 * Genera automáticamente:
 * 
 * getId(), getTitulo(), setAño(), etc.
 * 
 * toString(), equals(), hashCode()
 * 
 * Constructor vacío y constructor completo.
 * 
 * Atributos:
 * 
 * Cada atributo es una característica de la película.
 * 
 * Estos mismos atributos serán los que aparecerán en las respuestas JSON cuando
 * hagamos un GET.
 */