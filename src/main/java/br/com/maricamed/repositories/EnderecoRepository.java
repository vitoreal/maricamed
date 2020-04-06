package br.com.maricamed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.maricamed.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	
}
