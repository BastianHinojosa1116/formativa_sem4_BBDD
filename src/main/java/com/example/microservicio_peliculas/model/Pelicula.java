// Indica que esta clase pertenece al paquete model (donde se encuentran las clases que representan objetos o entidades)
package com.example.microservicio_peliculas.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
     @Id
     @NotNull(message = "El ID no puede ser nulo")
     private Long id;
 
     // Título de la película
     @NotBlank(message = "El título no puede estar vacío")
     @Size(max = 100, message = "El título no debe tener más de 100 caracteres")
     private String titulo;
 
     // Año de estreno de la película
     @Min(value = 1888, message = "El año debe ser mayor o igual a 1888") 
     @Max(value = 2100, message = "El año debe ser menor o igual a 2100")
     private int annio;
 
     // Director de la película
     @NotBlank(message = "El nombre del director no puede estar vacío")
     @Size(max = 60, message = "El nombre del director no debe tener más de 60 caracteres")
     private String director;
 
     // Género de la película
     @NotBlank(message = "El género no puede estar vacío")
     @Size(max = 30, message = "El género no debe tener más de 30 caracteres")
     private String genero;
 
     // Sinopsis de la película
     @NotBlank(message = "La sinopsis no puede estar vacía")
     @Size(max = 500, message = "La sinopsis no debe tener más de 500 caracteres")
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