package com.example.libros.controlador;

import java.util.List;

import com.example.libros.IncializarDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.libros.entidad.Categoria;
import com.example.libros.entidad.Libro;
import com.example.libros.servicio.ServicioLibros;

import javax.naming.Binding;

@Controller
public class ControladorLibro {
	
	@Autowired
	ServicioLibros serviciolibros;
	@Autowired
	IncializarDatos datos;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("mensaje", " DWES :: RA3");
		return "index";
	}

	// Obtengo la lista de libros del servicio y la mando al Model
	// La URL mapeada será "/libros" como pide el examen
	@GetMapping("/libros")
	public String libros(Model model) {
		model.addAttribute("libros", serviciolibros.obtenerLibros());
		model.addAttribute("categorias", serviciolibros.obtenerCategorias());
		return "libros";
	}

	// Funcionalidad de filtrar por categoría (Ejercicio 3)
	@GetMapping("/filtrar")
	public String filtrarLibrosPorCategoria(@RequestParam String categoria, Model model) {
		model.addAttribute("libros", serviciolibros.obtenerLibros(categoria));
		return "libros";
	}

	// Funcionalidad que lleva al formulario al clickear el boton de "Añadir"
	@GetMapping("/formulario")
	public String mostrarFormulario(Model model) {
		model.addAttribute("libro", new Libro());
		return "formulario";
	}
	// Funcionalidad para "hacer commit" del libro que hemos creado
	@PostMapping("/formulario")
	public String agregarLibro(@ModelAttribute("libro") Libro libro, BindingResult result) {
		// Validaciones
		if (libro.getId().toString().trim().isEmpty()) {
			result.rejectValue("id", "error", "El ID no puede estar en blanco");
		}
		if (libro.getAutor().trim().isEmpty()) {
			result.rejectValue("autor", "error", "El autor no puede estar en blanco");
		}
		if (libro.getNombre().trim().isEmpty()) {
			result.rejectValue("nombre", "error", "El nombre no puede estar en blanco");
		}
		if (libro.getPrecio() <= 0) {
			result.rejectValue("precio", "error", "El precio debe ser un número positivo");
		}
		if (libro.getCategoria().toString().trim().isEmpty()) {
			result.rejectValue("categoria", "error", "La categoría no puede estar en blanco");
		}

		if (result.hasErrors()) {
			// Devuelve al formulario si hay errores de validación
			return "formulario";
		}

		// Guarda el libro si no hay errores de validación
		serviciolibros.guardarLibro(libro);
		return "redirect:/libros";
	}


}
