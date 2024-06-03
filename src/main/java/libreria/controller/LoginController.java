package libreria.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login(Model model, Principal principal, RedirectAttributes flash,
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		System.out.println(passwordEncoder.encode("Abc123"));

		if(principal != null) {
			flash.addFlashAttribute("info", "El usuario " + principal.getName() + " ya tiene una sesión abierta");
			return "redirect:/";
		}

		if(error != null) {
			flash.addFlashAttribute("error", "Usuario o clave inválido, intentente de nuevo");
			return "redirect:/login";
		}

		if(logout != null) {
			flash.addFlashAttribute("success", "Has cerrado sesión de forma correcta");
			return "redirect:/login";
		}

		return "login/login";
	}
}
