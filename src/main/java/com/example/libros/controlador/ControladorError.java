package com.example.libros.controlador;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorError implements ErrorController  {
    @RequestMapping("/error")
    public String handleError(Model model) {
        // Agrega el mensaje de error al modelo
        model.addAttribute("errorMessage", "Ocurrió un error. Se ha producido un error.");

        // Plantilla (error.html)
        return "error";
    }

}
