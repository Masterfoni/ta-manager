/**
 * 
 */
function mudarTipo() {
	if(document.forms['cadastroform']['cadastroform:radio'][0].checked == true )
	{
		document.getElementById("divTitulo").style.display = "block"; 
	}
	else
	{
		document.getElementById("divTitulo").style.display = "none";
	}
}