package br.com.maricamed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maricamed.entities.Endereco;
import br.com.maricamed.repositories.EnderecoRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository repository;
	
	@Transactional(readOnly = true)
	public Endereco findById(Long id){
		Optional<Endereco> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional(readOnly = false)
	public Endereco save(Endereco endereco){
		return repository.save(endereco);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(Long id){
		repository.deleteById(id);
	}
	
}
