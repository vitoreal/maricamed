package br.com.maricamed.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		service.save(especialidade);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/especialidades	";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatableListar(HttpServletRequest request) {
		Map<String, Object> respBD = service.buscarEspecialidades(request);
		ResponseEntity<Map<String, Object>> resp = ResponseEntity.ok(respBD);
	    return resp;
	}

}
