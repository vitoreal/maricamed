package br.com.maricamed.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.maricamed.entities.Clinica;
import br.com.maricamed.entities.Medico;
import br.com.maricamed.entities.Perfil;
import br.com.maricamed.entities.PerfilTipo;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.services.ClinicaService;
import br.com.maricamed.services.MedicoService;
import br.com.maricamed.services.PerfilService;
import br.com.maricamed.services.UsuarioService;
import br.com.maricamed.utils.Utils;

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
@RequestMapping("medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService service;
	@Autowired
	private ClinicaService serviceClinica;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PerfilService perfilService;
	
	@GetMapping("/lista")
	public ModelAndView listarMedicos() {
		ModelAndView mv = new ModelAndView("medico/lista");
    	mv.addObject("medico", service.findAll());
    	return mv;
	}
	
    @GetMapping("/dados/{idClinica}")
    public ModelAndView abrirDadosMedico(@PathVariable("idClinica") Long id, RedirectAttributes attr) {
    	ModelAndView mv = new ModelAndView("medico/lista");
    	mv.addObject("medico", service.findByIdClinica(id));
    	mv.addObject("idClinica", id);
    	return mv;
    }
    
    @GetMapping("/novo/{idClinica}")
	public ModelAndView telaCadastro(Medico medico, @PathVariable("idClinica") Long id) {
    	ModelAndView mv = new ModelAndView("medico/cadastro");
    	Clinica clinica = serviceClinica.findById(id);
    	medico.setClinica(clinica);
    	mv.addObject("medico", medico);
    	return mv;
	}
    
    @GetMapping("/editar/dados/{idClinica}/medico/{idMedico}")
	public ModelAndView preEditarCadastro(@PathVariable("idClinica") Long idClinica, 
										@PathVariable("idMedico") Long idMedico) {
		
    	ModelAndView mv = new ModelAndView("medico/cadastro");
    	Clinica clinica = serviceClinica.findById(idClinica);
    	Medico medico = service.findById(idMedico);
    	
    	String dtInscricao = Utils.converteInstantToDate(medico.getDtInscricao());
    	
    	medico.setClinica(clinica);
    	mv.addObject("medico", medico);
    	mv.addObject("dataInscricao", dtInscricao);
    	return mv;
    	
	}
    
    @GetMapping("/datatables/server/medicos/{idMedico}")
	public ResponseEntity<?> datatableEpecialidadeClinicaListar(@PathVariable("idMedico") Long id, HttpServletRequest request) {
	    return ResponseEntity.ok(service.buscarMedicosPorIdClinica(id, request));
	}
    
    @PostMapping("/salvar")
	public RedirectView salvarMedico(Medico medico, 
			@RequestParam("dataInscricao") String dataInscricao,
			RedirectAttributes attr) {
    	
    	RedirectView rv = new RedirectView ();
		
    	Long idClinica = medico.getClinica().getId();
    	
    	try {
			
			if(medico.getUsuario() != null) {
				
				List<Perfil> lpe = new ArrayList<Perfil>();
				
				Perfil perf = perfilService.findById(PerfilTipo.MEDICO.getCod());
				lpe.add(perf);
				medico.getUsuario().setPerfis(lpe);
				
				Usuario user = usuarioService.salvar(medico.getUsuario());
				
				medico.setUsuario(user);
			}
				
			Clinica cli = serviceClinica.findById(idClinica);
			medico.setClinica(cli);
			
			if(!("").equals(dataInscricao)) {
				Instant dtInsc = new SimpleDateFormat("yyyy-mm-dd").parse(dataInscricao).toInstant();
				medico.setDtInscricao(dtInsc);
			}
			
			service.salvar(medico);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			attr.addFlashAttribute("idClinica", idClinica);
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: E-mail já existente!");
			attr.addFlashAttribute("medico", medico);
			attr.addFlashAttribute("idClinica", idClinica);
		} catch (ParseException ex) {
			attr.addFlashAttribute("falha", "Algum erro aconteceu!");
			attr.addFlashAttribute("medico", medico);
			attr.addFlashAttribute("idClinica", idClinica);
		}
		
		rv.setUrl("/medicos/novo/"+idClinica);
		return rv;
	}
    
    @PostMapping("/editar")
	public RedirectView editarMedico(Medico medico, 
			@RequestParam("dataInscricao") String dataInscricao, RedirectAttributes attr) {
		try {
			if(!medico.hasNotId()) {
				
				Medico med = service.findById(medico.getId());
				
				if (!medico.getEspecialidades().isEmpty()) {
					medico.getEspecialidades().addAll(med.getEspecialidades());
				}
				
				if(medico.getUsuario() != null) {
					Usuario usuario = updateUsuario(medico.getUsuario());
					Usuario user = usuarioService.salvar(usuario);
					medico.setUsuario(user);
				}
				
				if(!("").equals(dataInscricao)) {
					Instant dtInsc = new SimpleDateFormat("yyyy-mm-dd").parse(dataInscricao).toInstant();
					medico.setDtInscricao(dtInsc);
				}
				
				service.salvar(medico);
				attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
				
			}
			
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Error: E-mail já existente!");
			attr.addFlashAttribute("medico", medico);
		} catch (ParseException ex) {
			attr.addFlashAttribute("falha", "Algum erro aconteceu!");
			attr.addFlashAttribute("medico", medico);
			attr.addFlashAttribute("idClinica", medico.getClinica().getId());
		}
		RedirectView rv = new RedirectView ();
		if(!medico.hasNotId()) {
			rv.setUrl("/medicos/editar/dados/"+medico.getClinica().getId()+"/medico/"+medico.getId());
			attr.addFlashAttribute("id", medico.getClinica().getId());
			
		} else {
			rv.setUrl("/medicos/novo/"+medico.getClinica().getId());
			return rv;
		}
		return rv;
	}
    
 // excluir especialidade
 	@GetMapping({"{idClinica}/id/{idMedico}/excluir/especializacao/{idEsp}"})
 	public RedirectView excluirEspecialidadePorMedico(@PathVariable("idClinica") Long idClinica, 
 						@PathVariable("idMedico") Long idMedico, 
 						 @PathVariable("idEsp") Long idEsp, RedirectAttributes attr) {
 		service.excluirEspecialidadePorMedico(idMedico, idEsp);
 		attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso.");
 		RedirectView rv = new RedirectView ();
 		rv.setUrl("/medicos/editar/dados/"+idClinica+"/medico/"+idMedico);
 			
 		return rv;		
 	}
    
    public Usuario updateUsuario(Usuario usuario) {
		Usuario user = usuarioService.findById(usuario.getId());
		
		user.setNome(usuario.getNome());
		user.setAtivo(usuario.isAtivo());
		return user;
	}
    
}
