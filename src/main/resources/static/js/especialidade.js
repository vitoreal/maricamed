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
			{mData: 'id'},
			{mData: 'titulo'},
			{orderable: false,
				mData: 'id',
					"render": function(id){
						return '<a class"btn btn-success btn-sm btn-block" href="/especialidades/editar/"'+id+'" role="button"><i class="fa fa-edit"></i></a>';
					}
			},
			{orderable: false,
				mData: 'id',
					"render": function(id){
						return '<a class"btn btn-success btn-sm btn-block" href="/especialidades/excluir/"'+id+'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fa fa-edit"></i></a>';
					}
			}
			
		]
	})
	
});