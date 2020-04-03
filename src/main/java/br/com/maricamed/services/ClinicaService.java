package br.com.maricamed.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maricamed.datatables.Datatables;
import br.com.maricamed.datatables.DatatablesColunas;
import br.com.maricamed.entities.Clinica;
import br.com.maricamed.repositories.ClinicaRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class ClinicaService {
	
	@Autowired
	private ClinicaRepository repository;
	
	@Autowired
	private Datatables datatables;
	
	@Transactional(readOnly = true)
	public List<Clinica> findAll(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Clinica findById(Long id){
		Optional<Clinica> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id){
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Clinica clinica){
		repository.save(clinica);
	}
	
	@Transactional(readOnly = false)
	public Map<String, Object> buscarClinicas(HttpServletRequest request){
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CLINICAS);
		Page<?> page = datatables.getSearch().isEmpty() 
				? repository.findAll(datatables.getPageable()) 
				: repository.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		
		return datatables.getResponse(page);		
	}
	
	@Transactional(readOnly = false)
	public Clinica buscaPorUsuarioId(Long id) {
		return repository.findByUsuarioId(id).orElse(new Clinica());
	}
	
	
}