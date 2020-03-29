package br.com.maricamed.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
	
	@Query("Select e FROM Especialidade e WHERE e.titulo LIKE :search%")
	Page<Especialidade> findAllByTitulo(String search, Pageable pageable);
	
}
