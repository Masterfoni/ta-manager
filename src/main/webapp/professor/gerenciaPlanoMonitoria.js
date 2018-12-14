$(".cadastrador").click(function(e){
    $("#myModal2").modal();
});

$(".alterador").click(function(e) {
    $("#myModal").modal();
});

$(".numero2").mask("99");
$(".periodo").mask("9999/9");
$(".inserirNotas").tooltip();

jsf.ajax.addOnEvent(handleAjaxPlanoMonitoria);

function handleAjaxPlanoMonitoria(data) {
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
    $(".alterador").click(function(e) {
        $("#myModal").modal();
    }); 

    $(".cadastrador").click(function(e){
        $("#myModal2").modal();
    });
    
    $(".numero2").mask("99");
    $(".periodo").mask("9999/9");
    $(".inserirNotas").tooltip();
}