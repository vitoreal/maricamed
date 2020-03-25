package br.com.maricamed.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	String nome;
	String uf;
	String sigla;

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public String getSigla() {
		return sigla;
	}


}
