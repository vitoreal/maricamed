package br.com.maricamed.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	@Query("Select m FROM Medico m WHERE m.crm LIKE :search%")
	Page<Medico> findAllByTitulo(String search, Pageable pageable);
}
