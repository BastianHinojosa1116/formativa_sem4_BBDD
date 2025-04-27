// 📦 Paquete base de la aplicación
package com.example.microservicio_peliculas;

// 📥 Importamos el controlador que vamos a probar
import com.example.microservicio_peliculas.controller.PeliculaController;
// 🎞 Entidad Pelicula que será evaluada en las pruebas
import com.example.microservicio_peliculas.model.Pelicula;
// 🧠 Servicio que será simulado con @MockBean
import com.example.microservicio_peliculas.service.PeliculaService;
// 🔗 Assembler que genera enlaces HATEOAS
import com.example.microservicio_peliculas.hateoas.PeliculaModelAssembler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

// 📡 Librerías para crear mocks y validar respuestas HTTP
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 🌐 Librerías para generar enlaces HATEOAS manualmente (simulación del assembler)
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@WebMvcTest(PeliculaController.class) // ✅ Solo se testea la capa del controlador
public class PeliculaControllerTest {

    @Autowired
    private MockMvc mockMvc; // 🚀 Herramienta de Spring para simular peticiones HTTP

    @SuppressWarnings("removal")
    @MockBean

    private PeliculaService peliculaService; // 🎭 Simulamos el servicio
    @SuppressWarnings("removal")
    @MockBean
    private PeliculaModelAssembler peliculaAssembler; // 🔗 Simulamos el ensamblador de HATEOAS

                                                                                  // autenticado
    public void testObtenerPorId() throws Exception {
        // 🎞 Creamos una instancia simulada de la película
        Pelicula pelicula = new Pelicula(1L, "Matrix", 1999, "Wachowski", "Acción", "Ciencia ficción");

        // 🔗 Simulamos el resultado del assembler con enlaces HATEOAS
        EntityModel<Pelicula> peliculaModel = EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaController.class).obtenerPorId(1L)).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).eliminarPelicula(1L)).withRel("delete"),
                linkTo(methodOn(PeliculaController.class).actualizarPelicula(1L, null)).withRel("update"),
                linkTo(methodOn(PeliculaController.class).obtenerTodas()).withRel("all"));

        // 🎭 Simulamos el comportamiento del servicio y el assembler
        when(peliculaService.obtenerPorId(1L)).thenReturn(pelicula);
        when(peliculaAssembler.toModel(pelicula)).thenReturn(peliculaModel);

        // 🚀 Realizamos una solicitud GET al endpoint y validamos la respuesta esperada
        mockMvc.perform(get("/peliculas/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // ✅ Código 200 OK
                .andExpect(jsonPath("$.id").value(1)) // 🧪 Validamos ID
                .andExpect(jsonPath("$.titulo").value("Matrix")) // 🧪 Validamos título
                .andExpect(jsonPath("$.año").value(1999)) // 🧪 Validamos año
                .andExpect(jsonPath("$.genero").value("Acción")) // 🧪 Validamos género
                .andExpect(jsonPath("$._links.self.href").exists()) // 🧪 Validamos que exista enlace "self"
                .andExpect(jsonPath("$._links.delete.href").exists()) // 🧪 Validamos que exista enlace "delete"
                .andExpect(jsonPath("$._links.update.href").exists()); // 🧪 Validamos que exista enlace "update"
    }
}
