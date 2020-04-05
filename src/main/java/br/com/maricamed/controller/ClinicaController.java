package br.com.maricamed.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.maricamed.entities.Clinica;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.ClinicaService;

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
    
}
