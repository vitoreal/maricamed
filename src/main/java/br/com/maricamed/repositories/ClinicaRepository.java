package br.com.maricamed.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Clinica;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
	
	@Query("Select c FROM Clinica c WHERE c.usuario.nome LIKE :search%")
	Page<Clinica> findAllByTitulo(String search, Pageable pageable);
	
	@Query("Select c FROM Clinica c WHERE c.usuario.id = :id")
	Optional<Clinica> findByUsuarioId(Long id);
}
