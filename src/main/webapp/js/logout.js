var auth2;

onload = initClient;

function initClient () {
	gapi.load('auth2', function(){
	    auth2 = gapi.auth2.init();
	    console.log("app started");
	  });
};

/**
 * Handle successful sign-ins.
 */
var onSuccess = function(user) {
    console.log('Signed in as ' + user.getBasicProfile().getName());
 };

/**
 * Handle sign-in failures.
 */
var onFailure = function(error) {
    console.log(error);
};

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	
	if(auth2 != null)
	{
		auth2.signOut().then(function () {
    	console.log('User signed out.');
    	});
	}
}