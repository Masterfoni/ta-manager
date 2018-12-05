$(document).ready(function () {
    $(".cadastrador").click(function(e){
        $("#myModal2").modal();
    });

    $(".alterador").click(function(e) {
        $("#myModal").modal();
    });
    
    $(".date").mask("99-99-9999");
    $(".nota").mask("99.9");
    $(".ano").mask("9999");

    $(".colapsador").click(function(e){
        $("#collapsibleCursos").collapse();
    });
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
    $(".date").mask("99-99-9999");
    $(".nota").mask("99.9");
    $(".ano").mask("9999");
}