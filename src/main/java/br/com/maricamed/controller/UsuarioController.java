package br.com.maricamed.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.UsuarioService;

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
	private UsuarioService service;
	
	@GetMapping("/novo")
	public String cadastroPorAdmin(Usuario usuario) {
	    return "usuario/cadastro";
	}
	
	@GetMapping("/lista")
	public String listarUsuarios() {
	    return "usuario/lista";
	}
	
	@GetMapping("/datatables/server/usuarios")
	public ResponseEntity<?> listarUsuarios(HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarTodos(request));
	}
	

}
