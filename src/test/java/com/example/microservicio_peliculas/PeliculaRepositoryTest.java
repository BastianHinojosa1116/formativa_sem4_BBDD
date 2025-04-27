// 📦 Declaramos el paquete del proyecto. Esto permite mantener la organización del código.
package com.example.microservicio_peliculas;

// 📥 Importamos la entidad Pelicula para poder trabajar con ella en el test.
import com.example.microservicio_peliculas.model.Pelicula;
// 📥 Importamos el repositorio que queremos probar.
import com.example.microservicio_peliculas.repository.PeliculaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// 🧪 Esta anotación es clave: configura automáticamente un entorno de pruebas solo para JPA (base de datos).
// Utiliza una base de datos embebida en memoria (H2) sin necesidad de conectarse a Oracle ni a ninguna BD externa.
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 🧪 Esta clase contiene una prueba unitaria para verificar el funcionamiento del repositorio PeliculaRepository.
// El objetivo es asegurar que podemos guardar y luego recuperar correctamente una película desde la base de datos.
@DataJpaTest
public class PeliculaRepositoryTest {

    // 🧱 Inyección automática del repositorio real, pero usando una base de datos
    // embebida en memoria (H2).
    // Esto nos permite probar el comportamiento real del repositorio sin afectar
    // datos reales.
    @Autowired
    private PeliculaRepository peliculaRepository;

    // ✅ Método de prueba: guarda una película y luego intenta buscarla por ID.
    // Verifica que:
    // 1. Se haya guardado correctamente.
    // 2. Los datos recuperados sean los mismos.
    @Test
    public void testGuardarYBuscar() {
        // 🎬 Creamos una instancia de película con datos de ejemplo.
        Pelicula pelicula = new Pelicula(5L, "Titanic", 1997, "Cameron", "Romance", "Historia del barco");

        // 💾 Guardamos la película en la base de datos embebida.
        peliculaRepository.save(pelicula);

        // 🔍 Buscamos la película recién guardada usando su ID.
        Optional<Pelicula> encontrada = peliculaRepository.findById(5L);

        // ✅ Verificamos que la película efectivamente fue encontrada.
        assertTrue(encontrada.isPresent());

        // 🧪 Verificamos que los datos coincidan con lo que se guardó (en este caso, el
        // título).
        assertEquals("Titanic", encontrada.get().getTitulo());
    }
}
