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

import br.com.maricamed.entities.Clinica;
import br.com.maricamed.entities.Endereco;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.ClinicaService;
import br.com.maricamed.services.EnderecoService;
import br.com.maricamed.services.UsuarioService;

/**
* <h1>Marica Med - controle de clinica!</h1>
* Usuario Controller - 
* Tratamento do login / logout / cadastro do usuário
* <p>
* Controle de clinicas
* 
*
* @author  Vitor Almeida
* @version 1.0
* @since   2020-03-18 
*/

@Controller
@RequestMapping("clinicas")
public class ClinicaController {
	
	@Autowired
	private ClinicaService service;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/lista")
	public String listarClinicas() {
	    return "clinica/lista";
	}
	
	@GetMapping("/novo")
	public String telaCadastro(Clinica clinica) {
	    return "clinica/cadastro";
	}
	
	@GetMapping("/datatables/server/clinicas")
	public ResponseEntity<?> listarDatatables(HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarTodos(request));
	}
	
	@PostMapping("/salvar")
	public RedirectView salvarClinica(Clinica clinica, RedirectAttributes attr) {
		try {
			
			if(clinica.getUsuario() != null) {
				Usuario user = usuarioService.salvar(clinica.getUsuario());
				clinica.setUsuario(user);
			}
			
			if(clinica.getEndereco() != null) {
				Endereco end = enderecoService.save(clinica.getEndereco());
				clinica.setEndereco(end);
			}
			service.salvar(clinica);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: E-mail já existente!");
			attr.addFlashAttribute("clinica", clinica);
		}
		RedirectView rv = new RedirectView ();
		rv.setUrl("/clinicas/novo/");
		return rv;
	}
	
	@PostMapping("/editar")
	public RedirectView editarClinica(Clinica clinica, RedirectAttributes attr) {
		try {
			if(!clinica.hasNotId()) {
				
				Clinica cli = service.findById(clinica.getId());
				
				if (!clinica.getEspecialidades().isEmpty()) {
					clinica.getEspecialidades().addAll(cli.getEspecialidades());
				}
				
				if(clinica.getUsuario() != null) {
					Usuario usuario = updateUsuario(clinica.getUsuario());
					Usuario user = usuarioService.salvar(usuario);
					clinica.setUsuario(user);
				}
				
				if(clinica.getEndereco() != null) {
					Endereco end = enderecoService.save(clinica.getEndereco());
					clinica.setEndereco(end);
				}
				service.salvar(clinica);
				attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
				
			}
			
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: E-mail já existente!");
			attr.addFlashAttribute("clinica", clinica);
		}
		RedirectView rv = new RedirectView ();
		if(!clinica.hasNotId()) {
			rv.setUrl("/clinicas/editar/dados/clinica/"+clinica.getId());
			attr.addFlashAttribute("id", clinica.getId());
			
		} else {
			rv.setUrl("/clinicas/novo/");
		}
		return rv;
	}
	
	@GetMapping("/editar/dados/clinica/{id}")
	public ModelAndView preEditarCadastro(@PathVariable("id") Long id) {
		return new ModelAndView("clinica/cadastro", "clinica", service.findById(id));
	}
	
	// excluir especialidade
	@GetMapping({"/id/{idCli}/excluir/especializacao/{idEsp}"})
	public RedirectView excluirEspecialidadePorClinica(@PathVariable("idCli") Long idCli, 
						 @PathVariable("idEsp") Long idEsp, RedirectAttributes attr) {
		service.excluirEspecialidadePorClinica(idCli, idEsp);
		attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso.");
		RedirectView rv = new RedirectView ();
		rv.setUrl("/clinicas/editar/dados/clinica/"+idCli);
		attr.addFlashAttribute("id", idCli);
			
		return rv;		
	}
    
	public Usuario updateUsuario(Usuario usuario) {
		Usuario user = usuarioService.findById(usuario.getId());
		
		user.setNome(usuario.getNome());
		user.setTelefone1(usuario.getTelefone1());
		user.setTelefone2(usuario.getTelefone2());
		user.setCelular(usuario.getCelular());
		user.setPerfis(usuario.getPerfis());
		user.setAtivo(usuario.isAtivo());
		return user;
	}
}
