package br.com.maricamed.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.maricamed.entities.PerfilTipo;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private UsuarioService usuarioService;
	
    @GetMapping({"/", "/home"})
    public String home(Authentication authentication, Model model, 
    		@AuthenticationPrincipal UserDetails currentUser, HttpSession session) {
    	
    	if (authentication != null && authentication.isAuthenticated()) {
    		
    		Usuario user = usuarioService.buscaPorEmail(currentUser.getUsername());
    		
    		Usuario userSession = new Usuario();
    		userSession.setId(user.getId());
    		userSession.setNome(user.getNome());
    		userSession.setEmail(user.getEmail());
    		
    		session.setAttribute("user", userSession);
    		
    		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()){
    	        if (grantedAuthority.getAuthority().equals(PerfilTipo.CLINICA.getDesc())) {
    	        	return "clinica/admin-clinica";
    	        } else if (grantedAuthority.getAuthority().equals(PerfilTipo.MEDICO.getDesc())) {
    	        	return "admin-medico";
    	        } else if (grantedAuthority.getAuthority().equals(PerfilTipo.PACIENTE.getDesc())) {
    	        	return "admin-paciente";
    	        }
    	    }
    	}
    	
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
