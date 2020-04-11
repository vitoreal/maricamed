package br.com.maricamed.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maricamed.entities.Perfil;
import br.com.maricamed.repositories.PerfilRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class PerfilService {
	
	@Autowired
	private PerfilRepository repository;
	
	@Transactional(readOnly = true)
	public Perfil findById(Long id){
		Optional<Perfil> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	
}
