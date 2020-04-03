package br.com.maricamed.controller;

import java.util.Arrays;
import java.util.List;

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

import br.com.maricamed.entities.Clinica;
import br.com.maricamed.entities.Perfil;
import br.com.maricamed.entities.PerfilTipo;
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
	
	// salvar cadastro de usuarios por administrador
	@PostMapping("cadastro/salvar")
	public String salvarUsuario(Usuario usuario, RedirectAttributes attr) {
		List<Perfil> perfis = usuario.getPerfis();
		if(perfis.size() > 2 || 
				perfis.containsAll(Arrays.asList(new Perfil(PerfilTipo.ADMIN.getCod()), new Perfil(PerfilTipo.PACIENTE.getCod()))) ||
				perfis.containsAll(Arrays.asList(new Perfil(PerfilTipo.CLINICA.getCod()), new Perfil(PerfilTipo.PACIENTE.getCod())))) {
			attr.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico");
			attr.addFlashAttribute("usuario", usuario);
		} else {
			try {
			service.salvar(usuario);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			} catch (DataIntegrityViolationException ex) {
				attr.addFlashAttribute("falha", "Error: E-mail já existente!");
				attr.addFlashAttribute("usuario", usuario);
			}
		}
		return "redirect:/usuarios/novo/";
	}
	
	@GetMapping("/editar/credenciais/usuario/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {
	    return new ModelAndView("usuario/cadastro", "usuario", service.findById(id));
	}
	
	@GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
	public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long usuarioId, 
											@PathVariable("perfis") Long[] perfisId) {
		
		Usuario us = service.buscarPorIdEPerfis(usuarioId, perfisId);
		
		if(us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod())) &&
		   !us.getPerfis().contains(new Perfil(PerfilTipo.CLINICA.getCod()))		) {
			return new ModelAndView("usuario/cadastro", "usuario", us);
		} else if(us.getPerfis().contains(new Perfil(PerfilTipo.CLINICA.getCod()))) {
			
			Clinica clinica = clinicaService.buscaPorUsuarioId(usuarioId);
			
			return clinica.hasNotId() 
					? new ModelAndView("clinica/cadastro", "clinica", new Clinica(new Usuario(usuarioId)))
					: new ModelAndView("clinica/cadastro", "clinica", clinica);
			
		} else if(us.getPerfis().contains(new Perfil(PerfilTipo.PACIENTE.getCod()))) {
			ModelAndView model = new ModelAndView("error");
			model.addObject("status", 403);
	    	model.addObject("error", "Área Restrita");
	    	model.addObject("message", "Os dados do Paciente são restritos a ele.");
	    	return model;
		}
		
		return new ModelAndView("redirect:/usuarios/lista");
	}
}
