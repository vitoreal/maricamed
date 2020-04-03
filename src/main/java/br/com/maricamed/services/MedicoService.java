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
import br.com.maricamed.entities.Medico;
import br.com.maricamed.repositories.MedicoRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository repository;
	
	@Autowired
	private Datatables datatables;
	
	@Transactional(readOnly = true)
	public List<Medico> findAll(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Medico findById(Long id){
		Optional<Medico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id){
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Medico medico){
		repository.save(medico);
	}
	
	public Map<String, Object> buscarMedicos(HttpServletRequest request){
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.MEDICOS);
		Page<?> page = datatables.getSearch().isEmpty() 
				? repository.findAll(datatables.getPageable()) 
				: repository.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		
		return datatables.getResponse(page);		
	}
	
	
}
