$(function () {
    $(".cadastrador").click(function(e){
        $("#modalRegistroAtividade").modal();
    });

    $(".alterador").click(function(e) {
        $("#myModal").modal();
    });
    
    $(".date").mask("99-99-9999");
});