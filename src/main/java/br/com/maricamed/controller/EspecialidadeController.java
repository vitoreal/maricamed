package br.com.maricamed.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.maricamed.entities.Especialidade;
import br.com.maricamed.services.EspecialidadeService;

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
@RequestMapping("especialidades")
public class EspecialidadeController {
	
	@Autowired
	private EspecialidadeService service;
	
	@GetMapping({"/", ""})
	public String paginaAdmin(Especialidade especialidade) {
	
	    return "especialidade/especialidade";
	}
	
	@PostMapping("/salvar")
	public String salvar(Especialidade especialidade, RedirectAttributes attr) {
		try {
			service.save(especialidade);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: Especialidade já existente!");
			attr.addFlashAttribute("especialidade", especialidade);
		}
		
		return "redirect:/especialidades";
			
	}
	
	@GetMapping("/editar/{id}")
	public String buscarParaEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("especialidade", service.findById(id));
		return "especialidade/especialidade";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirPorId(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/especialidades";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatableListar(HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarEspecialidades(request));
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> buscaPorTitulo(@RequestParam("termo") String termo) {
		List<String> especialidades = service.buscarEspecialidadesPorTermo(termo);
	    return ResponseEntity.ok(especialidades);
	}
	
	@GetMapping("/datatables/server/clinica/{id}")
	public ResponseEntity<?> datatableEpecialidadeClinicaListar(@PathVariable("id") Long id, HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarEspecialidadesPorIdClinica(id, request));
	}
	
	@GetMapping("/datatables/server/medico/{id}")
	public ResponseEntity<?> datatableEpecialidadeMedicoListar(@PathVariable("id") Long id, HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarEspecialidadesPorIdMedico(id, request));
	}

}
