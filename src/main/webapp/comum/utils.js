function handleAjax(data) {
    var status = data.status;
    var gemLoader = document.getElementById("gemLoader");

    switch(status) {
        case "begin":
            gemLoader.style.display = 'block';
            break;

        case "complete":
            gemLoader.style.display = 'none';
            break;

        case "success":
            break;
    }
}