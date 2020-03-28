package br.com.maricamed.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)	
            model.addAttribute("error", "Login e Senha inválidos.");

        if (logout != null)
            model.addAttribute("message", "Você desconectou.");

        return "login";
    }
    
    @GetMapping("/login-error")
    public String loginError(ModelMap model) {
    	model.addAttribute("alerta", "erro");
    	model.addAttribute("titulo", "Credenciais Inválidas");
    	model.addAttribute("texto", "Login ou senha incorretas, tente novamente!");
    	model.addAttribute("subtexto", "Acesso somente permitido para cadastro já ativados!");
        return "login";
    }
    
    @GetMapping("/acesso-negado")
    public String acessoNegado(ModelMap model, HttpServletResponse resp) {
    	model.addAttribute("status", resp.getStatus());
    	model.addAttribute("error", "Acesso Negado");
    	model.addAttribute("message", "Você não tem permissão de acesso para esta área ou ação");
        return "error";
    }
    
}
