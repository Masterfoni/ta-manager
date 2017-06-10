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
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
	  console.log(profile);
	  console.log('HostedDomain: ' + googleUser.getHostedDomain());
	  
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
	  xhr.send(id_token);
	  
	  
	  
}