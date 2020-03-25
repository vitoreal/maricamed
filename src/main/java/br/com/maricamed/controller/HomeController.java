package br.com.maricamed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login-error")
    public String loginError(ModelMap model) {
    	model.addAttribute("alerta", "erro");
    	model.addAttribute("titulo", "Credenciais Inválidas");
    	model.addAttribute("texto", "Login ou senha incorretas, tente novamente!");
    	model.addAttribute("subtexto", "Acesso somente permitido para cadastro já ativados!");
        return "login";
    }
    
}
