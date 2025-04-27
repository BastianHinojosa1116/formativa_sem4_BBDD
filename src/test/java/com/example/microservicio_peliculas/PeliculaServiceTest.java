package com.example.microservicio_peliculas;

import com.example.microservicio_peliculas.model.Pelicula;
import com.example.microservicio_peliculas.exception.PeliculaNotFoundException;
import com.example.microservicio_peliculas.repository.PeliculaRepository;
import com.example.microservicio_peliculas.service.PeliculaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class PeliculaServiceTest {

    private PeliculaRepository peliculaRepository;
    private PeliculaService peliculaService;

    @BeforeEach
    public void setUp() {
        peliculaRepository = mock(PeliculaRepository.class);
        peliculaService = new PeliculaService(peliculaRepository);
    }

    @Test
    public void testObtenerTodas(){
        Pelicula p1 = new Pelicula(1L, "Pelicula 1", 2020, "Director 1", "Accion", "Un mundo perdido");
        Pelicula p2 = new Pelicula(2L, "Pelicula 2", 2025, "Director 2", "Drama", "La venganza");

        when(peliculaRepository.findAll(Sort.by("id").ascending())).thenReturn(Arrays.asList(p1,p2));

        List<Pelicula> resultado = peliculaService.obtenerTodas();

        assertEquals(2, resultado.size()); //esperamos 2 peliculas
        assertEquals("Pelicula 1", resultado.get(0).getTitulo());
    }

    @Test
    public void testObtenerPorId_existente(){
        Pelicula pelicula = new Pelicula(1L,"Pelicula Test" , 2022,"Director Test", "Genero Test", "Sinopsis Test");

        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

        Pelicula resultado = peliculaService.obtenerPorId(1L);

        assertEquals("Pelicula Test", resultado.getTitulo());
        assertEquals(2022, resultado.getAnnio());
    }

    @Test
    public void testObtenerPorId_noExistente() {
        // 🧪 Simulamos que el repositorio no encuentra nada para el ID 99
        when(peliculaRepository.findById(99L)).thenReturn(Optional.empty());

        // 🚨 Esperamos que se lance una excepción del tipo PeliculaNotFoundException
        assertThrows(PeliculaNotFoundException.class, () -> {
            peliculaService.obtenerPorId(99L);
        });
    }
    
}
