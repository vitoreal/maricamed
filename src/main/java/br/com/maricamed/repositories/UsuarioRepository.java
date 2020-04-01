package br.com.maricamed.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.maricamed.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
	@Query("Select u FROM Usuario u "
			+ "JOIN u.perfis p "
			+ "WHERE u.email like :search% OR p.desc like :search% OR u.nome LIKE :search%")
	Page<Usuario> findAllByNamePerfilEmail(String search, Pageable pageable);
}
