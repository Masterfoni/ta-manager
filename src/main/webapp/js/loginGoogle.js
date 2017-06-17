/**
 * 
 */
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}

function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  
	  if(googleUser.getHostedDomain() != 'a.recife.ifpe.edu.br')
	  {
		  alert("Utilize seu email instituncional.");
		  signOut();
	  }	
	  
	  var id_token = googleUser.getAuthResponse().id_token;
	  
	  login([{name:'idToken', value:id_token}]);
}

function handleComplete(xhr, status, args) {
    var nomeDoAtributo = args.logou;

    // Atualizar UI
    alert(nomeDoAtributo);
}
