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
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open('POST', 'https://localhost:8181/FALJVAL/services/googleSignInService');
	  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	  xhr.onload = function() {
	    console.log('Signed in as: ' + xhr.responseText);
	  };
	  xhr.onreadystatechange = function (){
			if(xhr.readyState == 4)
				alert(xhr.responseText);
	  };
	  xhr.send(id_token);
	  
}