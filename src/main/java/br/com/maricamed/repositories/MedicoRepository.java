package br.com.maricamed.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	/*
	 
	  @Query("select u FROM Usuario u "
			+ "JOIN u.perfis p "
			+ "WHERE u.email like :search% OR p.desc like :search% OR u.nome LIKE :search%")
	 
	 */
	@Query("Select m FROM Medico m "
			+ "JOIN m.usuario u "
			+ "JOIN m.clinica c WHERE m.crm LIKE :search% "
			+ "OR u.nome LIKE :search% "
			+ "AND c.id = :idClinica")
	Page<Medico> findAllByNomeCrm(Long idClinica, String search, Pageable pageable);
	
	@Query("Select m FROM Medico m WHERE m.clinica.id = :idClinica")
	Page<Medico> findAllByIdClinica(Long idClinica, Pageable pageable);
	
	@Query("Select m FROM Medico m "
			+ "JOIN m.clinica c WHERE c.id = :idClinica")
	List<Medico> findByIdClinica(Long idClinica);
}
