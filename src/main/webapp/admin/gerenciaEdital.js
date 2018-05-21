$(function () {
    $(".cadastrador").click(function(e){
        $("#myModal2").modal();
    });

    $(".alterador").click(function(e) {
        $("#myModal").modal();
    });
    
    $(".date").mask("99-99-9999");

    $(".colapsador").click(function(e){
        $("#collapsibleCursos").collapse();
    });
});