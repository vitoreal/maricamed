//datatables - lista de médicos
$(document).ready(function() {
	
	moment.locale('pt-BR');
	var table = $('#tbl-datatable').DataTable({
		"language": {
            "url": "/util/datatable-pt-br.json"
        },
		searching : true,
		lengthMenu : [ 5, 10 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/clinicas/datatables/server/clinicas',
			data : 'data'
		},
		aoColumns : [
				{data : 'usuario.nome'},
				{data : 'usuario.email'},
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a ', ' ')
								 .concat('href="').concat('/usuarios/editar/credenciais/usuario/')
								 .concat(id, '"', ' ') 
								 .concat('role="button" title="Horários">', ' ')
								 .concat('<i class="iconecor-horario iconecor-24"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a ', ' ')
								 .concat('href="').concat('/usuarios/editar/dados/usuario/')
								 .concat(id, '"', ' ') 
								 .concat('role="button" title="Médicos">', ' ')
								 .concat('<i class="iconecor-doctor iconecor-24"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a ', ' ')
								 .concat('href="').concat('/clinicas/editar/dados/clinica/')
								 .concat(id, '"', ' ') 
								 .concat('role="button" title="Editar">', ' ')
								 .concat('<i class="iconecor-editar iconecor-24"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a ', ' ')
								 .concat('href="').concat('/usuarios/editar/dados/usuario/')
								 .concat(id, '"', ' ') 
								 .concat('role="button" title="Alterar Senha">', ' ')
								 .concat('<i class="iconecor-credencial iconecor-24"></i></a>');
					},
					orderable : false
				}
		]
	});
	
});	