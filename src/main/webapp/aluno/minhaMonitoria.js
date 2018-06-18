$(function () {
    $(".cadastrador").click(function(e){
        $("#modalRegistroAtividade").modal();
    });

    $(".alterador").click(function(e) {
        $("#myModal").modal();
    });
    
    $(".date").mask("99-99-9999");
    $(".hora").mask("99:99:99");
});

function handleAjax(data) {
	 var status = data.status;

	    switch(status) {
	        case "complete":
	        	console.log("complete");
	            break;
	        case "success":
	        	console.log("success");
	            break;
	    }
}