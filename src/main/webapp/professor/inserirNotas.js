$(".nota").mask("99.9");

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