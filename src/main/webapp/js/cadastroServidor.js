/**
 * 
 */
function ativarProfessor() {
	if(document.getElementById("dadosProfessor").style.display == "none")
		document.getElementById("dadosProfessor").style.display = 'block';
	
	if(document.getElementById("dadosAdministrativos").style.display == "block")
		document.getElementById("dadosAdministrativos").style.display = 'none';
}

function ativarAdministrativo() {
		
	if(document.getElementById("dadosProfessor").style.display == "block")
		document.getElementById("dadosProfessor").style.display = 'none';

	if(document.getElementById("dadosAdministrativos").style.display == "none")
		document.getElementById("dadosAdministrativos").style.display = 'block';
}