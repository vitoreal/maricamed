package br.com.maricamed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.maricamed.entities.Usuario;
import br.com.maricamed.validator.UsuarioValidator;

/**
* <h1>Marica Med - controle de usuario!</h1>
* Usuario Controller - 
* Tratamento do login / logout / cadastro do usuário
* <p>
* Controle de acesso da api
* 
*
* @author  Vitor Almeida
* @version 1.0
* @since   2020-03-18 
*/

@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioValidator userValidator;
	
	@GetMapping("/novo")
	public String cadastroPorAdmin(Usuario usuario) {
	
	    return "usuario/cadastro";
	}


}
