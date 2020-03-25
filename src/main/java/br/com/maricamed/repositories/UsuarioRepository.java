package br.com.maricamed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.maricamed.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
