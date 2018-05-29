$(".nota").mask("99.9");

function teste(i) {
	id = i.id.substring(0,25);
	notaselecao = document.getElementById(id + "notaSelecao");
	mediaComponente = document.getElementById(id + "mediaComponente");
	if(notaselecao.disabled) {
		notaselecao.disabled = false;
		mediaComponente.disabled = false;
	} else {
		notaselecao.disabled = true;
		mediaComponente.disabled = true;
	}
}

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
    $(".nota").mask("99.9");
}