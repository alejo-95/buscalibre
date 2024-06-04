package libreria.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import libreria.model.entity.Editorial;
import libreria.model.entity.Libro;
import libreria.model.service.LibreriaServiceIface;
import libreria.utils.paginator.PageRender;

@Controller
@RequestMapping("/libreria")
@SessionAttributes("libro")
public class LibroController {

    @Autowired
    private LibreriaServiceIface libreriaServiceIface;

    @GetMapping("/libroslistar")
    public String librosListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {
        Pageable pagina = PageRequest.of(pag, 5);
        Page<Libro> libros = libreriaServiceIface.buscarLibrosTodos(pagina);

        PageRender<Libro> pageRender = new PageRender<>("/libreria/libroslistar", libros);

        model.addAttribute("titulo", "Listado de libros disponibles");
        model.addAttribute("libros", libros);
        model.addAttribute("pageRender", pageRender);
        return "libro/listado_libros";
    }

    @GetMapping("/libronuevo")
    public String libroNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo libro");
        model.addAttribute("accion", "Crear");
        model.addAttribute("libro", new Libro());
        return "libro/formulario_libro";
    }

    @PostMapping("/libroguardar")
    public String libroGuardar(@Valid @ModelAttribute("libro") Libro libro, BindingResult result,
            Model model, SessionStatus status, RedirectAttributes flash,
            @RequestParam(name = "file") MultipartFile multipartFile) {

        String accion = (libro.getId() == null) ? "Crear" : "Modificar";

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo libro");
            model.addAttribute("accion", accion);
            model.addAttribute("info", "Complemente o corrija la información de los campos del formulario");
            return "libro/formulario_libro";
        }

        if (!multipartFile.isEmpty()) {

            if (multipartFile.getSize() > 10 * 1024 * 1024) {
                model.addAttribute("titulo", "Nuevo libro");
                model.addAttribute("accion", "Guardar libro");
                model.addAttribute("info", "El archino no puede superar los 10MB");
                return "libro/formulario_libro";
            }

            String contentType = multipartFile.getContentType();
            if (!contentType.equals("image/jpeg")
                    && !contentType.equals("image/jpg")) {
                model.addAttribute("titulo", "Nuevo libro");
                model.addAttribute("accion", "Guardar libro");
                model.addAttribute("info",
                        "Tipo de archivo no soportado. Por favor, ingrese un archivo jpg o jpeg");
                return "libro/formulario_libro";
            }

            if (libro.getId() != null && libro.getId() > 0 &&
                    libro.getCaratula() != null && libro.getCaratula().length() > 0) {

                Path rutaAbsUploads = Paths.get("uploads", "caratulas").resolve(libro.getCaratula()).toAbsolutePath();
                File archivo = rutaAbsUploads.toFile();
                if (archivo.exists() && archivo.canRead()) {
                    archivo.delete();
                }
            }

            String nombreUnico = UUID.randomUUID().toString().substring(0, 8) + "_"
                    + multipartFile.getOriginalFilename();
            Path rutaUploads = Paths.get("uploads", "caratulas").resolve(nombreUnico);
            Path rutaAbsUploads = rutaUploads.toAbsolutePath();

            try {
                Files.copy(multipartFile.getInputStream(), rutaAbsUploads);
                libro.setCaratula("caratulas/" + nombreUnico);
                flash.addFlashAttribute("info", "El archivo " + multipartFile.getOriginalFilename() + " fue cargado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        libreriaServiceIface.guardarLibro(libro);
        status.setComplete();
        flash.addFlashAttribute("success",
                "El registro fue " + (libro.getId() == null ? "agregado" : "modificado") + " con éxito");
        return "redirect:/libreria/libroslistar";
    }

    @GetMapping("/libroconsultar/{id}")
	public String libroConsultar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Libro libro = libreriaServiceIface.buscarLibroPorId(id);
		if (libro == null) {
			flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
			return "redirect:/libreria/libroslistar";
		}
		model.addAttribute("titulo", "Consulta del libro: " + libro.getTitulo());
		model.addAttribute("libro", libro);
		return "libro/consulta_libro";
	}

    @GetMapping("/libromodificar/{id}")
	public String libroFormModificar(@PathVariable(value = "id") Long id, 
			Model model, RedirectAttributes flash) {
		Libro libro = null;
		if (id > 0) {
			libro = libreriaServiceIface.buscarLibroPorId(id);
			if (libro == null) {
				flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
				return "redirect:/libreria/libroslistar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
			return "redirect:/libreria/libroslistar";
		}
		model.addAttribute("accion", "Modificar");
		model.addAttribute("titulo", "Modificar libro");
		model.addAttribute("libro", libro);
		return "libro/formulario_libro";
	}

    @GetMapping("/libroeliminar/{id}")
	public String productoEliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Libro libro = libreriaServiceIface.buscarLibroPorId(id);
			if(libro != null) {
				libreriaServiceIface.eliminarLibroPorId(id);
				flash.addFlashAttribute("success", "El registro fue eliminado de la base de datos");
				Path rutaAbsUploads = Paths.get("uploads").resolve(libro.getCaratula()).toAbsolutePath();
				File archivo = rutaAbsUploads.toFile();
				if(archivo.exists() && archivo.canRead()) {
					if(archivo.delete()) {
						flash.addFlashAttribute("info", "El archivo " + libro.getCaratula() + " fue eliminado");	
					}
				}
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
		}		
		return "redirect:/libreria/libroslistar";
	}




    @ModelAttribute("editoriales")
	public List<Editorial> obtenerCategorias() {
		return libreriaServiceIface.buscarEditorialesTodos();
	}

}
