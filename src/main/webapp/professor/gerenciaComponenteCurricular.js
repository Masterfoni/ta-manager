$(document).ready(function () {

	$(".cadastrador").click(function(e){
    	$("#myModal").modal();
	});

	$(".alterador").click(function(e) {
    	$("#myModal2").modal();
	});

	$(".numero2").mask("99");
	$(".periodo").mask("9999/9");
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
	$(".numero2").mask("99");
	$(".periodo").mask("9999/9");
}