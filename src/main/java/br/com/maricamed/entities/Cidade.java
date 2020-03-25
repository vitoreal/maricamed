package br.com.maricamed.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	String nome;
	
	@OneToOne
	@JoinColumn(name = "id_estado")
	private Estado estado;

	public String getNome() {
		return nome;
	}

	public Estado getEstado() {
		return estado;
	}

}
