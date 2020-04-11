package br.com.maricamed.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] MEDICOS = {"usuario.nome", "usuario.ativo", "crm"};
	
	public static final String[] CLINICAS = {"usuario.nome", "usuario.ativo"};
	
	public static final String[] AGENDAMENTOS = {"id", "paciente.nome", "dataConsulta", "medico.nome", "especialidade.titulo"};

	public static final String[] USUARIOS = {"id", "nome", "email", "ativo", "perfis"};	
}
