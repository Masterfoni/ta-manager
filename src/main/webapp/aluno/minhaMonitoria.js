$(function () {
    $(".cadastrador").click(function(e){
        $("#modalRegistroAtividade").modal();
    });
    
    $(".editarAtividade").click(function(e) {
        $("#modalAlterarAtividade").modal();
    });
    
    $(".excluirAtividade").tooltip();
});

function handleAjax(data) {
	 var status = data.status;

	    switch(status) {
	        case "complete":
	        	updateAlteradorFunction();
	            break;
	        case "success":
	        	updateAlteradorFunction();
	            break;
	    }
}

function updateAlteradorFunction() {
	$(".cadastrador").click(function(e){
		$("#modalRegistroAtividade").modal();
	});
	
	$(".editarAtividade").click(function(e) {
		 $("#modalAlterarAtividade").modal();
	});

	
    $(".excluirAtividade").tooltip();
}