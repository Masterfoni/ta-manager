$(".cadastrador").click(function(e){
    $("#myModal").modal();
});

$(".alterador").click(function(e) {
    $("#myModal2").modal();
});

$(".numero2").mask("99");
$(".periodo").mask("9999/9");