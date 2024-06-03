package libreria.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import libreria.model.entity.Cliente;
import libreria.model.service.LibreriaServiceIface;
import libreria.utils.paginator.PageRender;

@Controller
@RequestMapping("/libreria")
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private LibreriaServiceIface libreriaServiceIface;

    @GetMapping("clienteslistar")
    public String clientesListar(@RequestParam(name = "pag", defaultValue = "0") int pag, Model model) {

        Pageable pagina = PageRequest.of(pag, 5);
        Page<Cliente> clientes = libreriaServiceIface.buscarClientesTodos(pagina);

        PageRender<Cliente> pageRender = new PageRender<>("/libreria/clienteslistar", clientes);
        model.addAttribute("pageRender", pageRender);
        model.addAttribute("titulo", "listado de clientes");
        model.addAttribute("clientes", clientes);
        return "cliente/listado_clientes";
    }

    @GetMapping("/clientenuevo")
    public String clienteFormNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo cliente");
        model.addAttribute("accion", "Crear");
        model.addAttribute("cliente", new Cliente());
        return "cliente/formulario_cliente";
    }

    @PostMapping("/clienteguardar")
    public String clienteGuardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result,
            Model model, SessionStatus status, RedirectAttributes flash,
            @RequestParam(name = "file") MultipartFile multipartFile) {

        String accion = (cliente.getId() == null) ? "Crear" : "Modificar";

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo cliente");
            model.addAttribute("accion", accion);
            model.addAttribute("info", "Complemente o corrija la información de los campos del formulario");
            return "cliente/formulario_cliente";
        }

        if (!multipartFile.isEmpty()) {

            if (multipartFile.getSize() > 10 * 1024 * 1024) {
                model.addAttribute("titulo", "Nuevo cliente");
                model.addAttribute("accion", "Guardar cliente");
                model.addAttribute("info", "El archino no puede superar los 10MB");
                return "cliente/formulario_cliente";
            }

            String contentType = multipartFile.getContentType();
            if (!contentType.equals("image/jpeg")
                    && !contentType.equals("image/jpg")) {
                        model.addAttribute("titulo", "Nuevo cliente");
                        model.addAttribute("accion", "Guardar cliente");
                        model.addAttribute("info", "Tipo de archivo no soportado. Por favor, ingrese un archivo pdf, jpg o jpeg");
                        return "cliente/formulario_cliente";
            }

            if (cliente.getId() != null && cliente.getId() > 0 &&
                    cliente.getFoto() != null && cliente.getFoto().length() > 0) {

                Path rutaAbsUploads = Paths.get("uploads").resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rutaAbsUploads.toFile();
                if (archivo.exists() && archivo.canRead()) {
                    archivo.delete();
                }
            }

            String nombreUnico = UUID.randomUUID().toString().substring(0, 8) + "_"
                    + multipartFile.getOriginalFilename();
            Path rutaUploads = Paths.get("uploads").resolve(nombreUnico);
            Path rutaAbsUploads = rutaUploads.toAbsolutePath();

            try {
                Files.copy(multipartFile.getInputStream(), rutaAbsUploads);
                cliente.setFoto(nombreUnico);
                flash.addFlashAttribute("info", "El archivo " + multipartFile.getOriginalFilename() + " fue cargado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        libreriaServiceIface.guardarCliente(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",
                "El registro fue " + (cliente.getId() == null ? "agregado" : "modificado") + " con éxito");
        return "redirect:/libreria/clienteslistar";
    }

    @GetMapping("/clienteconsultar/{id}")
	public String clienteConsultar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = libreriaServiceIface.buscarClientePorId(id);
		if (cliente == null) {
			flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
			return "redirect:/libreria/clienteslistar";
		}
		model.addAttribute("titulo", "Consulta del cliente: " + cliente.getNombres());
		model.addAttribute("cliente", cliente);
		return "cliente/consulta_cliente";
	}	


    @GetMapping("/clientemodificar/{id}")
	public String clienteFormModificar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = libreriaServiceIface.buscarClientePorId(id);
			if (cliente == null) {
				flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
				return "redirect:/comercial/clienteslistar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
			return "redirect:/comercial/clienteslistar";
		}
		model.addAttribute("accion", "Modificar");
		model.addAttribute("titulo", "Modificar cliente");
		model.addAttribute("cliente", cliente);
		return "cliente/formulario_cliente";
	}
    

}
