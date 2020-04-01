package br.com.maricamed.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.maricamed.entities.Perfil;
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
	public ResponseEntity<?> listarUsuariosDatatables(HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarTodos(request));
	}
	
	// salvar cadastro de usuarios por administrador
	@PostMapping("cadastro/salvar")
	public String salvarUsuario(Usuario usuario, RedirectAttributes attr) {
		List<Perfil> perfis = usuario.getPerfis();
		if(perfis.size() > 2 || 
				perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(4L))) ||
				perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(4L)))) {
			attr.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico");
			attr.addFlashAttribute("usuario", usuario);
		} else {
			service.salvar(usuario);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			
		}
		return "redirect:/usuarios/novo/";
	}
}
