$(".detalhador").click(function(e){
    $("#myModal").modal();
});

jsf.ajax.addOnEvent(handleAjaxGerenciaRelatoriosFinais);

function handleAjaxGerenciaRelatoriosFinais(data) {
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
    $(".detalhador").click(function(e){
        $("#myModal").modal();
    });
}