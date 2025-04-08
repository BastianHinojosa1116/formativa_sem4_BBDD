// -------------------- PAQUETE -------------------------

// Indica que esta clase pertenece al paquete raíz del microservicio
package com.example.microservicio_peliculas;

// -------------------- IMPORTACIONES -------------------------

// Importamos la clase SpringApplication que es la encargada de iniciar la aplicación Spring Boot
import org.springframework.boot.SpringApplication;

// Importamos la anotación que configura automáticamente la aplicación como un proyecto Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

// -------------------- CLASE PRINCIPAL -------------------------

/**
 * Clase principal que da inicio al microservicio.
 * Aquí es donde arranca toda la aplicación.
 */
@SpringBootApplication // Indica a Spring Boot que debe hacer:
						// - Escaneo automático de componentes
						// - Configuración automática (autoconfiguration)
						// - Registro de beans en el contexto de Spring
public class MicroservicioPeliculasApplication {

	/**
	 * Método main.
	 * Es el punto de entrada de cualquier aplicación Java.
	 * En este caso, además de ser el método principal, inicia el servidor web
	 * embebido (Tomcat) y
	 * configura automáticamente todo el microservicio.
	 */
	public static void main(String[] args) {
		// Aquí es donde Spring Boot arranca la aplicación
		// Configura las rutas, servicios, controladores y deja lista la API para
		// recibir peticiones HTTP
		SpringApplication.run(MicroservicioPeliculasApplication.class, args);
	}

}

/*
 * @SpringBootApplication
 * 
 * Hace que Spring detecte automáticamente:
 * 
 * Controladores (@RestController)
 * 
 * Servicios (@Service)
 * 
 * Manejadores de excepciones (@ControllerAdvice)
 * 
 * Configura el servidor embebido (Tomcat) sin que tengamos que instalar nada
 * extra.
 * 
 * Método main()
 * 
 * Es el arranque oficial de cualquier aplicación Java.
 * 
 * Pero gracias a SpringApplication.run(...), aquí arranca:
 * 
 * El microservicio.
 * 
 * El servidor web.
 * 
 * Todos los componentes que definimos.
 * 
 * SpringApplication.run()
 * 
 * Llama a Spring Boot para que:
 * 
 * Detecte las clases anotadas.
 * 
 * Levante el contexto.
 * 
 * Inicie el servidor.
 * 
 * Prepare la API para recibir peticiones en http://localhost:8080/
 */