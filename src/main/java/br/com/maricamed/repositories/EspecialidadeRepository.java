package br.com.maricamed.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
	
	@Query("Select e FROM Especialidade e WHERE e.titulo LIKE :search%")
	Page<Especialidade> findAllByTitulo(String search, Pageable pageable);

	@Query("Select e.titulo FROM Especialidade e WHERE e.titulo LIKE :termo%")
	List<String> findEspecialidadesPorTermo(String termo);
	
	@Query("Select e FROM Especialidade e WHERE e.titulo IN :titulos")
	Set<Especialidade> findByTitulo(String[] titulos);
	
	@Query("Select e FROM Especialidade e "
			+ "join e.clinicas c WHERE c.id = :id")
	Page<Especialidade> findByIdClinica(Long id, Pageable pageable);
	
	@Query("Select e FROM Especialidade e "
			+ "join e.medicos m WHERE m.id = :id")
	Page<Especialidade> findByIdMedico(Long id, Pageable pageable);
	
}
