$(document).ready(function() {
	moment.locale('pt-BR');
	
	$.fn.dataTable.ext.errMode = 'throw';

	var table = $('#table-especializacao').DataTable({
		searching: true,
		order: [[1, "asc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/especialidades/datatables/server',
			data: 'data'
		},
		aoColumns: [
			{data: 'id'},
			{data: 'titulo'},
			{orderable: false,
				data: 'id',
					"render": function(id){
						return '<a class"btn btn-success btn-sm btn-block" href="/especialidades/editar/'+id+'" role="button"><i class="iconecor-editar iconecor-24"></i></a>';
					}
			},
			{orderable: false,
				data: 'id',
					"render": function(id){
						return '<a class"btn btn-success btn-sm btn-block" href="/especialidades/excluir/'+id+'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="iconecor-delete iconecor-24"></i></a>';
					}
			}
			
		]
	})
	
});