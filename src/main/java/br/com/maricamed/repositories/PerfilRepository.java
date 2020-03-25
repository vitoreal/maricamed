package br.com.maricamed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.maricamed.entities.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
