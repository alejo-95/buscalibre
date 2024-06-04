package libreria.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import libreria.model.entity.Ejemplar;
import libreria.model.entity.Libro;
import libreria.model.service.LibreriaServiceIface;

@Controller
@RequestMapping("/libreria")
@SessionAttributes("ejemplar")
public class EjemplarController {

    @Autowired
    private LibreriaServiceIface libreriaServiceIface;

    @GetMapping("/ejemplarnuevo/{id}")
    public String ejemplarNuevo(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
        Libro libro = libreriaServiceIface.buscarLibroPorId(id);
        if (libro == null) {
            flash.addFlashAttribute("error", "El libro no existe en la base de datos");
            return "redirect:/libreria/libroslistar";
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setLibro(libro);

        model.addAttribute("titulo", "Nueva ejemplar");
        model.addAttribute("accion", "Guardar ejemplar");
        model.addAttribute("ejemplar", ejemplar);
        return "ejemplar/formulario_ejemplar";
    }

    @PostMapping("/ejemplarguardar")
    public String ejemplarGuardar(@Valid @ModelAttribute("ejemplar") Ejemplar ejemplar, BindingResult result,
            Model model, SessionStatus status, RedirectAttributes flash) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo ejemplar");
            model.addAttribute("accion", "Guardar ejemplar");
            model.addAttribute("info", "Complemente o corrija la información de los campos del formulario");
            return "ejemplar/formulario_ejemplar";
        }

        libreriaServiceIface.guardarEjemplar(ejemplar);
        status.setComplete();
        flash.addFlashAttribute("success", "Ejemplar guardado con éxito!");
        return "redirect:/libreria/libroconsultar/" + ejemplar.getLibro().getId();
    }

    @GetMapping("/eliminarejemplar/{id}")
    public String eliminarEjemplar(@PathVariable(name = "id") Long id, Model model,
            RedirectAttributes flash) {
        Ejemplar ejemplar = libreriaServiceIface.buscarEjemplarPorId(id);
        if (ejemplar == null) {
            flash.addFlashAttribute("error", "El ejemplar no existe en la base de datos");
            return "redirect:/libreria/libroslistar/";
        }
        libreriaServiceIface.eliminarEjemplarPorId(id);
        flash.addFlashAttribute("info", "El ejemplar fue eliminado con éxito!");
        return "redirect:/libreria/libroconsultar/" + ejemplar.getLibro().getId();
    }

    @GetMapping("/ejemplarmodificar/{id}")
	public String libroFormModificar(@PathVariable(value = "id") Long id, 
			Model model, RedirectAttributes flash) {
		Ejemplar ejemplar = null;
		if (id > 0) {
			ejemplar = libreriaServiceIface.buscarEjemplarPorId(id);
			if (ejemplar == null) {
				flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
				return "redirect:/libreria/libroslistar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
			return "redirect:/libreria/libroslistar";
		}
		model.addAttribute("accion", "Modificar");
		model.addAttribute("titulo", "Modificar ejemplar");
		model.addAttribute("ejemplar", ejemplar);
		return "ejemplar/formulario_ejemplar";
	}
    // @GetMapping("/productoconsultar/{id}")
    // public String productoConsultar(@PathVariable(value = "id") Long id, Model
    // model, RedirectAttributes flash) {
    // Ejemplar ejemplar = libreriaServiceIface.buscarEjemplarPorId(id);
    // if (ejemplar == null) {
    // flash.addFlashAttribute("warning", "El registro no fue hallado en la base de
    // datos");
    // return "redirect:/libreria/productoslistar";
    // }
    // model.addAttribute("titulo", "Consulta del producto: " + ejemplar.);
    // model.addAttribute("producto", producto);
    // return "producto/consulta_producto";
    // }

}
