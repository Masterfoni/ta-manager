$(".cadastrador").click(function(e){
    $("#myModal2").modal();
});

$(".alterador").click(function(e) {
    $("#myModal").modal();
});

$(".cadastradorComponente").click(function(e) {
    $("#myModal3").modal();
});

$(".numero2").mask("99");
$(".periodo").mask("9999/9");