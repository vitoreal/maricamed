package br.com.maricamed.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maricamed.datatables.Datatables;
import br.com.maricamed.datatables.DatatablesColunas;
import br.com.maricamed.entities.Especialidade;
import br.com.maricamed.repositories.EspecialidadeRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class EspecialidadeService {
	
	@Autowired
	private EspecialidadeRepository repository;
	
	@Autowired
	private Datatables datatables;
	
	@Transactional(readOnly = true)
	public List<Especialidade> findAll(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Especialidade findById(Long id){
		Optional<Especialidade> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id){
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Especialidade especialidade){
		repository.save(especialidade);
	}
	
	public Map<String, Object> buscarEspecialidades(HttpServletRequest request){
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
		Page<?> page = datatables.getSearch().isEmpty() 
				? repository.findAll(datatables.getPageable()) 
				: repository.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		
		return datatables.getResponse(page);		
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarEspecialidadesPorTermo(String termo) {
		return repository.findEspecialidadesPorTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Especialidade> buscarPorTitulo(String[] titulos) {
		return repository.findByTitulo(titulos);
	}

	public Map<String, Object> buscarEspecialidadesPorIdClinica(Long id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
		Page<?> page = repository.findByIdClinica(id, datatables.getPageable());
		
		return datatables.getResponse(page);
	}

	public Map<String, Object> buscarEspecialidadesPorIdMedico(Long id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.MEDICOS);
		Page<?> page = repository.findByIdMedico(id, datatables.getPageable());
		
		return datatables.getResponse(page);
	}
	
	
}
