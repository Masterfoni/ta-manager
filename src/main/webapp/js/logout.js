var auth2;

onload = initClient;

function initClient () {
    gapi.load('auth2', function(){
        auth2 = gapi.auth2.init({
            client_id: '835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com'
        });

    });
    
    signOut();
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
	//auth2 = gapi.auth2.getAuthInstance();
	
	if(auth2 != null)
	{
		auth2.signOut().then(function () {
    	console.log('User signed out.');
    	});
	}
	
	window.location.href = "/";
}