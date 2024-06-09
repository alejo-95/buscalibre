package libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libreria.model.entity.Cliente;
import libreria.model.entity.Libro;
import libreria.model.entity.Prestamo;
import libreria.model.service.LibreriaServiceIface;

@Controller
@RequestMapping("/libreria")
@SessionAttributes("prestamo")
public class PrestamoController {

    @Autowired
    private LibreriaServiceIface libreriaServiceIface;

    @GetMapping("/prestamonuevo/{id}")
    public String prestamoNuevo(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = libreriaServiceIface.buscarClientePorId(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/libreria/clienteslistar";
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);

        model.addAttribute("titulo", "Nuevo prestamo");
        model.addAttribute("accion", "Guardar prestamo");
        model.addAttribute("prestamo", prestamo);
        return "prestamo/prestamo_nuevo";
    }

    @GetMapping(value = "/cargarlibros/{term}", produces = "application/json")
    public @ResponseBody List<Libro> cargarLibros(@PathVariable(name = "term") String term) {
        
        return libreriaServiceIface.buscarLibrosPorNombre(term);
    }

}
