//datatables - lista de médicos
$(document).ready(function() {
	
	moment.locale('pt-BR');
	var table = $('#table-usuarios').DataTable({
		"language": {
            "url": "/util/datatable-pt-br.json"
        },
		searching : true,
		lengthMenu : [ 5, 10 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/usuarios/datatables/server/usuarios',
			data : 'data'
		},
		aoColumns : [
				{data : 'id'},
				{data : 'nome'},
				{data : 'email'},
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'perfis',									 
					render : function(perfis) {
						var aux = new Array();
						$.each(perfis, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a ', ' ')
								 .concat('href="').concat('/usuarios/editar/credenciais/usuario/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar">', ' ')
								 .concat('<i class="iconecor-credencial iconecor-24"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a href="#"', ' ') 
								 .concat('id="dp_').concat(id).concat('"', ' ') 
								 .concat('role="button" title="Editar">', ' ')
								 .concat('<i class="iconecor-editar iconecor-24"></i></a>');
					},
					orderable : false
				}
		]
	});
	
	$('#table-usuarios tbody').on('click', '[id*="dp_"]', function () {
    	var data = table.row($(this).parents('tr')).data();
    	var aux = new Array();
		$.each(data.perfis, function( index, value ) {
			  aux.push(value.id);
		});
		document.location.href = '/usuarios/editar/dados/usuario/' + data.id + '/perfis/' + aux;
    } );	
	
});	