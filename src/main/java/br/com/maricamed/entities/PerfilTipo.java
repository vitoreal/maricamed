package br.com.maricamed.entities;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), CLINICA(2, "CLINICA"), MEDICO(3, "MEDICO"), PACIENTE(4, "PACIENTE");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
