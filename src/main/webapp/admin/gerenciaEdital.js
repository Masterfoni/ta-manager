$(function () {
    $(".cadastrador").click(function(e){
        $("#myModal2").modal();
    });

    $(".alterador").click(function(e) {
        $("#myModal").modal();
    });
    
    $(".specificdate").mask("99-99-9999");

    $(".specificdate2").change(function(e) {
        console.log("lololo");
        $(".specificdate").mask("99-99-9999");
    });

    $(".colapsador").click(function(e){
        $("#collapsibleCursos").collapse();
    });
});