package br.com.maricamed.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.ClinicaService;
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
	
	@Autowired
	private ClinicaService clinicaService;
	
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
	
	@PostMapping("cadastro/salvar")
	public RedirectView salvarUsuario(Usuario usuario, RedirectAttributes attr) {
		try {
			if (usuario.getId() != null) {
				usuario = updateUsuario(usuario);
			} 
			service.salvar(usuario);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: E-mail já existente!");
			attr.addFlashAttribute("usuario", usuario);
		}
		RedirectView rv = new RedirectView ();
		if(usuario.getId() != null) {
			rv.setUrl("/usuarios/editar/dados/usuario/"+usuario.getId());
			attr.addFlashAttribute("id", usuario.getId());
			
		} else {
			rv.setUrl("/usuarios/novo/");
		}
		return rv;
	}
	
	public Usuario updateUsuario(Usuario usuario) {
		Usuario user = service.findById(usuario.getId());
		
		user.setNome(usuario.getNome());
		user.setTelefone1(usuario.getTelefone1());
		user.setTelefone2(usuario.getTelefone2());
		user.setCelular(usuario.getCelular());
		user.setPerfis(usuario.getPerfis());
		user.setAtivo(usuario.isAtivo());
		return user;
	}
	
	@PostMapping("cadastro/alterar-senha")
	public RedirectView alterarSenhaUsuario(Usuario usuario, RedirectAttributes attr) {
		
		Usuario user = service.findById(usuario.getId());
		user.setSenha(usuario.getSenha());
		service.salvar(user);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		
		RedirectView rv = new RedirectView ();
		rv.setUrl("/usuarios/editar/credenciais/usuario/"+usuario.getId());
		attr.addFlashAttribute("id", usuario.getId());
		return rv;
	}
	
	@GetMapping("/editar/credenciais/usuario/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {
	    return new ModelAndView("usuario/alterar-senha", "usuario", service.findById(id));
	}
	
	@GetMapping("/editar/dados/usuario/{id}")
	public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long id) {
		return new ModelAndView("usuario/cadastro", "usuario", service.findById(id));
	}
}
