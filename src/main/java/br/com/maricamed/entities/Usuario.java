package br.com.maricamed.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuarios", indexes = {@Index(name = "idx_usuario_email", columnList = "email")})
public class Usuario extends AbstractEntity {	
	
	@Column(name = "nome", unique = true, nullable = false, length = 200)
	private String nome;

	@Column(name = "data_nascimento", length = 10)
	private Instant dtNascimento;
	
	@Column(name = "data_cadastro", nullable = false, length = 10)
	private Instant dtCadastro;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@JsonIgnore
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@ManyToMany
	@JoinTable(
		name = "usuarios_tem_perfis", 
        joinColumns = { @JoinColumn(name = "usuario_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "perfil_id", referencedColumnName = "id") }
	)
	private List<Perfil> perfis;
	
	@Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean ativo;
	
	@Column(name = "codigo_verificador", length = 6)
	private String codigoVerificador;
	
	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		super.setId(id);
	}

	// adiciona perfis a lista
	public void addPerfil(PerfilTipo tipo) {
		if (this.perfis == null) {
			this.perfis = new ArrayList<>();
		}
		this.perfis.add(new Perfil(tipo.getCod()));
	}

	public Usuario(String email) {
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Instant getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Instant dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	public Instant getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Instant dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}	
	
	public String getCodigoVerificador() {
		return codigoVerificador;
	}

	public void setCodigoVerificador(String codigoVerificador) {
		this.codigoVerificador = codigoVerificador;
	}

}
